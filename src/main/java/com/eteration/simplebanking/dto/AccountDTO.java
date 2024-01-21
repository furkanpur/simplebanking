package com.eteration.simplebanking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AccountDTO {

    /**
     * The account number of the account.
     */
    private String accountNumber;

    /**
     * The owner of the account.
     */
    private String owner;

    /**
     * The balance of the account.
     */
    private Double balance;

    /**
     * The date and time of the account creation.
     */
    private LocalDateTime createDate;

    /**
     * The list of transactions of the account.
     */
    private List<TransactionDTO> transactions;
}
