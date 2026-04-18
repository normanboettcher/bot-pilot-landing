package de.bot.pilot.mail.infrastructure.captcha.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Bound to {@code captcha.secret.path} from application configuration.
 * Registered via {@code @ConfigurationPropertiesScan} on the main application
 * class.
 */
@ConfigurationProperties(prefix = "captcha.secret")
public record CaptchaProperties(String path) {
}
