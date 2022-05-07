package com.odg18.banksystem.client;

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
    private Long id;

    private String username;

    private String password;

}
