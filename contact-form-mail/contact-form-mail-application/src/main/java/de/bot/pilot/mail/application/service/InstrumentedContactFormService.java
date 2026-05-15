package de.bot.pilot.mail.application.service;

import de.bot.pilot.mail.application.port.inbound.ContactFormUseCase;
import de.bot.pilot.mail.domain.metrics.MetricName;
import de.bot.pilot.mail.domain.metrics.MetricTag;
import de.bot.pilot.mail.domain.model.ContactSubmission;

import de.bot.pilot.mail.domain.port.outbound.MetricPort;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class InstrumentedContactFormService implements ContactFormUseCase {

	private final ContactFormUseCase delegate;
	private final MetricPort metricPort;
	private final AtomicInteger activeSubmissions = new AtomicInteger(0);

	public InstrumentedContactFormService(ContactFormUseCase delegate, MetricPort metricPort) {
		this.delegate = delegate;
		this.metricPort = metricPort;
		metricPort.gauge(MetricName.ACTIVE_SUBMISSIONS, activeSubmissions, AtomicInteger::get);
	}

	@Override
	public void submit(ContactSubmission submission) {
		activeSubmissions.incrementAndGet();
		try {
			final Supplier<Void> task = () -> {
				delegate.submit(submission);
				return null;
			};
			metricPort.time(MetricName.MAIL_SENT, task);
			metricPort.count(MetricName.CONTACT_FORM_SUBMITTED, new MetricTag("status", "success"));
		} catch (Exception e) {
			metricPort.count(MetricName.CONTACT_FORM_SUBMITTED, new MetricTag("status", "failure"));
			throw e;
		} finally {
			activeSubmissions.decrementAndGet();
		}
	}
}
