package com.odg18.banksystem.clientproductassociation.exception;

public class AssociationNotFoundException extends RuntimeException {

    public AssociationNotFoundException(String clientName, Long productId){
        super("No waiting association clientName = " + clientName + " and productId=" + productId);
    }
}
