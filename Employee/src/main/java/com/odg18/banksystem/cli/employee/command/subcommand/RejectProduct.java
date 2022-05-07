package com.odg18.banksystem.cli.employee.command.subcommand;

import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class RejectProduct implements Runnable{

    private String clientName;

    private String productId;

    private String bankSystemUrl;

    public RejectProduct(String bankSystemUrl, String clientName, String productId){
        this.clientName = clientName;
        this.productId = productId;
        this.bankSystemUrl = bankSystemUrl;
    }

    @SneakyThrows
    @Override
    public void run(){
        final String uri = this.bankSystemUrl + "client/reject-product";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("clientName", this.clientName);
        map.add("productId", this.productId);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( uri, request , String.class );

        if(response.getStatusCode() == HttpStatus.OK){
            System.out.println("Product " + this.productId + " has been rejected for client " + this.clientName + ".");
        }
        else{
            System.out.println("Error occurred:" + response.toString());
        }
    }
}
