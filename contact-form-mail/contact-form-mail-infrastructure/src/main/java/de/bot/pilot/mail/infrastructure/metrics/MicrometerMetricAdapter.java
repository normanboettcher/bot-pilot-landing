package de.bot.pilot.mail.infrastructure.metrics;

import de.bot.pilot.mail.domain.metrics.MetricName;
import de.bot.pilot.mail.domain.metrics.MetricTag;
import de.bot.pilot.mail.domain.port.outbound.MetricPort;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.Callable;
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
		this.meterRegistry.counter(metricName.getName(), toMicrometerTags(tags)).increment();
	}

	@Override
	public <T> void gauge(MetricName metricName, T stateObject, ToDoubleFunction<T> valueFunction, MetricTag... tags) {
		Gauge.builder(metricName.getName(), stateObject, valueFunction).tags(toMicrometerTags(tags))
				.register(this.meterRegistry);
	}

	@Override
	public <T> T time(MetricName metricName, Supplier<T> task, MetricTag... tags) {
		Callable<T> callable = task::get;
		try {
			return this.meterRegistry.timer(metricName.getName(), toMicrometerTags(tags)).recordCallable(callable);
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Tags toMicrometerTags(MetricTag... tags) {
		return Tags.of(Arrays.stream(tags).map(tag -> Tag.of(tag.key(), tag.value())).toArray(Tag[]::new));
	}

}
