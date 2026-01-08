package de.bot.pilot.mail.contact.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class MailConfig {

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.port}")
    private int port;

    public JavaMailSender getJavaMailSender() throws IOException {
        final JavaMailSenderImpl sender = new JavaMailSenderImpl();
        String pwFilePath = System.getenv("SMTP_PASSWORD_FILE");
        sender.setHost(host);
        sender.setPort(port);
        sender.setUsername(username);
        log.debug("""
                Using SMTP username: [{}]
                with SMTP_PORT: [{}] and SMTP_HOST: [{}]
                """, sender.getUsername(), sender.getPort(), sender.getHost());
        if (pwFilePath != null) {
            sender.setPassword(
                    Files.readString(Path.of(pwFilePath)).trim()
            );
            log.debug("Using SMTP password from file: [{}].", sender.getPassword());
        } else {
            log.warn("No SMTP password file found. Using default password.");
        }
        return sender;
    }
}
