package com.odg18.banksystem.product;


import com.odg18.banksystem.common.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @SequenceGenerator(name="product_id_seq",
            sequenceName="product_id_seq", initialValue = 100,
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="product_id_seq")
    private Long id;

    private String name;

    private Boolean automaticAcceptation = Boolean.FALSE;

    private Boolean automaticExit = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    private ClientStatus minimumClientStatus = ClientStatus.BRONZE;

}
