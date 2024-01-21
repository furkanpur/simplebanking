package com.eteration.simplebanking.model;

import com.eteration.simplebanking.exception.InsufficientBalanceException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PhoneBillPaymentTransaction extends BillPaymentTransaction {

    private String companyName;
    private String phoneNumber;

    public PhoneBillPaymentTransaction(String companyName, String phoneNumber, double amount) {
        super(amount);
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void process(Account account) throws InsufficientBalanceException {
        super.process(account);

        System.out.println("Phone bill payment transaction is processed.");
    }
}
