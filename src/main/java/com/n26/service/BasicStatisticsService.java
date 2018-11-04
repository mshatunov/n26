package com.n26.service;

import com.n26.domain.StatisticsUnit;
import com.n26.storage.TransactionStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BasicStatisticsService implements StatisticsService {

    private final TransactionStorage storage;

    @Override
    public StatisticsUnit getLastMinuteStatistics() {

        StatisticsUnit cumulative = StatisticsUtils.getEmptyStatistics();

        LocalDateTime now = LocalDateTime.now();
        for (LocalDateTime processingTime = now.truncatedTo(ChronoUnit.SECONDS);
             processingTime.isAfter(now.minusMinutes(1));
             processingTime = processingTime.minusSeconds(1)) {
            StatisticsUnit statictics = storage.getStatictics(processingTime);
            if (statictics != null)
                cumulative = StatisticsUtils.mergeStatistics(cumulative, statictics);
        }

        return cumulative;
    }
}

