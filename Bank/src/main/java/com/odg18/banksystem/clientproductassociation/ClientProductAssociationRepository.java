package com.odg18.banksystem.clientproductassociation;


import com.odg18.banksystem.common.ClientProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientProductAssociationRepository extends JpaRepository<ClientProductAssociation, Long> {

    List<ClientProductAssociation> findByClient_name(String name);

    List<ClientProductAssociation> findByStatusOrStatus(ClientProductStatus clientProductStatus1, ClientProductStatus clientProductStatus2);

    List<ClientProductAssociation> findByClient_nameAndProduct_id(String clientName, Long productId);
}
