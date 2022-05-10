package com.odg18.banksystem.cli.employee.command.subcommand;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WaitingClient implements Runnable{

    private String bankSystemUrl;

    public WaitingClient(String bankSystemUrl){
        this.bankSystemUrl = bankSystemUrl;
    }

    @SneakyThrows
    @Override
    public void run(){
        final String uri = this.bankSystemUrl + "client/waiting-clients/";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.getForEntity( uri, String.class );

        if(response.getStatusCode() == HttpStatus.OK){
            JSONArray jsonArrayObj = new JSONArray(response.getBody());

            for(int i =0; i < jsonArrayObj.length(); i++){
                JSONObject association = jsonArrayObj.getJSONObject(i);
                String clientName = new JSONObject(association.getString("client")).getString("name");
                String productName = new JSONObject(association.getString("product")).getString("name");
                String status = association.getString("status");
                System.out.println("Client " + clientName + " waiting for product " + productName + " with status " + status + ".");
            }
        }
        else{
            System.out.println("Error occurred:" + response.toString());
        }
    }
}
