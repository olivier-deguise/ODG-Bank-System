package com.odg18.banksystem.product;


import com.odg18.banksystem.common.ClientStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "ID of the product", example = "55", required = true)
    private Long id;

    @Schema(description = "Name of the product", example = "Super product!", required = true)
    private String name;

    @Schema(description = "Set if the product is accepted automatically", example = "false", required = true)
    private Boolean automaticAcceptation = Boolean.FALSE;

    @Schema(description = "Set if the product is exited automatically", example = "false", required = true)
    private Boolean automaticExit = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Minimum client status for this product", example = "SILVER", required = true)
    private ClientStatus minimumClientStatus = ClientStatus.BRONZE;

}
