package co.srdejo.transaction.application.dto;

public class EventTransaction extends TransactionRequestDto{
    private long transactionId;

    public EventTransaction(long fromAccount, long toAccount, double amount, long transactionId) {
        super(fromAccount, toAccount, amount);
        this.transactionId = transactionId;
    }

    public long getTransactionId() {
        return transactionId;
    }
}
