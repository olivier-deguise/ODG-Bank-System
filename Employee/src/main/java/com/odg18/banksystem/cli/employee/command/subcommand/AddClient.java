package com.odg18.banksystem.cli.employee.command.subcommand;

import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class AddClient implements Runnable{

    private String clientName;

    private String bankSystemUrl;

    public AddClient(String bankSystemUrl, String clientName){
        this.clientName = clientName;
        this.bankSystemUrl = bankSystemUrl;
    }

    @SneakyThrows
    @Override
    public void run(){
        final String uri = this.bankSystemUrl + "client";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("clientName", this.clientName);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( uri, request , String.class );

        if(response.getStatusCode() == HttpStatus.OK){
            System.out.println("Client " + this.clientName + " has been created successfully.");
        }
        else{
            System.out.println("Error occurred:" + response.toString());
        }
    }
}
