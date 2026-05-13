package de.bot.pilot.mail.application.service;

import de.bot.pilot.mail.application.port.inbound.ContactFormUseCase;
import de.bot.pilot.mail.domain.exception.MailDeliveryException;
import de.bot.pilot.mail.domain.metrics.MetricName;
import de.bot.pilot.mail.domain.metrics.MetricTag;
import de.bot.pilot.mail.domain.model.ContactSubmission;
import de.bot.pilot.mail.domain.port.outbound.MetricPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstrumentedContactFormServiceTest {

	private ContactFormUseCase underTest;
	@Mock
	private ContactFormUseCase delegate;
	@Mock
	private MetricPort metricPortMock;
	private final ContactSubmission validSubmission = new ContactSubmission("Ada", "Lovelace", "ada.lovelace@email.de",
			"company", "I am interested", "token", "127.0.0.1");

	@Test
	@DisplayName("should have called time function from metric port")
	void submit_called_time_metric() {
		// given
		given(metricPortMock.time(eq(MetricName.MAIL_SENT), any(Supplier.class))).willAnswer(invocation -> {
			Supplier<Void> task = invocation.getArgument(1);
			return task.get();
		});
		underTest = new InstrumentedContactFormService(delegate, metricPortMock);

		// when
		underTest.submit(validSubmission);

		// then
		verify(delegate).submit(eq(validSubmission));
		verify(metricPortMock).time(eq(MetricName.MAIL_SENT), any(Supplier.class));
	}

	@Test
	@DisplayName("should count success metric when delegate call is successful")
	void submit_counts_success_metric() {
		// given
		given(metricPortMock.time(eq(MetricName.MAIL_SENT), any(Supplier.class))).willAnswer(invocation -> {
			Supplier<Void> task = invocation.getArgument(1);
			return task.get();
		});
		underTest = new InstrumentedContactFormService(delegate, metricPortMock);

		// when
		underTest.submit(validSubmission);

		// then
		verify(delegate).submit(eq(validSubmission));
		verify(metricPortMock).count(eq(MetricName.CONTACT_FORM_SUBMITTED), eq(new MetricTag("status", "success")));
		verify(metricPortMock, never()).count(eq(MetricName.CONTACT_FORM_SUBMITTED),
				eq(new MetricTag("status", "failure")));
	}

	@Test
	@DisplayName("should count failure metric when delegate call throws exception")
	void submit_counts_failure_metric() {
		// given
		doThrow(new MailDeliveryException("Something went wrong", new IllegalStateException())).when(delegate)
				.submit(eq(validSubmission));
		given(metricPortMock.time(any(), any())).willAnswer(invocation -> {
			Supplier<Void> task = invocation.getArgument(1);
			return task.get();
		});
		underTest = new InstrumentedContactFormService(delegate, metricPortMock);

		// when
		assertThatThrownBy(() -> underTest.submit(validSubmission)).isInstanceOf(MailDeliveryException.class);

		// then
		verify(delegate).submit(eq(validSubmission));
		verify(metricPortMock).count(eq(MetricName.CONTACT_FORM_SUBMITTED), eq(new MetricTag("status", "failure")));
		verify(metricPortMock, never()).count(eq(MetricName.CONTACT_FORM_SUBMITTED),
				eq(new MetricTag("status", "success")));
	}
}