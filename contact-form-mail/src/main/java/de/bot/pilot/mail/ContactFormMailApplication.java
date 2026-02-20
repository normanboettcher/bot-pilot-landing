package de.bot.pilot.mail;

import de.bot.pilot.mail.contact.config.VaultTransitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ContactFormMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactFormMailApplication.class, args);
    }

}
