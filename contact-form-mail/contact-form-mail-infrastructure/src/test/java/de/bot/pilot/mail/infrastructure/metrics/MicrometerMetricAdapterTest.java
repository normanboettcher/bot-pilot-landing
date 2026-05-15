package de.bot.pilot.mail.infrastructure.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import de.bot.pilot.mail.domain.metrics.MetricName;
import de.bot.pilot.mail.domain.metrics.MetricTag;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

class MicrometerMetricAdapterTest {

	private SimpleMeterRegistry registry;
	private MicrometerMetricAdapter underTest;

	@BeforeEach
	void setUp() {
		registry = new SimpleMeterRegistry();
		underTest = new MicrometerMetricAdapter(registry);
	}

	// -------------------------------------------------------------------------
	// count
	// -------------------------------------------------------------------------

	@Test
	@DisplayName("Happy path: count increments the counter by 1")
	void count_noTags_incrementsCounterByOne() {
		// when
		underTest.count(MetricName.CONTACT_FORM_SUBMITTED);

		// then
		double count = registry.counter(MetricName.CONTACT_FORM_SUBMITTED.getName()).count();
		assertThat(count).isEqualTo(1.0);
	}

	@Test
	@DisplayName("count with tags: counter is registered under the exact name+tag combination")
	void count_withTags_counterRegisteredWithTags() {
		// when
		underTest.count(MetricName.CONTACT_FORM_SUBMITTED, new MetricTag("status", "success"));

		// then
		double count = registry.counter(MetricName.CONTACT_FORM_SUBMITTED.getName(), "status", "success").count();
		assertThat(count).isEqualTo(1.0);
	}

	@Test
	@DisplayName("count called twice: counter reaches 2")
	void count_calledTwice_counterIsTwo() {
		// when
		underTest.count(MetricName.CONTACT_FORM_SUBMITTED);
		underTest.count(MetricName.CONTACT_FORM_SUBMITTED);

		// then
		double count = registry.counter(MetricName.CONTACT_FORM_SUBMITTED.getName()).count();
		assertThat(count).isEqualTo(2.0);
	}

	// -------------------------------------------------------------------------
	// gauge
	// -------------------------------------------------------------------------

	@Test
	@DisplayName("Happy path: gauge reflects current value of the state object")
	void gauge_stateObject_reportsFunctionValue() {
		// given
		AtomicInteger queueSize = new AtomicInteger(42);

		// when
		underTest.gauge(MetricName.CONTACT_FORM_SUBMITTED, queueSize, AtomicInteger::get);

		// then
		Gauge gauge = registry.find(MetricName.CONTACT_FORM_SUBMITTED.getName()).gauge();
		assertThat(gauge).isNotNull();
		assertThat(gauge.value()).isEqualTo(42.0);
	}

	@Test
	@DisplayName("gauge tracks live state: value updates when state object changes")
	void gauge_stateObjectMutated_valueUpdates() {
		// given
		AtomicInteger queueSize = new AtomicInteger(10);
		underTest.gauge(MetricName.CONTACT_FORM_SUBMITTED, queueSize, AtomicInteger::get);

		// when
		queueSize.set(99);

		// then
		Gauge gauge = registry.find(MetricName.CONTACT_FORM_SUBMITTED.getName()).gauge();
		assertThat(gauge).isNotNull();
		assertThat(gauge.value()).isEqualTo(99.0);
	}

	@Test
	@DisplayName("gauge with tags: registered under the correct name+tag combination")
	void gauge_withTags_registeredWithTags() {
		// given
		AtomicInteger value = new AtomicInteger(7);

		// when
		underTest.gauge(MetricName.CONTACT_FORM_SUBMITTED, value, AtomicInteger::get, new MetricTag("pool", "primary"));

		// then
		Gauge gauge = registry.find(MetricName.CONTACT_FORM_SUBMITTED.getName()).tag("pool", "primary").gauge();
		assertThat(gauge).isNotNull();
		assertThat(gauge.value()).isEqualTo(7.0);
	}

	// -------------------------------------------------------------------------
	// time
	// -------------------------------------------------------------------------

	@Test
	@DisplayName("Happy path: time returns the value produced by the task")
	void time_successfulTask_returnsTaskResult() {
		// when
		String result = underTest.time(MetricName.MAIL_SENT, () -> "hello");

		// then
		assertThat(result).isEqualTo("hello");
	}

	@Test
	@DisplayName("Happy path: time records a single observation in the timer")
	void time_successfulTask_timerRecordsOneObservation() {
		// when
		underTest.time(MetricName.MAIL_SENT, () -> null);

		// then
		Timer timer = registry.find(MetricName.MAIL_SENT.getName()).timer();
		assertThat(timer).isNotNull();
		assertThat(timer.count()).isEqualTo(1L);
	}

	@Test
	@DisplayName("time with tags: timer is registered under the correct name+tag combination")
	void time_withTags_timerRegisteredWithTags() {
		// when
		underTest.time(MetricName.MAIL_SENT, () -> null, new MetricTag("outcome", "success"));

		// then
		Timer timer = registry.find(MetricName.MAIL_SENT.getName()).tag("outcome", "success").timer();
		assertThat(timer).isNotNull();
		assertThat(timer.count()).isEqualTo(1L);
	}

	@Test
	@DisplayName("time: RuntimeException from task propagates unwrapped")
	void time_taskThrowsRuntimeException_propagatesUnwrapped() {
		// given
		IllegalStateException cause = new IllegalStateException("boom");

		// when / then
		assertThatThrownBy(() -> underTest.time(MetricName.MAIL_SENT, () -> {
			throw cause;
		})).isSameAs(cause);
	}

	@Test
	@DisplayName("time: timer still records observation even when task throws")
	void time_taskThrows_timerStillRecords() {
		// when (swallow the exception to continue)
		try {
			underTest.time(MetricName.MAIL_SENT, () -> {
				throw new RuntimeException("boom");
			});
		} catch (RuntimeException ignored) {
		}

		// then
		Timer timer = registry.find(MetricName.MAIL_SENT.getName()).timer();
		assertThat(timer).isNotNull();
		assertThat(timer.count()).isEqualTo(1L);
	}

	@Test
	@DisplayName("time called multiple times: timer accumulates observations")
	void time_calledMultipleTimes_timerAccumulatesCount() {
		// when
		underTest.time(MetricName.MAIL_SENT, () -> null);
		underTest.time(MetricName.MAIL_SENT, () -> null);
		underTest.time(MetricName.MAIL_SENT, () -> null);

		// then
		Timer timer = registry.find(MetricName.MAIL_SENT.getName()).timer();
		assertThat(timer).isNotNull();
		assertThat(timer.count()).isEqualTo(3L);
	}

}
