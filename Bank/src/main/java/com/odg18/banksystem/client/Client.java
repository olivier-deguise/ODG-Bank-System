package com.odg18.banksystem.client;

import com.odg18.banksystem.common.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client extends User {

    private String name;

    @Enumerated(EnumType.STRING)
    private ClientStatus status = ClientStatus.BRONZE;
}
