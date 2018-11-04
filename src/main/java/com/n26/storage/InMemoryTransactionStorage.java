package com.n26.storage;

import com.n26.domain.StatisticsUnit;
import com.n26.domain.Transaction;
import com.n26.service.StatisticsUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class InMemoryTransactionStorage implements TransactionStorage {

    private final Map<LocalDateTime, StatisticsUnit> storage = new HashMap<>();

    @Override
    public void saveTransaction(Transaction transaction) {
        LocalDateTime transactionTime = transaction.getTimestamp().truncatedTo(ChronoUnit.SECONDS);
        StatisticsUnit updatedStatistics = Optional.ofNullable(storage.get(transactionTime))
                .map(su -> StatisticsUtils.getUpdatedStatistics(su, transaction.getAmount()))
                .orElse(StatisticsUtils.getFirstStatistics(transaction.getAmount()));
        storage.put(transactionTime, updatedStatistics);
    }

    @Override
    public void clearTransactions() {
        storage.clear();
    }

    @Override
    public StatisticsUnit getStatictics(LocalDateTime processingTime) {
        return storage.get(processingTime);
    }
}
