package com.odg18.banksystem.cli.employee.test.acceptance;

import com.odg18.banksystem.cli.employee.EmployeeCliApplication;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Class to use spring application context while running cucumber
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = EmployeeCliApplication.class, loader = SpringBootContextLoader.class)
@Slf4j
public class CucumberSpringContextConfiguration {

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    @Before
    public void setUp() {
        log.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
    }
}
