package de.bot.pilot.mail.domain.model;

import org.immutables.value.Value;

import java.time.Instant;

/**
 * Domain value object representing a persisted email attempt. Generated
 * {@code ImmutableEmailRecord} provides a type-safe builder.
 */
@Value.Immutable
public interface EmailRecord {
	String content();
	String subject();
	Instant createdAt();
	boolean success();
}
