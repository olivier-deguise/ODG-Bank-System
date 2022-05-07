package com.odg18.banksystem.clientproductassociation;

import com.odg18.banksystem.client.Client;
import com.odg18.banksystem.common.ClientProductStatus;
import com.odg18.banksystem.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientProductAssociation {

    @Id
    @SequenceGenerator(name="client_product_association_id_seq",
            sequenceName="client_product_association_id_seq", initialValue = 100,
            allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator="client_product_association_id_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name="clientId")
    private Client client;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private ClientProductStatus status;

}
