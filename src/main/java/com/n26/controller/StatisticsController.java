package com.n26.controller;

import com.n26.controller.converter.DTOConverter;
import com.n26.controller.dto.GetStatisticsResponse;
import com.n26.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.n26.controller.StatisticsController.STATISTICS_URI;

@RestController
@RequestMapping(STATISTICS_URI)
@RequiredArgsConstructor
public class StatisticsController {
    public static final String STATISTICS_URI = "/statistics";

    private final StatisticsService statisticsService;

    @GetMapping
    public GetStatisticsResponse getStatistics() {
        return DTOConverter.convertStatisticsToGetStatisticsResponse(
                statisticsService.getLastMinuteStatistics());
    }
}
