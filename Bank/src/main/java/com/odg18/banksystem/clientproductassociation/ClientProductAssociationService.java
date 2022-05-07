package com.odg18.banksystem.clientproductassociation;

import com.odg18.banksystem.clientproductassociation.exception.AssociationNotFoundException;
import com.odg18.banksystem.common.ClientProductStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientProductAssociationService {

    private final ClientProductAssociationRepository clientProductAssociationRepository;

    public List<ClientProductAssociation> findByClientName(String name){
        return clientProductAssociationRepository.findByClient_name(name);
    }

    public List<ClientProductAssociation> getWaitingClients(){
        return clientProductAssociationRepository.findByStatusOrStatus(ClientProductStatus.WAITING_FOR_SUBSCRIBE, ClientProductStatus.WAITING_FOR_UNSUBSCRIBE);
    }

    public void acceptProduct(String clientName, Long productId){
        List<ClientProductAssociation> associations = clientProductAssociationRepository.findByClient_nameAndProduct_id(clientName, productId);
        int counter = 0;

        for(ClientProductAssociation association : associations){
            if(association.getStatus().equals(ClientProductStatus.WAITING_FOR_SUBSCRIBE)){
                association.setStatus(ClientProductStatus.SUBSCRIBED);
                association.setTimestamp(LocalDateTime.now());
                clientProductAssociationRepository.saveAndFlush(association);
                counter++;
            }
            else if(association.getStatus().equals(ClientProductStatus.WAITING_FOR_UNSUBSCRIBE)){
                association.setStatus(ClientProductStatus.UNSUBSCRIBED);
                association.setTimestamp(LocalDateTime.now());
                clientProductAssociationRepository.saveAndFlush(association);
                counter++;
            }
        }

        if(counter == 0){
            throw new AssociationNotFoundException(clientName, productId);
        }
    }

    public void rejectProduct(String clientName, Long productId){
        List<ClientProductAssociation> associations = clientProductAssociationRepository.findByClient_nameAndProduct_id(clientName, productId);
        int counter = 0;

        for(ClientProductAssociation association : associations){
            if(association.getStatus().equals(ClientProductStatus.WAITING_FOR_SUBSCRIBE)){
                association.setStatus(ClientProductStatus.REJECTED);
                association.setTimestamp(LocalDateTime.now());
                clientProductAssociationRepository.saveAndFlush(association);
                counter++;
            }
            else if(association.getStatus().equals(ClientProductStatus.WAITING_FOR_UNSUBSCRIBE)){
                association.setStatus(ClientProductStatus.SUBSCRIBED);
                association.setTimestamp(LocalDateTime.now());
                clientProductAssociationRepository.saveAndFlush(association);
                counter++;
            }
        }

        if(counter == 0){
            throw new AssociationNotFoundException(clientName, productId);
        }
    }

}
