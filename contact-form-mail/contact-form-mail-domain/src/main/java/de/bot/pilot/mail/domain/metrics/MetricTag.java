package de.bot.pilot.mail.domain.metrics;

import java.util.Objects;

public record MetricTag(String key, String value) {
	public MetricTag {
		Objects.requireNonNull(key, "key");
		Objects.requireNonNull(value, "value");
	}
}
