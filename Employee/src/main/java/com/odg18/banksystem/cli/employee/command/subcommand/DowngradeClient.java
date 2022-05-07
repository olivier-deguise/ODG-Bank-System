package com.odg18.banksystem.cli.employee.command.subcommand;

import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class DowngradeClient implements Runnable{

    private String clientName;

    private String bankSystemUrl;

    public DowngradeClient(String bankSystemUrl, String clientName){
        this.clientName = clientName;
        this.bankSystemUrl = bankSystemUrl;
    }

    @SneakyThrows
    @Override
    public void run(){
        final String uri = this.bankSystemUrl + "client/downgrade";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("clientName", this.clientName);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity( uri, request , String.class );


        if(response.getStatusCode() == HttpStatus.OK){
            final String uriToGetStatus = this.bankSystemUrl + "client/name/" + this.clientName;
            RestTemplate restTemplateToGetStatus = new RestTemplate();
            HttpHeaders headersToGetStatus = new HttpHeaders();
            headersToGetStatus.setContentType(MediaType.APPLICATION_JSON);

            ResponseEntity<String> responseToGetStatus = restTemplateToGetStatus.getForEntity( uriToGetStatus, String.class );

            String status = new JSONObject(responseToGetStatus.getBody()).getString("status");
            System.out.println("Client " + this.clientName + " has been downgraded to status " + status + ".");
        }
        else{
            System.out.println("Error occurred:" + response.toString());
        }
    }
}
