package com.example.justlifetest.configuration;

import com.example.justlifetest.helper.availability.AvailabilityStrategy;
import com.example.justlifetest.helper.availability.DefaultAvailabilityStrategy;
import com.example.justlifetest.repository.BookingRepository;
import com.example.justlifetest.repository.CleanerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrategyConfiguration {

    @Bean
    public AvailabilityStrategy availabilityStrategy(BookingRepository bookingRepository,
                                                     CleanerRepository cleanerRepository) {
        return new DefaultAvailabilityStrategy(bookingRepository, cleanerRepository);
    }
}