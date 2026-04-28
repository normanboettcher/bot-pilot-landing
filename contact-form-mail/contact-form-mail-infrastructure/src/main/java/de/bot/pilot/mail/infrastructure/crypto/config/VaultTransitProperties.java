package de.bot.pilot.mail.infrastructure.crypto.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "vault.transit")
public record VaultTransitProperties(String key, String path) {
}
