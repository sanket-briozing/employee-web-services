package com.briozing.employees.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {

    @Autowired
    RestTemplate restTemplate;

    public HttpStatus get(String url, Class className){
        System.out.println("URL : =>" + url);
        HttpStatus status = HttpStatus.OK;
        try {
            restTemplate.getForObject(url, className);
        }catch(HttpClientErrorException e){
            status = e.getStatusCode();
        }
        return status;
    }
}
