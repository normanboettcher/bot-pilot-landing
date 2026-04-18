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
import de.bot.pilot.mail.domain.port.outbound.MailPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

/**
 * Interactor implementing the contact form use case.
 * No Spring annotations — wired explicitly by the bootstrap configuration.
 *
 * Flow:
 *   1. Verify captcha (fail-fast)
 *   2. Build domain objects
 *   3. Attempt mail delivery, record outcome
 *   4. Persist customer + email record atomically via ContactFormRecordPort
 */
public class ContactFormService implements ContactFormUseCase {

    private static final Logger log = LoggerFactory.getLogger(ContactFormService.class);
    private static final String SUBJECT_TEMPLATE = "Kontaktanfrage: %s";

    private final CaptchaPort captchaPort;
    private final MailPort mailPort;
    private final ContactFormRecordPort contactFormRecordPort;

    public ContactFormService(CaptchaPort captchaPort,
                              MailPort mailPort,
                              ContactFormRecordPort contactFormRecordPort) {
        this.captchaPort = captchaPort;
        this.mailPort = mailPort;
        this.contactFormRecordPort = contactFormRecordPort;
    }

    @Override
    public void submit(ContactSubmission submission) {
        captchaPort.verify(submission.captchaToken(), submission.remoteIp());

        Customer customer = ImmutableCustomer.builder()
                .firstName(submission.firstName())
                .lastName(submission.lastName())
                .email(submission.email())
                .company(submission.company())
                .build();

        String subject = SUBJECT_TEMPLATE.formatted(submission.company());
        MailMessage message = new MailMessage(subject, submission.message());

        boolean mailSuccess = trySendMail(message);

        EmailRecord emailRecord = ImmutableEmailRecord.builder()
                .content(submission.message())
                .subject(subject)
                .createdAt(Instant.now())
                .success(mailSuccess)
                .build();

        contactFormRecordPort.save(customer, emailRecord);
    }

    private boolean trySendMail(MailMessage message) {
        try {
            mailPort.sendNotification(message);
            return true;
        } catch (MailDeliveryException e) {
            log.warn("Mail delivery failed for subject '{}': {}", message.subject(), e.getMessage());
            return false;
        }
    }
}
