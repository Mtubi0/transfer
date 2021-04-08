package com.example.transfer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.transfer.model.User;
import com.example.transfer.model.money.Money;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransferService {

    private static String LOGIN_URL = "https://sandbox.bind.com.ar/v1/login/jwt";
    private static String TRANSFER_URL = "https://sandbox.bind.com.ar/v1/banks/:bank_id/accounts/:account_id/:view_id/transaction-request-types/TRANSFER/transaction-requests";

    private static String DEFAULT_BANK = "322";
    private static String DEFAULT_VIEW = "owner";

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired 
    private Gson parser;

    private String token = ""; //no deberia almacenarse aca
    
    public void login(User user) {

        HttpEntity<User> entity = new HttpEntity<User>(user);
        ResponseEntity<String> response = restTemplate.postForEntity(LOGIN_URL, entity, String.class);

        if(response.getStatusCode().equals(HttpStatus.OK)) {
            token = parser.fromJson(response.getBody(), JsonObject.class).get("token").getAsString();
        }
    }

    public void transfer(String sender, String receiver, String concept, Money money, List<String> emails, Optional<String> description) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "JWT " + token);

        JsonObject receptor = new JsonObject();
        receptor.addProperty("cbu", receiver);

        JsonObject body = new JsonObject();
        body.add("to", receptor);
        body.add("value", parser.toJsonTree(money));
        body.addProperty("concept", concept);
        body.add("emails", parser.toJsonTree(emails));
        if(description.isPresent()) {
            body.addProperty("description", description.get());
        }
        
        HttpEntity<String> entity = new HttpEntity<String>(body.toString(), headers);

        Map<String, String> params = new HashMap<>();
        params.put("bank_id", DEFAULT_BANK);
        params.put("account_id", sender);
        params.put("view_id", DEFAULT_VIEW);
        
        restTemplate.postForEntity(TRANSFER_URL, entity, String.class, params);
    }
}