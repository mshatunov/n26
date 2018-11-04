package com.n26.service;

import com.n26.domain.StatisticsUnit;

public interface StatisticsService {
    StatisticsUnit getLastMinuteStatistics();
}
