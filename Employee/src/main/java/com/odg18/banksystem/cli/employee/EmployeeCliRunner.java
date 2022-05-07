package com.odg18.banksystem.cli.employee;

import com.odg18.banksystem.cli.employee.command.EmployeeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

@Component
public class EmployeeCliRunner implements CommandLineRunner, ExitCodeGenerator {

    private int exitCode;

    @Autowired
    EmployeeCommand employeeCommand;

    @Override
    public void run(String... args) throws Exception {
        exitCode = new CommandLine(employeeCommand).execute(args);
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}
