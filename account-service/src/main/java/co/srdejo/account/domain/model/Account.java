package co.srdejo.account.domain.model;

import co.srdejo.account.domain.exception.InsufficientFundsException;
import co.srdejo.account.domain.exception.NegativeAmountException;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
public class Account {

    private static final Logger log = LoggerFactory.getLogger(Account.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String name;

    @Column(name = "saldo")
    private double balance;

    @Version
    private Integer version;

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new NegativeAmountException("Amount must be positive");
        }
        this.balance = this.balance + amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new NegativeAmountException("Amount must be positive");
        }
        if (this.balance < amount ) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        this.balance = this.balance - amount;
    }


    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public Account() {
        // required for entity
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
}
