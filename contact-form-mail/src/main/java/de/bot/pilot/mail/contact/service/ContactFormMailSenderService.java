package de.bot.pilot.mail.contact.service;

import de.bot.pilot.mail.contact.dto.ContactRequest;
import de.bot.pilot.mail.contact.persistence.domain.ContactFormCustomer;
import de.bot.pilot.mail.contact.persistence.domain.EmailRequest;
import de.bot.pilot.mail.contact.service.api.ContactFormMailSender;
import de.bot.pilot.mail.contact.persistence.repository.api.ContactFormCustomerRepository;
import de.bot.pilot.mail.contact.persistence.repository.api.EmailRequestRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

import static de.bot.pilot.mail.contact.service.mapper.ContactFormCustomerMapper.mapContactFormCustomer;
import static de.bot.pilot.mail.contact.service.mapper.EmailRequestMapper.mapEmailRequest;

@Service
@Transactional
@Slf4j
public class ContactFormMailSenderService implements ContactFormMailSender {

    @Value("${spring.mail.username}")
    private String username;

    private final ContactFormCustomerRepository contactFormCustomerRepository;
    private final EmailRequestRepository emailRequestRepository;
    private final JavaMailSender mailSender;

    public ContactFormMailSenderService(ContactFormCustomerRepository contactFormCustomerRepository,
                                        EmailRequestRepository emailRequestRepository,
                                        JavaMailSender mailSender) {
        this.contactFormCustomerRepository = contactFormCustomerRepository;
        this.emailRequestRepository = emailRequestRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void sendContactFormMail(ContactRequest contactRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(username);
        simpleMailMessage.setFrom(username);

        String subjectTemplate = "Kontaktanfrage: %s";
        var subject = subjectTemplate.formatted(contactRequest.company());

        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(contactRequest.message());
        try {
            mailSender.send(simpleMailMessage);
            saveWithSuccessAndSubject(contactRequest, true, subject);
        } catch (MailException e) {
            saveWithSuccessAndSubject(contactRequest, false, subject);
        }
    }

    private void saveWithSuccessAndSubject(ContactRequest contactRequest, boolean success, String subject) {
        EmailRequest emailRequest = mapEmailRequest(contactRequest, subject);
        ContactFormCustomer contactFormCustomer = mapContactFormCustomer(contactRequest);
        emailRequest.setSuccess(true);
        emailRequest.setContactFormCustomer(contactFormCustomer);
        contactFormCustomerRepository.save(contactFormCustomer);
        emailRequestRepository.save(emailRequest);
    }

}
