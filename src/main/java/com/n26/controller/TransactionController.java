package com.n26.controller;

import com.n26.controller.dto.PostTransactionRequest;
import com.n26.domain.Transaction;
import com.n26.domain.TransactionAgeType;
import com.n26.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.n26.controller.TransactionController.TRANSACTIONS_URI;
import static com.n26.controller.converter.TransactionConverter.convertPostTransactionRequestToTransaction;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping(TRANSACTIONS_URI)
@RequiredArgsConstructor
public class TransactionController {
    public static final String TRANSACTIONS_URI = "/transactions";

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity postTransaction(@RequestBody PostTransactionRequest request) {
        Transaction transaction = convertPostTransactionRequestToTransaction(request);
        transactionService.postTransaction(transaction);
        return getResponseStatusForTransactionTime(transaction.getTimestamp());
    }

    @DeleteMapping
    @ResponseStatus(NO_CONTENT)
    public void clearTransactions() {
        transactionService.clearTransactions();
    }

    private ResponseEntity getResponseStatusForTransactionTime(LocalDateTime transactionTimestamp) {
        TransactionAgeType transactionAgeType =
                transactionService.checkTransactionAge(transactionTimestamp);
        switch (transactionAgeType) {
            case CURRENT:
                return new ResponseEntity(CREATED);
            case FUTURE:
                return new ResponseEntity(UNPROCESSABLE_ENTITY);
            case OLD:
                return new ResponseEntity(NO_CONTENT);
            default:
                return new ResponseEntity(OK);
        }
    }
}
