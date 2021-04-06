package com.example.transfer.controller;

import java.math.BigDecimal;

import com.example.transfer.model.User;
import com.example.transfer.service.TransferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    @Autowired
    TransferService transferService;

    @RequestMapping(value = "/operation/login", method = RequestMethod.POST)
    public void login(@RequestParam String username, @RequestParam String password) {
        transferService.login(new User(username, password));
    }

    @RequestMapping(value = "/operation/transfer", method = RequestMethod.POST)
    public void transfer(@RequestParam String sender,
                         @RequestParam String receiver,
                         @RequestParam String concept, 
                         @RequestParam BigDecimal amount) {

        transferService.transfer(sender, receiver, concept, amount);
    }
    
}
