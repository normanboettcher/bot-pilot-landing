package de.bot.pilot.mail.application.service;

import de.bot.pilot.mail.application.port.inbound.ContactFormUseCase;
import de.bot.pilot.mail.domain.metrics.MetricName;
import de.bot.pilot.mail.domain.metrics.MetricTag;
import de.bot.pilot.mail.domain.model.ContactSubmission;

import de.bot.pilot.mail.domain.port.outbound.MetricPort;

public class InstrumentedContactFormService implements ContactFormUseCase {

	private final ContactFormUseCase delegate;
	private final MetricPort metricPort;

	public InstrumentedContactFormService(ContactFormUseCase delegate, MetricPort metricPort) {
		this.delegate = delegate;
		this.metricPort = metricPort;
	}

	@Override
	public void submit(ContactSubmission submission) {
		try {
			delegate.submit(submission);
			metricPort.count(MetricName.CONTACT_FORM_SUBMITTED, new MetricTag("status", "success"));
		} catch (Exception e) {
			metricPort.count(MetricName.CONTACT_FORM_SUBMITTED, new MetricTag("status", "failure"));
			throw e;
		}
	}
}
