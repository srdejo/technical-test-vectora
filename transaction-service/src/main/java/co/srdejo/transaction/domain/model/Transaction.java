package co.srdejo.transaction.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_seq", allocationSize = 1, initialValue = 1000)
    private Long id;
    @Column(name = "from_account")
    private long fromAccount;
    @Column(name = "to_account")
    private long toAccount;
    @Column(name = "monto")
    private double amount;
    private TransactionStatus status;
    @Column(name = "fecha", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd hh:mm:ss a")
    private LocalDateTime dateTime;

    public Transaction(long fromAccount, long toAccount, double amount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
        this.status = TransactionStatus.PENDING;
    }

    public Transaction() {

    }

    public Long getId() {
        return id;
    }

    public long getFromAccount() {
        return fromAccount;
    }

    public long getToAccount() {
        return toAccount;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void transactionStatusChanged(TransactionStatus status) {
        this.status = status;
    }
}
