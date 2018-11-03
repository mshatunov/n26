package com.n26.storage;

import com.n26.domain.Transaction;

public interface TransactionStorage {
    void saveTransaction(Transaction transaction);
}
