package de.bot.pilot.mail.config;

import de.bot.pilot.mail.application.port.inbound.ContactFormUseCase;
import de.bot.pilot.mail.application.service.ContactFormMetricsRegistrar;
import de.bot.pilot.mail.application.service.ContactFormService;
import de.bot.pilot.mail.application.service.InstrumentedContactFormService;
import de.bot.pilot.mail.domain.port.outbound.CaptchaPort;
import de.bot.pilot.mail.domain.port.outbound.ContactFormRecordPort;
import de.bot.pilot.mail.domain.port.outbound.EncryptionPort;
import de.bot.pilot.mail.domain.port.outbound.MailPort;
import de.bot.pilot.mail.domain.port.outbound.MetricPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfig {

	@Bean
	public ContactFormUseCase contactFormUseCase(CaptchaPort captchaPort, MailPort mailPort,
			ContactFormRecordPort contactFormRecordPort, EncryptionPort encryptionPort, MetricPort metricPort) {
		ContactFormUseCase contactFormService = new ContactFormService(captchaPort, mailPort, contactFormRecordPort,
				encryptionPort);
		return new InstrumentedContactFormService(contactFormService, metricPort);
	}

	@Bean
	public ContactFormMetricsRegistrar contactFormMetricsRegistrar(ContactFormRecordPort recordPort,
			MetricPort metricPort) {
		return new ContactFormMetricsRegistrar(recordPort, metricPort);
	}
}
