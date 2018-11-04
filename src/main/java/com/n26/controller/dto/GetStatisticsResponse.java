package com.n26.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetStatisticsResponse {
    private String sum;
    private String avg;
    private String max;
    private String min;
    private int count;
}
