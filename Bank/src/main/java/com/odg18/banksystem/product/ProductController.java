package com.odg18.banksystem.product;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") final Long id) {
        Optional<Product> product = productService.getProduct(id);
        if(product.isPresent()) {
            return product.get();
        } else {
            return null;
        }
    }

    @GetMapping("/from-client/{name}")
    public List<Product> getProductsFromClient(@PathVariable("name") final String name){
        return  productService.getProductsForClient(name);
    }
}
