package de.bot.pilot.mail.infrastructure.mail.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Reads the SMTP password from the file referenced by SMTP_PASSWORD_FILE.
 * File-based secret injection avoids putting credentials in environment variables.
 */
@Component
public class MailSenderInitializer {

    @Autowired
    private JavaMailSender mailSender;

    @PostConstruct
    public void init() throws IOException {
        if (mailSender instanceof JavaMailSenderImpl impl) {
            String passwordFile = System.getenv("SMTP_PASSWORD_FILE");
            if (passwordFile != null) {
                impl.setPassword(Files.readString(Path.of(passwordFile)).trim());
            }
        }
    }
}
