package de.bot.pilot.mail.contact.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "transit")
public class VaultTransitProperties {
    @NotBlank
    private VaultTransitMailProperties mail;
    @NotBlank
    private VaultTransitMessageProperties message;

    public VaultTransitMailProperties getMail() {
        return mail;
    }

    public void setMail(VaultTransitMailProperties mail) {
        this.mail = mail;
    }

    public VaultTransitMessageProperties getMessage() {
        return message;
    }

    public void setMessage(VaultTransitMessageProperties message) {
    }
}
