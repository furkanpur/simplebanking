package com.eteration.simplebanking.model;


import com.eteration.simplebanking.exception.InsufficientBalanceException;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

// This class is a place holder you can change the complete implementation

@Entity
@NoArgsConstructor
@SuperBuilder
public class DepositTransaction extends Transaction {

    public DepositTransaction(double amount) {
        super(amount);
    }

    @Override
    public void process(Account account) throws InsufficientBalanceException {
        account.deposit(this.getAmount());
        account.getTransactions().add(this);
    }
}
