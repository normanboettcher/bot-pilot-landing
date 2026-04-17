package de.bot.pilot.mail.domain.model;

/**
 * Outbound mail payload. Routing (to/from addresses) is an infrastructure
 * concern resolved by the {@code MailPort} adapter from SMTP configuration.
 */
public record MailMessage(String subject, String body) {
}
