package com.odg18.banksystem.client;

import com.odg18.banksystem.clientproductassociation.ClientProductAssociation;
import com.odg18.banksystem.clientproductassociation.ClientProductAssociationService;
import com.odg18.banksystem.common.ClientStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/client")
@Tag(name = "Client", description = "The client API")
public class ClientController {

    private final ClientService clientService;

    private final ClientProductAssociationService clientProductAssociationService;

    @Operation(summary = "Get a client by ID", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client returned",
                    content = @Content(schema = @Schema(implementation = Client.class)))
    })
    @GetMapping(value ="/{id}")
    public Client getClient(
            @Parameter(description = "id of the client", required = true, schema = @Schema(implementation = Long.class)) @PathVariable("id") final Long id) {
        Optional<Client> client = clientService.getClient(id);
        if(client.isPresent()) {
            return client.get();
        } else {
            return null;
        }
    }

    @Operation(summary = "Get a client by name", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client returned",
                    content = @Content(schema = @Schema(implementation = Client.class)))
    })
    @GetMapping("/name/{name}")
    public Client getClientByName(
            @Parameter(description = "name of the client", required = true, schema = @Schema(implementation = String.class)) @PathVariable("name") final String name) {
        Optional<Client> client = clientService.getClientByName(name);
        if(client.isPresent()) {
            return client.get();
        } else {
            return null;
        }
    }

    @Operation(summary = "Get a list of client/product association waiting for actions", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of client/product returned",
                    content = @Content(schema = @Schema(implementation = ClientProductAssociation.class)))
    })
    @GetMapping("/waiting-clients")
    public List<ClientProductAssociation> getWaitingClients(){
        return clientProductAssociationService.getWaitingClients();
    }

    @Operation(summary = "Save a client with a specific name", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client created")
    })
    @PostMapping
    public ResponseEntity saveClient(
            @Parameter(description = "name of the client", required = true, schema = @Schema(implementation = String.class)) @RequestParam("clientName") String clientName){
        Client client = Client.builder()
                                .name(clientName)
                                .status(ClientStatus.BRONZE)
                                .build();

        clientService.saveClient(client);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Accept a product for a client", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A product has been accepted for a client")
    })
    @PostMapping("/accept-product")
    public ResponseEntity<Object> acceptProductForClient(
            @Parameter(description = "name of the client", required = true, schema = @Schema(implementation = String.class)) @RequestParam("clientName") String clientName,
            @Parameter(description = "id of the product", required = true, schema = @Schema(implementation = Long.class)) @RequestParam("productId") Long productId){
        clientProductAssociationService.acceptProduct(clientName, productId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reject a product for a client", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "A product has been rejected for a client")
    })
    @PostMapping("/reject-product")
    public ResponseEntity<Object> rejectProductForClient(
            @Parameter(description = "name of the client", required = true, schema = @Schema(implementation = String.class)) @RequestParam("clientName") String clientName,
            @Parameter(description = "id of the product", required = true, schema = @Schema(implementation = Long.class))@RequestParam("productId") Long productId){
        clientProductAssociationService.rejectProduct(clientName, productId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Upgrade a client status", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client has been upgraded")
    })
    @PostMapping("/upgrade")
    public ResponseEntity<Object> upgradeClient(
            @Parameter(description = "name of the client", required = true, schema = @Schema(implementation = String.class)) @RequestParam("clientName") String clientName){
        clientService.upgradeClient(clientName);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Downgrade a client status", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client has been downgraded")
    })
    @PostMapping("/downgrade")
    public ResponseEntity<Object> downgradeClient(
            @Parameter(description = "name of the client", required = true, schema = @Schema(implementation = String.class)) @RequestParam("clientName") String clientName){
        clientService.downgradeClient(clientName);
        return ResponseEntity.ok().build();
    }
}
