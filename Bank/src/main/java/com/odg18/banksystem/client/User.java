package com.odg18.banksystem.client;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

@Data
@MappedSuperclass
public abstract class User {

    @Id
    @SequenceGenerator(name="client_id_seq",
            sequenceName="client_id_seq", initialValue = 100,
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="client_id_seq")
    @Schema(description = "ID of the user", example = "101", required = true)
    private Long id;

    @Schema(description = "name of the user", example = "username", required = false)
    private String username;

    @Schema(description = "password of the user", example = "pa$$W0rd", required = false)
    private String password;

}
