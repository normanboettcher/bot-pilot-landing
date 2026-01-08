package de.bot.pilot.mail.contact.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Configuration class for setting up the mail sender.
 * This class provides the configuration for JavaMailSender.
 */
@Configuration
public class MailConfig {

    @Bean
    @Primary
    public JavaMailSender getJavaMailSender(JavaMailSenderImpl sender) throws IOException {
        String pwFilePath = System.getenv("SMTP_PASSWORD_FILE");
        if (pwFilePath != null) {
            sender.setPassword(
                    Files.readString(Path.of(pwFilePath)).trim()
            );
        }
        return sender;
    }
}
