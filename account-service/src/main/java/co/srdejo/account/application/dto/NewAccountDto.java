package co.srdejo.account.application.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class NewAccountDto {

    @NotBlank(message = "El nombre es obligatorio")
    @JsonAlias("nombre")
    private String name;

    @NotNull(message = "El saldo inicial es obligatorio")
    @Positive(message = "El saldo inicial no tiene un valor valido")
    @JsonAlias("saldoInicial")
    private Double initialBalance;

    public NewAccountDto() {}
    public NewAccountDto(String name, Double initialBalance) {
        this.name = name;
        this.initialBalance = initialBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(Double initialBalance) {
        this.initialBalance = initialBalance;
    }
}
