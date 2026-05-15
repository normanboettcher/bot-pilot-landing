package de.bot.pilot.mail.application.service;

import de.bot.pilot.mail.domain.metrics.MetricName;
import de.bot.pilot.mail.domain.port.outbound.ContactFormRecordPort;
import de.bot.pilot.mail.domain.port.outbound.MetricPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.ToDoubleFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContactFormMetricsRegistrarTest {

	@Mock
	private ContactFormRecordPort recordPort;
	@Mock
	private MetricPort metricPort;
	private ContactFormMetricsRegistrar underTest;

	@BeforeEach
	void setUp() {
		underTest = new ContactFormMetricsRegistrar(recordPort, metricPort);
	}

	@Test
	@DisplayName("should register EMAIL_RECORDS_TOTAL gauge at construction time")
	void constructor_registersEmailRecordsTotalGauge() {
		verify(metricPort).gauge(eq(MetricName.EMAIL_RECORDS_TOTAL), eq(recordPort), any(ToDoubleFunction.class));
	}

	@Test
	@DisplayName("gauge value function delegates to recordPort.countEmailRecords()")
	void gaugeFunction_delegatesToCountEmailRecords() {
		// given
		given(recordPort.countEmailRecords()).willReturn(42L);

		// capture the ToDoubleFunction registered with the gauge
		@SuppressWarnings("unchecked")
		ArgumentCaptor<ToDoubleFunction<ContactFormRecordPort>> fnCaptor = ArgumentCaptor
				.forClass(ToDoubleFunction.class);
		verify(metricPort).gauge(eq(MetricName.EMAIL_RECORDS_TOTAL), eq(recordPort), fnCaptor.capture());

		// when
		double value = fnCaptor.getValue().applyAsDouble(recordPort);

		// then
		assertThat(value).isEqualTo(42.0);
	}
}
