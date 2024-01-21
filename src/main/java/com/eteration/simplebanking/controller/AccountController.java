package com.eteration.simplebanking.controller;

import com.eteration.simplebanking.dto.AccountDTO;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.payload.request.CreditRequest;
import com.eteration.simplebanking.payload.request.DebitRequest;
import com.eteration.simplebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// This class is a place holder you can change the complete implementation
@RestController
@RequestMapping("/account/v1")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountNumber}")
    @ResponseBody
    public ResponseEntity<AccountDTO> getAccount(@PathVariable String accountNumber) {
        AccountDTO accountDTO = this.accountService.findAccount(accountNumber);

        return ResponseEntity.ok().body(accountDTO);
    }

    @PostMapping("/credit/{accountNumber}")
    @ResponseBody
    public ResponseEntity<TransactionStatus> credit(@PathVariable String accountNumber, @RequestBody CreditRequest request) {
        DepositTransaction transaction = new DepositTransaction(request.getAmount());

        TransactionStatus status = this.accountService.credit(accountNumber, transaction);

        return ResponseEntity.ok().body(status);
    }

    @PostMapping("/debit/{accountNumber}")
    @ResponseBody
    public ResponseEntity<TransactionStatus> debit(@PathVariable String accountNumber, @RequestBody DebitRequest request) {
        WithdrawalTransaction transaction = new WithdrawalTransaction(request.getAmount());

        TransactionStatus status = this.accountService.debit(accountNumber, transaction);

        return ResponseEntity.ok().body(status);
    }
}