package com.eteration.simplebanking.model;


// This class is a place holder you can change the complete implementation

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the account owner.
     */
    private String owner;

    /**
     * The unique account number.
     */
    private String accountNumber;

    /**
     * The current balance of the account.
     */
    private double balance;

    /**
     * The date of the account creation.
     */
    private LocalDateTime createDate;

    /**
     * The transactions of the account owner.
     */
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String owner, String accountNumber) {
        this.owner = owner;
        this.accountNumber = accountNumber;
        this.balance = 0;
        this.createDate = LocalDateTime.now();
        this.transactions = new ArrayList<>();
    }

    public void post(Transaction transaction) {
        transaction.process(this);
    }

    /**
     * Cash inflow
     *
     * @param amount The amount to deposit. Value must be bigger than zero.
     * @throws IllegalArgumentException if the amount is negative.
     */
    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("The deposit amount cannot be negative.");
        }

        balance += amount;
    }

    /**
     * Cash outflow
     *
     * @param amount The amount to withdraw.
     * @throws InsufficientBalanceException if the amount is greater than the account balance.
     */
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (balance < amount) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }

        balance -= amount;
    }
}
