package de.bot.pilot.mail.domain.port.outbound;

import de.bot.pilot.mail.domain.metrics.MetricName;
import de.bot.pilot.mail.domain.metrics.MetricTag;

import java.time.Duration;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

public interface MetricPort {
	void count(MetricName metricName, MetricTag... tags);

	<T> void gauge(MetricName metricName, T stateObject, ToDoubleFunction<T> valueFunction, MetricTag... tags);

	<T> T time(MetricName metricName, Supplier<T> task, MetricTag... tags);
}
