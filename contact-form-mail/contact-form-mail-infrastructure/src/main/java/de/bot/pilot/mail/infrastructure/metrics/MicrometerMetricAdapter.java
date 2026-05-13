package de.bot.pilot.mail.infrastructure.metrics;

import de.bot.pilot.mail.domain.metrics.MetricName;
import de.bot.pilot.mail.domain.metrics.MetricTag;
import de.bot.pilot.mail.domain.port.outbound.MetricPort;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Stream;

@Component
public class MicrometerMetricAdapter implements MetricPort {

	private final MeterRegistry meterRegistry;

	public MicrometerMetricAdapter(MeterRegistry meterRegistry) {
		this.meterRegistry = meterRegistry;
	}

	@Override
	public void count(MetricName metricName, MetricTag... tags) {
		Tags tagPairs = Tags.of(Arrays.stream(tags).map(tag -> Tag.of(tag.key(), tag.value())).toArray(Tag[]::new));
		this.meterRegistry.counter(metricName.getName(), tagPairs).increment();
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
