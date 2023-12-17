package org.example.CoffeeBewerter.Config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Data
@PropertySource("application.properties")
public class ConfigBot {
    @Value("${bot.name}")
    String botName;

    @Value("${bot.token}")
    String token;

}
