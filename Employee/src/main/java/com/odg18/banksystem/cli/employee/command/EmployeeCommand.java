package com.odg18.banksystem.cli.employee.command;

import com.odg18.banksystem.cli.employee.command.subcommand.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Command(
        name = "employee",
        description = "Commands for the employee of the Bank",
        mixinStandardHelpOptions = true)
public class EmployeeCommand implements Runnable, ExitCodeGenerator {

    private int exitCode;

    @Value("${bank.url}")
    private String bankSystemUrl;

    @Option(names = {"-a", "--add"})
    private String clientNameToAdd;

    @Option(names = {"-l", "--list"})
    private String listProductsOfClient;

    @Option(names = {"-ac", "--accept"})
    private String productIdAccepted;

    @Option(names = {"-r", "--reject"})
    private String productIdRejected;

    @Option(names = {"-c", "--client"})
    private String clientName;

    @Option(names = {"-t", "--tasks"})
    private boolean tasks;

    @Option(names = {"-u", "--upgrade"})
    private String upgradeClient;

    @Option(names = {"-d", "--downgrade"})
    private String downgradeClient;

    @Override
    public void run() {

        if(this.clientNameToAdd != null){
            AddClient command = new AddClient(this.bankSystemUrl, this.clientNameToAdd);
            command.run();
        }
        else if(this.listProductsOfClient != null){
            ListProductOfClient command = new ListProductOfClient(this.bankSystemUrl, this.listProductsOfClient);
            command.run();
        }
        else if(this.productIdAccepted != null && this.clientName != null){
            AcceptProduct command = new AcceptProduct(this.bankSystemUrl, this.clientName, this.productIdAccepted);
            command.run();
        }
        else if(this.productIdRejected != null && this.clientName != null){
            RejectProduct command = new RejectProduct(this.bankSystemUrl, this.clientName, this.productIdRejected);
            command.run();
        }
        else if(tasks){
            WaitingClient command = new WaitingClient(this.bankSystemUrl);
            command.run();
        }
        else if(this.upgradeClient != null){
            UpgradeClient command = new UpgradeClient(this.bankSystemUrl, this.upgradeClient);
            command.run();
        }
        else if(this.downgradeClient != null){
            DowngradeClient command = new DowngradeClient(this.bankSystemUrl, this.downgradeClient);
            command.run();
        }
        else{
            System.out.println("Invalid command");
        }
    }

    @Override
    public int getExitCode() {
        return exitCode;
    }
}