package com.n26;

import com.n26.configuration.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
public class TransactionsApplication {

    public static void main(String... args) {
        SpringApplication.run(TransactionsApplication.class, args);
    }

}
