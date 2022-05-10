package com.odg18.banksystem.product;

import com.odg18.banksystem.clientproductassociation.ClientProductAssociation;
import com.odg18.banksystem.clientproductassociation.ClientProductAssociationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final ClientProductAssociationService clientProductAssociationService;

    public Optional<Product> getProduct(final Long id) {
        return productRepository.findById(id);
    }

    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsForClient(String name) {
        List<ClientProductAssociation> association = clientProductAssociationService.findByClientName(name);

        List<Long> ids = association.stream().map(ClientProductAssociation::getProduct).collect(Collectors.toList()).stream().map(Product::getId).collect(Collectors.toList());

        return productRepository.findAllById(ids);
    }

    public void deleteProduct(final Long id) {
        productRepository.deleteById(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
