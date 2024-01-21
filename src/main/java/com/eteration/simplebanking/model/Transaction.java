package com.eteration.simplebanking.model;


import com.eteration.simplebanking.exception.InsufficientBalanceException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

// This class is a place holder you can change the complete implementation
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The amount of transaction.
     */
    private double amount;

    /**
     * The local date time when the transaction is created.
     */
    private LocalDateTime date;

    /**
     * The approval code of the transaction.
     */
    private String type;

    /**
     * The approval code of the transaction.
     */
    private String approvalCode;

    /**
     * The account of the transaction.
     */
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Account account;


    public Transaction(double amount) {
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.type = this.getClass().getSimpleName();
        this.approvalCode = UUID.randomUUID().toString();
    }

    public abstract void process(Account account) throws InsufficientBalanceException;
}
