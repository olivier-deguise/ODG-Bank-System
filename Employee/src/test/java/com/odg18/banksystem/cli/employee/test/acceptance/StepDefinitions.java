package com.odg18.banksystem.cli.employee.test.acceptance;

import com.odg18.banksystem.cli.employee.EmployeeCliRunner;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class StepDefinitions extends SpringTest{


    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    EmployeeCliRunner runner;

    private String command;
    private String arg1;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;


    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    @Before
    public void setUp() {
        log.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }

    @Given("the employee pass the following parameter {word} {word}")
    public void setParameter(String command, String arg1) throws Exception {
        this.command = command;
        this.arg1 = arg1;
        System.setOut(new PrintStream(outContent));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("clientName", arg1);
        ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.OK);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        given(restTemplate.postForEntity("http://localhost:9900/bank-system/api/v1/", request , String.class )).willReturn(response);
        //given(restTemplate.postForEntity(anyString(), request , String.class )).willReturn(response);

    }

    @When("the employee press enter")
    public void pressEnter() throws Exception{
        runner.run(this.command, this.arg1);
    }

    @Then("the console must contain the following String : '{word}'")
    public void consoleOutput(String consoleOutput){
        assertThat(consoleOutput).isEqualTo(this.outContent.toString());
        System.setOut(originalOut);
    }

}
