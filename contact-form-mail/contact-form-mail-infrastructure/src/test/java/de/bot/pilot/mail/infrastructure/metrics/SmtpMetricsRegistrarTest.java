package de.bot.pilot.mail.infrastructure.metrics;

import de.bot.pilot.mail.domain.metrics.MetricName;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class SmtpMetricsRegistrarTest {

	@Mock
	private JavaMailSenderImpl mailSender;
	private SimpleMeterRegistry registry;

	@BeforeEach
	void setUp() {
		registry = new SimpleMeterRegistry();
	}

	@Test
	@DisplayName("gauge value is 1.0 when testConnection() succeeds")
	void smtpUp_connectionSucceeds_gaugeIsOne() throws MessagingException {
		// given
		doNothing().when(mailSender).testConnection();
		new SmtpMetricsRegistrar(mailSender, registry);

		// when
		Gauge gauge = registry.find(MetricName.SMTP_UP.getName()).gauge();

		// then
		assertThat(gauge).isNotNull();
		assertThat(gauge.value()).isEqualTo(1.0);
	}

	@Test
	@DisplayName("gauge value is 0.0 when testConnection() throws MessagingException")
	void smtpUp_connectionFails_gaugeIsZero() throws MessagingException {
		// given
		doThrow(new MessagingException("connection refused")).when(mailSender).testConnection();
		new SmtpMetricsRegistrar(mailSender, registry);

		// when
		Gauge gauge = registry.find(MetricName.SMTP_UP.getName()).gauge();

		// then
		assertThat(gauge).isNotNull();
		assertThat(gauge.value()).isEqualTo(0.0);
	}
}
