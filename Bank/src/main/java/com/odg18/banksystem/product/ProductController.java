package com.odg18.banksystem.product;

import com.odg18.banksystem.client.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@Tag(name = "Product", description = "The product API")
@RequestMapping(path = "/api/v1/product")

public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get a product by ID", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product returned",
                    content = @Content(schema = @Schema(implementation = Product.class)))
    })
    @GetMapping("/{id}")
    public Product getProduct(
            @Parameter(description = "Id of the product", required = true, schema = @Schema(implementation = Long.class))@PathVariable("id") final Long id) {
        Optional<Product> product = productService.getProduct(id);
        if(product.isPresent()) {
            return product.get();
        } else {
            return null;
        }
    }

    @Operation(summary = "Get a list of products from a client", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lists of products returned",
                    content = @Content(schema = @Schema(implementation = List.class)))
    })
    @GetMapping("/from-client/{name}")
    public List<Product> getProductsFromClient(
            @Parameter(description = "name of the client", required = true, schema = @Schema(implementation = String.class)) @PathVariable("name") final String name){
        return  productService.getProductsForClient(name);
    }
}
