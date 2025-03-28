package co.srdejo.account.application.dto;

public class TransactionResponseDto {
    private String status;
    private long transactionId;

    public TransactionResponseDto() {
    }

    public TransactionResponseDto(String status, long transactionId) {
        this.status = status;
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }
}
