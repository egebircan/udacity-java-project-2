package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

/**
 * Creates a Spring Boot Application to run the Pricing Service.
 */
@SpringBootApplication
public class PricingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(PriceRepository repository) {
        return args -> {
            repository.save(new Price("USD", BigDecimal.valueOf(50000)));
            repository.save(new Price("USD", BigDecimal.valueOf(20000)));
            repository.save(new Price("USD", BigDecimal.valueOf(30000)));
            repository.save(new Price("USD", BigDecimal.valueOf(40000)));
            repository.save(new Price("USD", BigDecimal.valueOf(90000)));
        };
    }

}
