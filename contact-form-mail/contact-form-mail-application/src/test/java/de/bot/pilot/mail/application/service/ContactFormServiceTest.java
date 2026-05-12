package de.bot.pilot.mail.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import de.bot.pilot.mail.domain.error.EncryptionError;
import de.bot.pilot.mail.domain.exception.CaptchaVerificationException;
import de.bot.pilot.mail.domain.exception.EncryptionException;
import de.bot.pilot.mail.domain.exception.MailDeliveryException;
import de.bot.pilot.mail.domain.model.ContactSubmission;
import de.bot.pilot.mail.domain.model.Customer;
import de.bot.pilot.mail.domain.model.EmailRecord;
import de.bot.pilot.mail.domain.port.outbound.CaptchaPort;
import de.bot.pilot.mail.domain.port.outbound.ContactFormRecordPort;
import de.bot.pilot.mail.domain.port.outbound.EncryptionPort;
import de.bot.pilot.mail.domain.port.outbound.MailPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContactFormServiceTest {

	private static final String FIRST_NAME = "Ada";
	private static final String LAST_NAME = "Lovelace";
	private static final String EMAIL = "ada@example.com";
	private static final String COMPANY = "Acme";
	private static final String MESSAGE = "Hello world";
	private static final String CAPTCHA_TOKEN = "token-abc";
	private static final String REMOTE_IP = "127.0.0.1";
	private static final String EXPECTED_RAW_SUBJECT = "Kontaktanfrage: Acme";

	@Mock
	private CaptchaPort captchaPort;

	@Mock
	private MailPort mailPort;

	@Mock
	private ContactFormRecordPort contactFormRecordPort;

	@Mock
	private EncryptionPort encryptionPort;

	@InjectMocks
	private ContactFormService contactFormService;

	private ContactSubmission validSubmission() {
		return new ContactSubmission(FIRST_NAME, LAST_NAME, EMAIL, COMPANY, MESSAGE, CAPTCHA_TOKEN, REMOTE_IP);
	}

	@Test
	@DisplayName("Happy path: captcha passes and mail is sent — save is called with success=true and encrypted fields")
	void submit_happyPath_savesEncryptedRecordWithSuccessTrue() {
		// given
		when(encryptionPort.encrypt(MESSAGE)).thenReturn("enc:message");
		when(encryptionPort.encrypt(EXPECTED_RAW_SUBJECT)).thenReturn("enc:subject");
		when(encryptionPort.encrypt(COMPANY)).thenReturn("enc:company");
		when(encryptionPort.encrypt(EMAIL)).thenReturn("enc:email");
		when(encryptionPort.encrypt(FIRST_NAME)).thenReturn("enc:firstName");
		when(encryptionPort.encrypt(LAST_NAME)).thenReturn("enc:lastName");
		ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
		ArgumentCaptor<EmailRecord> emailRecordCaptor = ArgumentCaptor.forClass(EmailRecord.class);

		// when
		contactFormService.submit(validSubmission());

		// then
		verify(contactFormRecordPort).save(customerCaptor.capture(), emailRecordCaptor.capture());
		Customer savedCustomer = customerCaptor.getValue();
		EmailRecord savedRecord = emailRecordCaptor.getValue();
		assertThat(savedRecord.content()).isEqualTo("enc:message");
		assertThat(savedRecord.subject()).isEqualTo("enc:subject");
		assertThat(savedCustomer.firstName()).isEqualTo("enc:firstName");
		assertThat(savedCustomer.lastName()).isEqualTo("enc:lastName");
		assertThat(savedCustomer.email()).isEqualTo("enc:email");
		assertThat(savedCustomer.company()).isEqualTo("enc:company");
	}

	@Test
	@DisplayName("Mail delivery fails: Exception should be thrown and propagates")
	void submit_mailDeliveryFails_savesRecordWithSuccessFalse() {
		// given
		doThrow(new MailDeliveryException("SMTP timeout", new RuntimeException())).when(mailPort)
				.sendNotification(any());

		// then
		assertThatThrownBy(() -> contactFormService.submit(validSubmission())).isInstanceOf(MailDeliveryException.class)
				.hasMessage("SMTP timeout");
		verify(contactFormRecordPort, times(0)).save(any(), any());
	}

	@Test
	@DisplayName("Captcha fails: CaptchaVerificationException propagates and save is never called")
	void submit_captchaFails_propagatesExceptionAndNeverSaves() {
		// given
		doThrow(new CaptchaVerificationException("Invalid captcha")).when(captchaPort).verify(CAPTCHA_TOKEN, REMOTE_IP);

		// when / then
		assertThatThrownBy(() -> contactFormService.submit(validSubmission()))
				.isInstanceOf(CaptchaVerificationException.class).hasMessage("Invalid captcha");
		verify(contactFormRecordPort, never()).save(any(), any());
	}

	@Test
	@DisplayName("Encryption fails: EncryptionException propagates and save is never called")
	void submit_encryptionFails_propagatesExceptionAndNeverSaves() {
		// given
		var encryptionError = new EncryptionError.Unauthorized("Vault auth failed", new RuntimeException());
		doThrow(new EncryptionException(encryptionError)).when(encryptionPort).encrypt(any());

		// when / then
		assertThatThrownBy(() -> contactFormService.submit(validSubmission())).isInstanceOf(EncryptionException.class);
		verify(contactFormRecordPort, never()).save(any(), any());
	}

	@Test
	@DisplayName("Subject is stored encrypted: emailRecord.subject() equals the encrypted value, not the raw subject")
	void submit_subjectIsEncrypted_storedSubjectIsNotRawSubject() {
		// given
		String encryptedSubject = "vault:v1:encryptedSubject==";
		when(encryptionPort.encrypt(eq(EXPECTED_RAW_SUBJECT))).thenReturn(encryptedSubject);
		when(encryptionPort.encrypt(eq(MESSAGE))).thenReturn("enc:message");
		when(encryptionPort.encrypt(eq(COMPANY))).thenReturn("enc:company");
		when(encryptionPort.encrypt(eq(EMAIL))).thenReturn("enc:email");
		when(encryptionPort.encrypt(eq(FIRST_NAME))).thenReturn("enc:firstName");
		when(encryptionPort.encrypt(eq(LAST_NAME))).thenReturn("enc:lastName");
		ArgumentCaptor<EmailRecord> emailRecordCaptor = ArgumentCaptor.forClass(EmailRecord.class);

		// when
		contactFormService.submit(validSubmission());

		// then
		verify(contactFormRecordPort).save(any(), emailRecordCaptor.capture());
		EmailRecord savedRecord = emailRecordCaptor.getValue();
		assertThat(savedRecord.subject()).isEqualTo(encryptedSubject);
		assertThat(savedRecord.subject()).isNotEqualTo(EXPECTED_RAW_SUBJECT);
	}
}
