package com.briozing.employees.service;

import com.briozing.employees.models.CountryRequestVO;
import com.briozing.employees.models.CountryResponseVO;
import com.briozing.employees.models.EmployeeRequestVO;
import com.briozing.employees.models.EmployeeResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CountryService {

    RestService restService;

    public CountryService(@Autowired RestService restService){
        this.restService = restService;
    }

    @Value("${service.url}")
    private String baseUrl;

    public HttpStatus FindByName(String countryName) {
        String url = baseUrl +"findByName/"+ countryName;
        return restService.get(url, CountryResponseVO.class);
    }

    public HttpStatus FindById(String countryId) {
        String url = baseUrl +"findById/"+ countryId;
        return restService.get(url, CountryResponseVO.class);
    }

    public HttpStatus addCountry(CountryRequestVO countryRequestVO){
        String url = baseUrl +"add";
        return restService.post(url,countryRequestVO, CountryRequestVO.class);
    }

}