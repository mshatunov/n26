package com.n26.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsUnit {
    private BigDecimal sum;
    private BigDecimal max;
    private BigDecimal min;
    private int count;
}
