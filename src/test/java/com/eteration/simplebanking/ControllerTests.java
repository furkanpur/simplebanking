package com.eteration.simplebanking;

import com.eteration.simplebanking.controller.TransactionStatus;
import com.eteration.simplebanking.dto.AccountDTO;
import com.eteration.simplebanking.dto.TransactionDTO;
import com.eteration.simplebanking.model.Account;
import com.eteration.simplebanking.model.DepositTransaction;
import com.eteration.simplebanking.model.Transaction;
import com.eteration.simplebanking.model.WithdrawalTransaction;
import com.eteration.simplebanking.payload.request.CreditRequest;
import com.eteration.simplebanking.payload.request.DebitRequest;
import com.eteration.simplebanking.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ContextConfiguration
@AutoConfigureMockMvc
class ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void givenAccountNumber_GetAccount_thenReturnJson()
            throws Exception {
        String accountNumber = "12345";

        Transaction transaction = DepositTransaction.builder()
                .amount(1000.0)
                .build();

        Transaction transaction2 = WithdrawalTransaction.builder()
                .amount(250.0)
                .build();

        Account account = Account.builder()
                .owner("Kerem Karaca")
                .accountNumber(accountNumber)
                .balance(750)
                .transactions(new ArrayList<>())
                .build();

        List<TransactionDTO> transactionDTOList = modelMapper.map(List.of(transaction, transaction2), List.class);

        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);
        accountDTO.setTransactions(transactionDTOList);

        when(service.findAccount(isA(String.class))).thenReturn(accountDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/account/v1/{accountNumber}", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.owner").value("Kerem Karaca"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value(accountNumber))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(750.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.transactions.length()").value(2));
    }

    @Test
    public void givenAccountNumber_Credit_thenReturnJson()
            throws Exception {
        String accountNumber = "12345";

        TransactionStatus transactionStatus = TransactionStatus.builder()
                .status("OK")
                .approvalCode("11-22-33-44")
                .build();

        CreditRequest creditRequest = CreditRequest.builder()
                .amount(1000.0)
                .build();

        when(service.credit(isA(String.class), isA(Transaction.class))).thenReturn(transactionStatus);

        mockMvc.perform(MockMvcRequestBuilders.post("/account/v1/credit/{accountNumber}", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(creditRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvalCode").value("11-22-33-44"));
    }

    @Test
    public void givenAccountNumber_Debit_thenReturnJson()
            throws Exception {
        String accountNumber = "12345";

        TransactionStatus transactionStatus = TransactionStatus.builder()
                .status("OK")
                .approvalCode("11-22-33-44")
                .build();

        DebitRequest debitRequest = DebitRequest.builder()
                .amount(250.0)
                .build();

        when(service.debit(isA(String.class), isA(Transaction.class))).thenReturn(transactionStatus);

        mockMvc.perform(MockMvcRequestBuilders.post("/account/v1/debit/{accountNumber}", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(debitRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("OK"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.approvalCode").value("11-22-33-44"));
    }

}
