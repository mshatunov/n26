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
                .sum(statisticsUnit.getSum()
                        .setScale(2, BigDecimal.ROUND_HALF_UP))
                .avg(statisticsUnit.getCount() == 0 ? BigDecimal.ZERO : statisticsUnit.getSum()
                        .divide(BigDecimal.valueOf(statisticsUnit.getCount()), 2, BigDecimal.ROUND_HALF_UP))
                .max(statisticsUnit.getMax()
                        .setScale(2, BigDecimal.ROUND_HALF_UP))
                .min(statisticsUnit.getMin()
                        .setScale(2, BigDecimal.ROUND_HALF_UP))
                .count(statisticsUnit.getCount())
                .build();
    }
}
