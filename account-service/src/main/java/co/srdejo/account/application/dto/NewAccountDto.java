package co.srdejo.account.application.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class NewAccountDto {

    @JsonAlias("nombre")
    private String name;

    @JsonAlias("saldoInicial")
    private double initialBalance;

    public NewAccountDto() {}
    public NewAccountDto(String name, double initialBalance) {
        this.name = name;
        this.initialBalance = initialBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(double initialBalance) {
        this.initialBalance = initialBalance;
    }
}
