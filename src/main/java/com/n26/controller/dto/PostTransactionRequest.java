package com.n26.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostTransactionRequest {
    public static final String TIMESTAMP_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @NotNull
    private BigDecimal amount;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TIMESTAMP_PATTERN, timezone = "UTC")
    private LocalDateTime timestamp;
}
