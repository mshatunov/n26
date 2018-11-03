package com.n26.controller;

import com.n26.controller.dto.PostTransactionRequest;
import com.n26.domain.Transaction;
import com.n26.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.n26.controller.TransactionController.TRANSACTIONS_URI;

@RestController
@RequestMapping(TRANSACTIONS_URI)
@RequiredArgsConstructor
public class TransactionController {
    public static final String TRANSACTIONS_URI = "/transactions";

    private final TransactionService service;

    @PostMapping
    public void postTransaction(@RequestBody PostTransactionRequest request) {
        service.postTransaction(Transaction.builder()
                .amount(request.getAmount())
                .timestamp(request.getTimestamp())
                .build());
    }
}
