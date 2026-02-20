package de.bot.pilot.mail.contact.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "transit")
public class VaultTransitProperties {
    @NotNull
    @Valid
    private VaultTransitMailProperties mail;
    @NotNull
    @Valid
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
        this.message = message;
    }
}
