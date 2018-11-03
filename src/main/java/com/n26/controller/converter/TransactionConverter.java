package com.n26.controller.converter;

import com.n26.controller.dto.PostTransactionRequest;
import com.n26.domain.Transaction;
import lombok.experimental.UtilityClass;
import org.springframework.web.bind.annotation.RequestBody;

@UtilityClass
public class TransactionConverter {
    public static Transaction convertPostTransactionRequestToTransaction(@RequestBody PostTransactionRequest request) {
        return Transaction.builder()
                .amount(request.getAmount())
                .timestamp(request.getTimestamp())
                .build();
    }
}
