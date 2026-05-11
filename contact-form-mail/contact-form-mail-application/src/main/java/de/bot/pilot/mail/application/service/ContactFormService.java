package de.bot.pilot.mail.application.service;

import de.bot.pilot.mail.application.port.inbound.ContactFormUseCase;
import de.bot.pilot.mail.domain.exception.MailDeliveryException;
import de.bot.pilot.mail.domain.model.ContactSubmission;
import de.bot.pilot.mail.domain.model.Customer;
import de.bot.pilot.mail.domain.model.EmailRecord;
import de.bot.pilot.mail.domain.model.ImmutableCustomer;
import de.bot.pilot.mail.domain.model.ImmutableEmailRecord;
import de.bot.pilot.mail.domain.model.MailMessage;
import de.bot.pilot.mail.domain.port.outbound.CaptchaPort;
import de.bot.pilot.mail.domain.port.outbound.ContactFormRecordPort;
import de.bot.pilot.mail.domain.port.outbound.EncryptionPort;
import de.bot.pilot.mail.domain.port.outbound.MailPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

/**
 * Interactor implementing the contact form use case. No Spring annotations —
 * wired explicitly by the bootstrap configuration.
 * <p>
 * Flow: 1. Verify captcha (fail-fast) 2. Build domain objects 3. Attempt mail
 * delivery, record outcome 4. Persist customer + email record atomically via
 * ContactFormRecordPort
 */
public class ContactFormService implements ContactFormUseCase {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactFormService.class);
	private static final String SUBJECT_TEMPLATE = "Kontaktanfrage: %s";

	private final CaptchaPort captchaPort;
	private final MailPort mailPort;
	private final ContactFormRecordPort contactFormRecordPort;
	private final EncryptionPort encryptionPort;

	public ContactFormService(CaptchaPort captchaPort, MailPort mailPort, ContactFormRecordPort contactFormRecordPort,
			EncryptionPort encryptionPort) {
		this.captchaPort = captchaPort;
		this.mailPort = mailPort;
		this.contactFormRecordPort = contactFormRecordPort;
		this.encryptionPort = encryptionPort;
	}

	@Override
	public void submit(ContactSubmission submission) {
		captchaPort.verify(submission.captchaToken(), submission.remoteIp());

		String subject = SUBJECT_TEMPLATE.formatted(submission.company());
		MailMessage message = new MailMessage(subject, submission.message());

		boolean mailSuccess = trySendMail(message);

		var encrypted = encryptMail(new MailEncryptionInput(submission.message(), subject));
		EmailRecord emailRecord = ImmutableEmailRecord.builder().content(encrypted.encryptedMessage())
				.subject(encrypted.encryptedSubject()).createdAt(Instant.now()).success(mailSuccess).build();
		var encryptedCustomer = encryptCustomer(new CustomerEncryptionInput(submission.company(), submission.email(),
				submission.firstName(), submission.lastName()));
		Customer customer = ImmutableCustomer.builder().firstName(encryptedCustomer.encryptedFirstName())
				.lastName(encryptedCustomer.encryptedLastName()).email(encryptedCustomer.encryptedEmail())
				.company(encryptedCustomer.encryptedCompany()).build();

		contactFormRecordPort.save(customer, emailRecord);
	}

	private boolean trySendMail(MailMessage message) {
		try {
			mailPort.sendNotification(message);
			return true;
		} catch (MailDeliveryException e) {
			LOGGER.warn("Mail delivery failed");
			return false;
		}
	}

	private CustomerEncryptionOutput encryptCustomer(final CustomerEncryptionInput input) {
		var encryptedCompany = this.encryptionPort.encrypt(input.company());
		var encryptedEmail = this.encryptionPort.encrypt(input.email());
		var encryptedFirstName = this.encryptionPort.encrypt(input.firstName());
		var encryptedLastName = this.encryptionPort.encrypt(input.lastName());
		return new CustomerEncryptionOutput(encryptedCompany, encryptedEmail, encryptedFirstName, encryptedLastName);
	}

	private MailEncryptionOutput encryptMail(final MailEncryptionInput input) {
		var message = this.encryptionPort.encrypt(input.message());
		var subject = this.encryptionPort.encrypt(input.subject());
		return new MailEncryptionOutput(message, subject);
	}

	record CustomerEncryptionInput(String company, String email, String firstName, String lastName) {
	}

	record CustomerEncryptionOutput(String encryptedCompany, String encryptedEmail, String encryptedFirstName,
			String encryptedLastName) {
	}

	record MailEncryptionInput(String message, String subject) {
	}

	record MailEncryptionOutput(String encryptedMessage, String encryptedSubject) {
	}
}
