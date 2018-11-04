package com.n26.controller;

import com.n26.controller.dto.PostTransactionRequest;
import com.n26.domain.TransactionAgeType;
import com.n26.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static com.n26.controller.TransactionController.TRANSACTIONS_URI;
import static com.n26.controller.converter.DTOConverter.convertPostTransactionRequestToTransaction;

@Slf4j
@RestController
@RequestMapping(TRANSACTIONS_URI)
@RequiredArgsConstructor
public class TransactionController {
    public static final String TRANSACTIONS_URI = "/transactions";

    private static final ResponseEntity UNPROCESSABLE_ENTITY = new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
    private static final ResponseEntity CREATED = new ResponseEntity(HttpStatus.CREATED);
    private static final ResponseEntity NO_CONTENT = new ResponseEntity(HttpStatus.NO_CONTENT);

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity postTransaction(@RequestBody PostTransactionRequest request) {
        return convertPostTransactionRequestToTransaction(request)
                .map(t -> {
                    transactionService.postTransaction(t);
                    return getResponseStatusForTransactionTime(t.getTimestamp());
                })
                .orElse(UNPROCESSABLE_ENTITY);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearTransactions() {
        transactionService.clearTransactions();
    }

    private ResponseEntity getResponseStatusForTransactionTime(LocalDateTime transactionTimestamp) {
        TransactionAgeType transactionAgeType =
                transactionService.checkTransactionAge(transactionTimestamp);
        switch (transactionAgeType) {
            case CURRENT:
                return CREATED;
            case FUTURE:
                return UNPROCESSABLE_ENTITY;
            case OLD:
                return NO_CONTENT;
            default:
                log.error("Can't process transaction for timestamp", transactionTimestamp);
                throw new UnsupportedOperationException("Can't process transaction for timestamp " + transactionTimestamp);
        }
    }
}
