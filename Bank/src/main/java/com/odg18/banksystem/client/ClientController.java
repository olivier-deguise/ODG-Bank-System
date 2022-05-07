package com.odg18.banksystem.client;

import com.odg18.banksystem.clientproductassociation.ClientProductAssociation;
import com.odg18.banksystem.clientproductassociation.ClientProductAssociationService;
import com.odg18.banksystem.common.ClientStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    private final ClientProductAssociationService clientProductAssociationService;

    @GetMapping("/{id}")
    public Client getClient(@PathVariable("id") final Long id) {
        Optional<Client> client = clientService.getClient(id);
        if(client.isPresent()) {
            return client.get();
        } else {
            return null;
        }
    }

    @GetMapping("/name/{name}")
    public Client getClientByName(@PathVariable("name") final String name) {
        Optional<Client> client = clientService.getClientByName(name);
        if(client.isPresent()) {
            return client.get();
        } else {
            return null;
        }
    }

    @GetMapping("/waiting-clients")
    public List<ClientProductAssociation> getWaitingClients(){
        return clientProductAssociationService.getWaitingClients();
    }

    @PostMapping
    public ResponseEntity saveClient(@RequestParam("clientName") String clientName){
        Client client = Client.builder()
                                .name(clientName)
                                .status(ClientStatus.BRONZE)
                                .build();

        clientService.saveClient(client);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept-product")
    public ResponseEntity acceptProductForClient(@RequestParam("clientName") String clientName, @RequestParam("productId") Long productId){
        clientProductAssociationService.acceptProduct(clientName, productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject-product")
    public ResponseEntity rejectProductForClient(@RequestParam("clientName") String clientName, @RequestParam("productId") Long productId){
        clientProductAssociationService.rejectProduct(clientName, productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upgrade")
    public ResponseEntity upgradeClient(@RequestParam("clientName") String clientName){
        clientService.upgradeClient(clientName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/downgrade")
    public ResponseEntity downgradeClient(@RequestParam("clientName") String clientName){
        clientService.downgradeClient(clientName);
        return ResponseEntity.ok().build();
    }
}
