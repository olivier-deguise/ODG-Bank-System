package com.odg18.banksystem.clientproductassociation;

import com.odg18.banksystem.client.Client;
import com.odg18.banksystem.common.ClientProductStatus;
import com.odg18.banksystem.product.Product;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "ID of the ClientProductAssociation", example = "55", required = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name="clientId")
    @Schema(description = "Client of the ClientProductAssociation", required = true)
    private Client client;

    @ManyToOne
    @JoinColumn(name="productId")
    @Schema(description = "Product of the ClientProductAssociation", required = true)
    private Product product;

    @Schema(description = "Timestamp of the ClientProductAssociation", required = true)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status of the ClientProductAssociation", required = true)
    private ClientProductStatus status;

}
