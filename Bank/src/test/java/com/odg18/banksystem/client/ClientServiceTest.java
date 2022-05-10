package com.odg18.banksystem.client;

import com.odg18.banksystem.common.ClientStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;

@Tag("UnitTests")
@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock private ClientRepository clientRepository;

    private ClientService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ClientService(clientRepository);
    }

    @Test
    void getClientTest(){
        //given


        //when
        underTest.getClient(Long.valueOf(1));

        //then
        verify(clientRepository).findById(Long.valueOf(1));
    }

    @Test
    void getClientByNameTest(){
        //given

        //when
        underTest.getClientByName("");

        //then
        verify(clientRepository).findByName("");
    }

    @Test
    void getClientsTest(){
        //given

        //when
        underTest.getClients();

        //then
        verify(clientRepository).findAll();
    }

    @Test
    void deleteClientTest(){
        //given

        //when
        underTest.deleteClient(Long.valueOf(1));

        //then
        verify(clientRepository).deleteById(Long.valueOf(1));
    }

    @Test
    void saveClientTest(){
        //given
        Client c = Client.builder()
                            .name("name")
                            .build();

        //when
        underTest.saveClient(c);

        //then
        verify(clientRepository).save(c);
    }

    @Test
    void upgradeClientWithStatusBronze(){
        //given
        String clientName = "name";
        Client c = Client.builder()
                .name(clientName)
                .status(ClientStatus.BRONZE)
                .build();
        given(clientRepository.findByName(clientName)).willReturn(Optional.of(c));

        //when
        underTest.upgradeClient("name");

        //then
        verify(clientRepository).save(c);
    }

    @Test
    void upgradeClientWithStatusSilver(){
        //given
        String clientName = "name";
        Client c = Client.builder()
                .name(clientName)
                .status(ClientStatus.SILVER)
                .build();
        given(clientRepository.findByName(clientName)).willReturn(Optional.of(c));

        //when
        underTest.upgradeClient("name");

        //then
        verify(clientRepository).save(c);
    }

    @Test
    void upgradeClientNotExist(){
        //given
        String clientName = "no-name";
        Client c = Client.builder()
                .name(clientName)
                .status(ClientStatus.BRONZE)
                .build();
        c.setId(Long.valueOf(456));
        c.setUsername(clientName);
        c.setPassword("pass");
        c.getId();
        c.getUsername();
        c.getPassword();
        c.getName();

        given(clientRepository.findByName(clientName)).willReturn(Optional.empty());

        //when
        underTest.upgradeClient(clientName);

        //then
        verify(clientRepository, never()).save(c);
    }

    @Test
    void downgradeClientWithStatusGold(){
        //given
        String clientName = "name";
        Client c = Client.builder()
                .name(clientName)
                .status(ClientStatus.GOLD)
                .build();
        given(clientRepository.findByName(clientName)).willReturn(Optional.of(c));

        //when
        underTest.downgradeClient("name");

        //then
        verify(clientRepository).save(c);
    }

    @Test
    void downgradeClientWithStatusSilver(){
        //given
        String clientName = "name";
        Client c = Client.builder()
                .name(clientName)
                .status(ClientStatus.SILVER)
                .build();
        given(clientRepository.findByName(clientName)).willReturn(Optional.of(c));

        //when
        underTest.downgradeClient("name");

        //then
        verify(clientRepository).save(c);
    }

    @Test
    void downgradeClientNotExist(){
        //given
        String clientName = "no-name";
        Client c = new Client();
        c.setStatus(ClientStatus.BRONZE);
        c.setName(clientName);

        given(clientRepository.findByName(clientName)).willReturn(Optional.empty());

        //when
        underTest.downgradeClient(clientName);

        //then
        verify(clientRepository, never()).save(c);
    }

}
