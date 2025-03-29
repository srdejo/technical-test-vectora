package co.srdejo.transaction.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransactionRequestDto {
    @NotNull(message = "La cuenta de origen es obligatoria")
    @Positive(message = "No es una cuenta valida")
    private Long fromAccount;
    @NotNull(message = "La cuenta de destino es obligatoria")
    @Positive(message = "No es una cuenta valida")
    private Long toAccount;
    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto no es un valor valido")
    private Double amount;

    public TransactionRequestDto() {
    }

    public TransactionRequestDto(Long fromAccount, Long toAccount, Double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public Long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Long getToAccount() {
        return toAccount;
    }

    public void setToAccount(Long toAccount) {
        this.toAccount = toAccount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
