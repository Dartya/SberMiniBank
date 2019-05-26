package ru.sber.alex.minibank.layers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class BusinessLogic {

    @Autowired
    private RestTemplate restTemplate;
}
