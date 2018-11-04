package com.n26.storage;

import com.n26.domain.StatisticsUnit;
import com.n26.domain.Transaction;

import java.time.LocalDateTime;

public interface TransactionStorage {
    void saveTransaction(Transaction transaction);

    void clearTransactions();

    StatisticsUnit getStatictics(LocalDateTime processingTime);
}
