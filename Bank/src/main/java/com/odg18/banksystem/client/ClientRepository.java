package com.odg18.banksystem.client;

import com.odg18.banksystem.common.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByName(String name);

    List<Client> findByStatus(ClientStatus status);

}
