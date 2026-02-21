package de.bot.pilot.crypto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BotPilotCryptoApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotPilotCryptoApplication.class, args);
    }

}
