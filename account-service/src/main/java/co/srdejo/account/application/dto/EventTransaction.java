package co.srdejo.account.application.dto;

public class EventTransaction extends TransactionRequestDto{
    private long transactionId;

    public EventTransaction() {
    }

    public EventTransaction(long fromAccount, long toAccount, double amount, long transactionId) {
        super(fromAccount, toAccount, amount);
        this.transactionId = transactionId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }
}
