package de.bot.pilot.mail.infrastructure.metrics;

import de.bot.pilot.mail.domain.metrics.MetricName;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class SmtpMetricsRegistrar {

	public SmtpMetricsRegistrar(JavaMailSenderImpl mailSender, MeterRegistry meterRegistry) {
		Gauge.builder(MetricName.SMTP_UP.getName(), mailSender, sender -> {
			try {
				sender.testConnection();
				return 1.0;
			} catch (MessagingException e) {
				return 0.0;
			}
		}).register(meterRegistry);
	}
}
