package com.n26.service;

import com.n26.domain.StatisticsUnit;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class StatisticsUtils {
    public StatisticsUnit getUpdatedStatistics(StatisticsUnit currentStatistics, BigDecimal amount) {
        return StatisticsUnit.builder()
                .sum(currentStatistics.getSum().add(amount))
                .max(getMax(amount, currentStatistics.getMax()))
                .min(getMin(amount, currentStatistics.getMin()))
                .count(currentStatistics.getCount() + 1)
                .build();
    }

    public StatisticsUnit getFirstStatistics(BigDecimal amount) {
        return StatisticsUnit.builder()
                .sum(amount)
                .max(amount)
                .min(amount)
                .count(1)
                .build();
    }

    public StatisticsUnit mergeStatistics(StatisticsUnit s1, StatisticsUnit s2) {
        return StatisticsUnit.builder()
                .sum(s1.getSum().add(s2.getSum()))
                .max(getMax(s1.getMax(), s2.getMax()))
                .min(getMin(s1.getMin(), s2.getMin()))
                .count(s1.getCount() + s2.getCount())
                .build();
    }

    private BigDecimal getMax(BigDecimal d1, BigDecimal d2) {
        return d1.compareTo(d2) > 0 ? d1 : d2;
    }

    private BigDecimal getMin(BigDecimal d1, BigDecimal d2) {
        if (d1.equals(BigDecimal.ZERO))
            return d2;
        if (d2.equals(BigDecimal.ZERO))
            return d1;
        return d1.compareTo(d2) < 0 ? d1 : d2;
    }

    public StatisticsUnit getEmptyStatistics() {
        return StatisticsUnit.builder()
                .sum(BigDecimal.ZERO)
                .max(BigDecimal.ZERO)
                .min(BigDecimal.ZERO)
                .count(0)
                .build();
    }
}
