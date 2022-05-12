package com.odg18.banksystem.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odg18.banksystem.client.Client;
import com.odg18.banksystem.client.ClientController;
import com.odg18.banksystem.client.ClientRepository;
import com.odg18.banksystem.clientproductassociation.ClientProductAssociation;
import com.odg18.banksystem.clientproductassociation.ClientProductAssociationRepository;
import com.odg18.banksystem.common.ClientProductStatus;
import com.odg18.banksystem.common.ClientStatus;
import com.odg18.banksystem.product.Product;
import com.odg18.banksystem.product.ProductRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-it.properties")
@AutoConfigureMockMvc
@Tag("IntegrationTests")
class ClientControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientProductAssociationRepository clientProductAssociationRepository;

    @Autowired
    private ClientController underTest;

    @Test
    @SneakyThrows
    void saveClient(){
        //given
        Client c = Client.builder()
                .name("name")
                .status(ClientStatus.BRONZE)
                .build();
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("clientName", c.getName());

        //when

        //then
        ResultActions resultActions = mockMvc
                    .perform(post("/api/v1/client?clientName="+c.getName())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(""))
                    .andDo(print())
                    .andExpect(status().isOk());

        List<Client> clients = clientRepository.findAll();
        assertThat(clients).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .contains(c);
    }

    @Test
    @SneakyThrows
    void getClientNonExisting(){
       //given
        clientRepository.deleteAllInBatch();
        String nonExistingId = "888";

       //when

       //then
        MvcResult result = mockMvc
                .perform(get("/api/v1/client/" + nonExistingId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    @SneakyThrows
    void getClientExisting(){
        //given
        clientRepository.deleteAllInBatch();
        Client c = Client.builder()
                .name("name")
                .status(ClientStatus.BRONZE)
                .build();
        clientRepository.saveAndFlush(c);
        Client c1 = clientRepository.findByName(c.getName()).get();

        //when

        //then
        MvcResult result = mockMvc
                .perform(get("/api/v1/client/" + c1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Client c2 = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(c2).isEqualTo(c1);
        clientRepository.deleteAllInBatch();
    }

    @Test
    @SneakyThrows
    void getClientByNameNonExisting(){
        //given
        clientRepository.deleteAllInBatch();
        String nonExistingName = "xyz";

        //when

        //then
        MvcResult result = mockMvc
                .perform(get("/api/v1/client/name/" + nonExistingName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    @SneakyThrows
    void getClientByNameExisting(){
        //given
        clientRepository.deleteAllInBatch();
        Client c = Client.builder()
                .name("name")
                .status(ClientStatus.BRONZE)
                .build();
        clientRepository.saveAndFlush(c);
        Client c1 = clientRepository.findByName(c.getName()).get();

        //when

        //then
        MvcResult result = mockMvc
                .perform(get("/api/v1/client/name/" + c1.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Client c2 = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(c2).isEqualTo(c1);
        clientRepository.deleteAllInBatch();
    }

    @Test
    @SneakyThrows
    void getWaitingClient(){
        //given
        Client c1 = Client.builder()
                .name("name1")
                .status(ClientStatus.BRONZE)
                .build();

        Client c2 = Client.builder()
                .name("name2")
                .status(ClientStatus.BRONZE)
                .build();

        Product p1 = Product.builder()
                .name("p1")
                .automaticAcceptation(Boolean.FALSE)
                .automaticExit(Boolean.FALSE)
                .minimumClientStatus(ClientStatus.BRONZE)
                .build();

        Product p2 = Product.builder()
                .name("p2")
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

        clientRepository.saveAndFlush(c1);
        clientRepository.saveAndFlush(c2);
        productRepository.saveAndFlush(p1);
        productRepository.saveAndFlush(p2);
        clientProductAssociationRepository.saveAndFlush(ass1);
        clientProductAssociationRepository.saveAndFlush(ass2);

        //when
        MvcResult result = mockMvc
                .perform(get("/api/v1/client/waiting-clients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();


        //then
        List<ClientProductAssociation> list = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});
        assertThat(list).contains(ass1, ass2);

        clientProductAssociationRepository.deleteAllInBatch();
        clientRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
    }

}
