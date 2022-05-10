package com.odg18.banksystem.client;

import com.odg18.banksystem.clientproductassociation.ClientProductAssociation;
import com.odg18.banksystem.clientproductassociation.ClientProductAssociationService;
import com.odg18.banksystem.common.ClientProductStatus;
import com.odg18.banksystem.common.ClientStatus;
import com.odg18.banksystem.product.Product;
import com.odg18.banksystem.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Optional<Client> getClient(final Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> getClientByName(final String name) {
        return clientRepository.findByName(name);
    }

    public Iterable<Client> getClients() {
        return clientRepository.findAll();
    }

    public void deleteClient(final Long id) {
        clientRepository.deleteById(id);
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public void upgradeClient(String clientName){
        Client client = clientRepository.findByName(clientName).get();
        if(client.getStatus().equals(ClientStatus.BRONZE)){
            client.setStatus(ClientStatus.SILVER);
        }
        else if(client.getStatus().equals(ClientStatus.SILVER)){
            client.setStatus(ClientStatus.GOLD);
        }
        clientRepository.save(client);
    }

    public void downgradeClient(String clientName){
        Client client = clientRepository.findByName(clientName).get();
        if(client.getStatus().equals(ClientStatus.SILVER)){
            client.setStatus(ClientStatus.BRONZE);
        }
        else if(client.getStatus().equals(ClientStatus.GOLD)){
            client.setStatus(ClientStatus.SILVER);
        }
        clientRepository.save(client);
    }

}
