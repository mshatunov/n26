package com.n26.service;

import com.n26.domain.Transaction;
import com.n26.storage.TransactionStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionStorage storage;

    @Override
    public void postTransaction(Transaction transaction) {
        storage.saveTransaction(transaction);
    }
}
