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

        Customer customer = ImmutableCustomer.builder().firstName(submission.firstName())
                .lastName(submission.lastName()).email(submission.email()).company(submission.company()).build();

        String subject = SUBJECT_TEMPLATE.formatted(submission.company());
        MailMessage message = new MailMessage(subject, submission.message());

        boolean mailSuccess = trySendMail(message);

        // TODO: handle encryption right here before persisting. Persist encrypted data, never plaintext.
        EmailRecord emailRecord = ImmutableEmailRecord.builder().content(submission.message()).subject(subject)
                .createdAt(Instant.now()).success(mailSuccess).build();

        contactFormRecordPort.save(customer, emailRecord);
    }

    private boolean trySendMail(MailMessage message) {
        try {
            mailPort.sendNotification(message);
            return true;
        } catch (MailDeliveryException e) {
            LOGGER.warn("Mail delivery failed for subject '{}': {}", message.subject(), e.getMessage());
            return false;
        }
    }
}
