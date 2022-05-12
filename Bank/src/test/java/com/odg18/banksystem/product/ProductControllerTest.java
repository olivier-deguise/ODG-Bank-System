package com.odg18.banksystem.product;

import com.odg18.banksystem.common.ClientStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@Tag("UnitTests")
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    private ProductController underTest;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        underTest = new ProductController(productService);
    }

    @Test
    void getProductPresent(){
        //given
        Long id = Long.valueOf(333);
        Product p = Product.builder()
                .id(Long.valueOf(1))
                .name("name")
                .minimumClientStatus(ClientStatus.BRONZE)
                .automaticAcceptation(Boolean.FALSE)
                .automaticExit(Boolean.FALSE)
                .build();

        Optional<Product> optProduct= Optional.of(p);
        given(productService.getProduct(id)).willReturn(optProduct);

        //when
        Product actualProduct= underTest.getProduct(id);

        //then
        assertThat(actualProduct).isEqualTo(p);
    }

    @Test
    void getClientNotPresent(){
        //given
        Long id = Long.valueOf(333);
        given(productService.getProduct(id)).willReturn(Optional.empty());

        //when
        Product actualProduct = underTest.getProduct(id);

        //then
        assertThat(actualProduct).isNull();
    }

    @Test
    void getProductsFromClient(){
        //given
        String clientName = "name";

        //when
        underTest.getProductsFromClient(clientName);

        //then
        verify(productService).getProductsForClient(clientName);
    }

}
