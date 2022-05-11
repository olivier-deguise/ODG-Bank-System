package com.odg18.banksystem.client;

import com.odg18.banksystem.common.ClientStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Name of the client", example = "Bob", required = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status of the client", example = "BRONZE", required = true)
    private ClientStatus status = ClientStatus.BRONZE;
}
