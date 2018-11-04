package com.n26.controller.converter;

import com.n26.controller.dto.GetStatisticsResponse;
import com.n26.controller.dto.PostTransactionRequest;
import com.n26.domain.StatisticsUnit;
import com.n26.domain.Transaction;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@UtilityClass
public class DTOConverter {
    public static Optional<Transaction> convertPostTransactionRequestToTransaction(PostTransactionRequest request) {
        try {
            return Optional.of(Transaction.builder()
                    .amount(new BigDecimal(request.getAmount()))
                    .timestamp(LocalDateTime.parse(request.getTimestamp(), DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                    .build());
        } catch (Exception e) {
            log.error("Error while parsing ", request);
            return Optional.empty();
        }
    }

    public static GetStatisticsResponse convertStatisticsToGetStatisticsResponse(StatisticsUnit statisticsUnit) {
        return GetStatisticsResponse.builder()
                .sum(statisticsUnit.getSum()
                        .setScale(2, BigDecimal.ROUND_HALF_UP)
                        .toString())
                .avg(statisticsUnit.getCount() == 0 ? "0.00" : statisticsUnit.getSum()
                        .divide(BigDecimal.valueOf(statisticsUnit.getCount()), 2, BigDecimal.ROUND_HALF_UP)
                        .toString())
                .max(statisticsUnit.getMax()
                        .setScale(2, BigDecimal.ROUND_HALF_UP)
                        .toString())
                .min(statisticsUnit.getMin()
                        .setScale(2, BigDecimal.ROUND_HALF_UP)
                        .toString())
                .count(statisticsUnit.getCount())
                .build();
    }
}
