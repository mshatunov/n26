package com.n26.service;

import com.n26.domain.Transaction;
import com.n26.domain.TransactionAgeType;
import com.n26.storage.TransactionStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.n26.domain.TransactionAgeType.CURRENT;
import static com.n26.domain.TransactionAgeType.FUTURE;
import static com.n26.domain.TransactionAgeType.OLD;

@Service
@RequiredArgsConstructor
public class BasicTransactionService implements TransactionService {

    private final TransactionStorage storage;

    @Override
    public void postTransaction(Transaction transaction) {
        storage.saveTransaction(transaction);
    }

    @Override
    public void clearTransactions() {
        storage.clearTransactions();
    }

    @Override
    public TransactionAgeType checkTransactionAge(LocalDateTime transactionTimestamp) {
        LocalDateTime now = LocalDateTime.now();

        if (transactionTimestamp.isAfter(now)) {
            return FUTURE;
        } else if (transactionTimestamp.isBefore(now.minusMinutes(1))) {
            return OLD;
        } else {
            return CURRENT;
        }
    }
}
