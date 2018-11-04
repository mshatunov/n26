package com.n26.controller.converter;

import com.n26.controller.dto.GetStatisticsResponse;
import com.n26.controller.dto.PostTransactionRequest;
import com.n26.domain.StatisticsUnit;
import com.n26.domain.Transaction;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class DTOConverter {
    public static Transaction convertPostTransactionRequestToTransaction(PostTransactionRequest request) {
        return Transaction.builder()
                .amount(request.getAmount())
                .timestamp(request.getTimestamp())
                .build();
    }

    public static GetStatisticsResponse convertStatisticsToGetStatisticsResponse(StatisticsUnit statisticsUnit) {
        return GetStatisticsResponse.builder()
                .sum(statisticsUnit.getSum())
                .avg(statisticsUnit.getCount() == 0 ? BigDecimal.ZERO : statisticsUnit.getSum()
                        .divide(BigDecimal.valueOf(statisticsUnit.getCount()), 2, 2))
                .max(statisticsUnit.getMax())
                .min(statisticsUnit.getMin())
                .count(statisticsUnit.getCount())
                .build();
    }
}
