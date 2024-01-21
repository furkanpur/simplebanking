package com.eteration.simplebanking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TransactionDTO {

    /**
     * The amount of the given transaction
     */
    private Double amount;

    /**
     * The approval code of the transaction.
     */
    private String approvalCode;

    /**
     * The date and time of the transaction.
     */
    private LocalDateTime date;

    /**
     * The type of the transaction.
     */
    private String type;
}
