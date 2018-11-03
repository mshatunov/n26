package com.n26.service;

import com.n26.domain.Transaction;
import com.n26.domain.TransactionAgeType;

import java.time.LocalDateTime;

public interface TransactionService {
    void postTransaction(Transaction transaction);
    void clearTransactions();
    TransactionAgeType checkTransactionAge(LocalDateTime transactionTimestamp);
}
