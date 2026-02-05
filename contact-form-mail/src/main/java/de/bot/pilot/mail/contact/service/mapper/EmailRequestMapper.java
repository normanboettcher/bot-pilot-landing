package de.bot.pilot.mail.contact.service.mapper;

import de.bot.pilot.mail.contact.dto.ContactRequest;
import de.bot.pilot.mail.contact.persistence.domain.EmailRequest;

import java.sql.Timestamp;
import java.time.Instant;

public class EmailRequestMapper {
    public static EmailRequest mapEmailRequest(ContactRequest contactRequest, String subject) {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setContent(contactRequest.message());
        emailRequest.setSubject(subject);
        emailRequest.setCreatedAt(Timestamp.from(Instant.now()));
        return emailRequest;
    }
}
