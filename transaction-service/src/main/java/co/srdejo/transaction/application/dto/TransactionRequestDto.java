package co.srdejo.transaction.application.dto;

public class TransactionRequestDto {
    private long fromAccount;
    private long toAccount;
    private double amount;

    public TransactionRequestDto() {
    }

    public TransactionRequestDto(long fromAccount, long toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }

    public long getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(long fromAccount) {
        this.fromAccount = fromAccount;
    }

    public long getToAccount() {
        return toAccount;
    }

    public void setToAccount(long toAccount) {
        this.toAccount = toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
