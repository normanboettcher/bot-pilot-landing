package de.bot.pilot.mail.infrastructure.metrics;

import de.bot.pilot.mail.domain.metrics.MetricName;
import de.bot.pilot.mail.domain.metrics.MetricTag;
import de.bot.pilot.mail.domain.port.outbound.MetricPort;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

@Component
public class MicrometerMetricAdapter implements MetricPort {

	private final MeterRegistry meterRegistry;

	public MicrometerMetricAdapter(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
	}

	@Override
	public void count(MetricName metricName, MetricTag... tags) {
		// TODO: Implement metrics with concrete prometheus classes
	}

	@Override
	public <T> void gauge(MetricName metricName, T stateObject, ToDoubleFunction<T> valueFunction, MetricTag... tags) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public <T> T time(MetricName metricName, Supplier<T> task, MetricTag... tags) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
