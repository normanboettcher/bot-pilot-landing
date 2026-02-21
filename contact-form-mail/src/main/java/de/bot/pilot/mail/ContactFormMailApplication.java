package de.bot.pilot.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ContactFormMailApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContactFormMailApplication.class, args);
    }

}
