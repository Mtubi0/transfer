package com.example.transfer.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import com.example.transfer.model.User;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TransferServiceTest {

    @Autowired
    private TransferService service;

    @Test
    public void loginTest() {
        User user = new User("mtubio96@gmail.com", "c4zlgXVLfYcjLoO");
        service.login(user);

        assertTrue(true);
    }

    @Test
    public void transferTest() {
        
        service.login(new User("mtubio96@gmail.com", "c4zlgXVLfYcjLoO"));
        service.transfer("21-1-99999-4-6", "3220001823000055910025", "VAR", BigDecimal.valueOf(10.0d));
    }
    
}
