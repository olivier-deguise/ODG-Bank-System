package com.odg18.banksystem.cli.employee.command.subcommand;

import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ListProductOfClient implements Runnable{

    private String clientName;

    private String bankSystemUrl;

    public ListProductOfClient(String bankSystemUrl, String clientName){
        this.clientName = clientName;
        this.bankSystemUrl = bankSystemUrl;
    }

    @SneakyThrows
    @Override
    public void run(){
        final String uri = this.bankSystemUrl + "product/from-client/" + this.clientName;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = restTemplate.getForEntity( uri, String.class );

        if(response.getStatusCode() == HttpStatus.OK){
            JSONArray jsonArrayObj = new JSONArray(response.getBody());

            for(int i =0; i < jsonArrayObj.length(); i++){
                JSONObject product = jsonArrayObj.getJSONObject(i);
                String name = product.getString("name");
                System.out.println(name);
            }
            //System.out.println("response body=" + response.getBody());
        }
        else{
            System.out.println("Error occurred:" + response.toString());
        }
    }
}
