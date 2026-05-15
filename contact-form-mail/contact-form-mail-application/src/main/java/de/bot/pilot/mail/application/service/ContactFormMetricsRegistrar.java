package de.bot.pilot.mail.application.service;

import de.bot.pilot.mail.domain.metrics.MetricName;
import de.bot.pilot.mail.domain.port.outbound.ContactFormRecordPort;
import de.bot.pilot.mail.domain.port.outbound.MetricPort;

public class ContactFormMetricsRegistrar {

	public ContactFormMetricsRegistrar(ContactFormRecordPort recordPort, MetricPort metricPort) {
		metricPort.gauge(MetricName.EMAIL_RECORDS_TOTAL, recordPort, port -> (double) port.countEmailRecords());
	}
}
