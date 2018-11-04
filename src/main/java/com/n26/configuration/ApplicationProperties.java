package com.n26.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    @NotEmpty
    private String timezone;
}
