package com.odg18.banksystem.product;

import com.odg18.banksystem.client.Client;
import com.odg18.banksystem.clientproductassociation.ClientProductAssociation;
import com.odg18.banksystem.clientproductassociation.ClientProductAssociationService;
import com.odg18.banksystem.common.ClientProductStatus;
import com.odg18.banksystem.common.ClientStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@Tag("UnitTests")
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ClientProductAssociationService clientProductAssociationService;

    private ProductService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ProductService(productRepository, clientProductAssociationService);
    }

    @Test
    void getProduct(){
        //given
        Long id = Long.valueOf(15);

        //when
        underTest.getProduct(id);

        //then
        verify(productRepository).findById(id);
    }

    @Test
    void getProducts(){
        //given

        //when
        underTest.getProducts();

        //then
        verify(productRepository).findAll();
    }

    @Test
    void getProductsForClient(){
        //given
        Client c1 = Client.builder()
                .name("name")
                .status(ClientStatus.BRONZE)
                .build();
        c1.setId(Long.valueOf(1));

        Client c2 = Client.builder()
                .name("name")
                .status(ClientStatus.BRONZE)
                .build();
        c2.setId(Long.valueOf(2));

        Product p1 = Product.builder()
                .name("p1")
                .id(Long.valueOf(55))
                .automaticAcceptation(Boolean.FALSE)
                .automaticExit(Boolean.FALSE)
                .minimumClientStatus(ClientStatus.BRONZE)
                .build();

        Product p2 = Product.builder()
                .name("p2")
                .id(Long.valueOf(57))
                .automaticAcceptation(Boolean.FALSE)
                .automaticExit(Boolean.FALSE)
                .minimumClientStatus(ClientStatus.BRONZE)
                .build();

        ClientProductAssociation ass1 = ClientProductAssociation.builder()
                .client(c1)
                .product(p1)
                .status(ClientProductStatus.WAITING_FOR_SUBSCRIBE)
                .build();

        ClientProductAssociation ass2 = ClientProductAssociation.builder()
                .client(c2)
                .product(p2)
                .status(ClientProductStatus.WAITING_FOR_UNSUBSCRIBE)
                .build();
        String name = "name";
        List<ClientProductAssociation> assList = new ArrayList<ClientProductAssociation>();
        assList.add(ass1);
        assList.add(ass2);
        List<Long> ids = new ArrayList<Long>();
        ids.add(ass1.getProduct().getId());
        ids.add(ass2.getProduct().getId());

        p1.getId();
        p1.getAutomaticAcceptation();
        p1.getAutomaticExit();
        p1.getName();
        p1.getMinimumClientStatus();

        given(clientProductAssociationService.findByClientName(name)).willReturn(assList);

        //when
        underTest.getProductsForClient(name);

        //then
        verify(productRepository).findAllById(ids);
    }

    @Test
    void deleteProduct(){
        //given
        Long id = Long.valueOf(15);

        //when
        underTest.deleteProduct(id);

        //then
        verify(productRepository).deleteById(id);
    }

    @Test
    void saveProduct(){
        //given
        Product p = new Product();
        p.setId(Long.valueOf(14));
        p.setAutomaticAcceptation(Boolean.FALSE);
        p.setAutomaticExit(Boolean.FALSE);
        p.setName("name");
        p.setMinimumClientStatus(ClientStatus.BRONZE);

        //when
        underTest.saveProduct(p);

        //then
        verify(productRepository).save(p);
    }

}
