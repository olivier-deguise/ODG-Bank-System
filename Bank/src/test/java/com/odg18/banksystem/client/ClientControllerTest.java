package com.odg18.banksystem.client;

import com.odg18.banksystem.clientproductassociation.ClientProductAssociationService;
import com.odg18.banksystem.common.ClientStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;

@Tag("UnitTests")
@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    private ClientController underTest;

    @Mock
    private ClientService clientService;

    @Mock
    private ClientProductAssociationService clientProductAssociationService;


    @BeforeEach
    void setUp() {
        underTest = new ClientController(clientService, clientProductAssociationService);
    }

    @Test
    void getClientPresent(){
        //given
        String clientName = "name";
        Long id = Long.valueOf(333);
        Client c = Client.builder()
                .name(clientName)
                .status(ClientStatus.BRONZE)
                .build();
        Optional<Client> optClient = Optional.of(c);
        given(clientService.getClient(id)).willReturn(optClient);

        //when
        Client actualClient = underTest.getClient(id);

        //then
        assertThat(actualClient).isEqualTo(c);
    }

    @Test
    void getClientNotPresent(){
        //given
        String clientName = "name";
        Long id = Long.valueOf(333);
        given(clientService.getClient(id)).willReturn(Optional.empty());

        //when
        Client actualClient = underTest.getClient(id);

        //then
        assertThat(actualClient).isNull();
    }

    @Test
    void getClientByNamePresent(){
        //given
        String clientName = "name";
        Long id = Long.valueOf(333);
        Client c = Client.builder()
                .name(clientName)
                .status(ClientStatus.BRONZE)
                .build();
        Optional<Client> optClient = Optional.of(c);
        given(clientService.getClientByName(clientName)).willReturn(optClient);

        //when
        Client actualClient = underTest.getClientByName(clientName);

        //then
        assertThat(actualClient).isEqualTo(c);
    }

    @Test
    void getClientByNotPresent(){
        //given
        String clientName = "name";
        Long id = Long.valueOf(333);
        given(clientService.getClientByName(clientName)).willReturn(Optional.empty());

        //when
        Client actualClient = underTest.getClientByName(clientName);

        //then
        assertThat(actualClient).isNull();
    }

    @Test
    void getWaitingClients(){
        //given

        //when
        underTest.getWaitingClients();

        //then
        verify(clientProductAssociationService).getWaitingClients();
    }

    @Test
    void saveClient(){
        //given
        String clientName = "name";
        Client client = Client.builder()
                .name(clientName)
                .status(ClientStatus.BRONZE)
                .build();

        //when
        underTest.saveClient(clientName);

        //then
        verify(clientService).saveClient(client);
    }

    @Test
    void acceptProductForClient(){
        //given
        String clientName = "name";
        Long productId = Long.valueOf(55);

        //when
        underTest.acceptProductForClient(clientName, productId);

        //then
        verify(clientProductAssociationService).acceptProduct(clientName, productId);
    }

    @Test
    void rejectProductForClient(){
        //given
        String clientName = "name";
        Long productId = Long.valueOf(55);

        //when
        underTest.rejectProductForClient(clientName, productId);

        //then
        verify(clientProductAssociationService).rejectProduct(clientName, productId);
    }

    @Test
    void upgradeClient(){
        //given
        String clientName = "name";

        //when
        underTest.upgradeClient(clientName);

        //then
        verify(clientService).upgradeClient(clientName);
    }

    @Test
    void downgradeClient(){
        //given
        String clientName = "name";

        //when
        underTest.downgradeClient(clientName);

        //then
        verify(clientService).downgradeClient(clientName);
    }
}
