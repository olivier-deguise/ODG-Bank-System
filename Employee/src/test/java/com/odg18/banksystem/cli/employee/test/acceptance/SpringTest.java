package com.odg18.banksystem.cli.employee.test.acceptance;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = com.odg18.banksystem.cli.employee.EmployeeCliApplication.class)
public class SpringTest {
}
