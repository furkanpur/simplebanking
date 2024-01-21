package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
public class BillPaymentTransaction extends WithdrawalTransaction {

    public BillPaymentTransaction(double amount) {
        super(amount);
    }


    @Override
    public void process(Account account) throws InsufficientBalanceException {
        super.process(account);

        System.out.println("Bill payment transaction is processed.");
    }
}
