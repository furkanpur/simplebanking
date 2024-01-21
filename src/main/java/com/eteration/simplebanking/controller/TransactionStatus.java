package com.eteration.simplebanking.controller;


// This class is a place holder you can change the complete implementation

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TransactionStatus {
    private String status;

    private String approvalCode;

}
