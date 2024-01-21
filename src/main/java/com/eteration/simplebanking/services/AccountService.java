package com.eteration.simplebanking.services;


import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.dto.AccountDTO;
import com.eteration.simplebanking.dto.TransactionDTO;
import com.eteration.simplebanking.exception.NoAccountFoundException;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.repository.AccountRepository;
import com.eteration.simplebanking.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

// This class is a place holder you can change the complete implementation
@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final TransactionRepository transactionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AccountDTO findAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NoAccountFoundException("No account found with account number: " + accountNumber));

        List<TransactionDTO> transactionDTOS = account.getTransactions().stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDTO.class))
                .toList();

        AccountDTO accountDTO = AccountDTO.builder()
                .accountNumber(account.getAccountNumber())
                .owner(account.getOwner())
                .balance(account.getBalance())
                .createDate(account.getCreateDate())
                .transactions(transactionDTOS)
                .build();

        return accountDTO;
    }

    public TransactionStatus credit(String accountNumber, Transaction transaction) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NoAccountFoundException("No account found with account number: " + accountNumber));

        account.deposit(transaction.getAmount());
        accountRepository.save(account);

        transaction.setAccount(account);
        transactionRepository.save(transaction);

        return new TransactionStatus("OK", transaction.getApprovalCode());
    }

    public TransactionStatus debit(String accountNumber, Transaction transaction) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NoAccountFoundException("No account found with account number: " + accountNumber));

        account.withdraw(transaction.getAmount());
        accountRepository.save(account);

        transaction.setAccount(account);
        transactionRepository.save(transaction);

        return new TransactionStatus("OK", transaction.getApprovalCode());
    }
}
