package de.bot.pilot.mail.infrastructure.crypto.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "vault.transit")
@Validated
public record VaultTransitProperties(@NotBlank String key, @NotBlank String path) {
}
