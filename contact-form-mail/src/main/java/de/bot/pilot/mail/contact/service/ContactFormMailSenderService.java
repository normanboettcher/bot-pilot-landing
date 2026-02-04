package de.bot.pilot.mail.contact.service;

import de.bot.pilot.mail.contact.dto.ContactRequest;
import de.bot.pilot.mail.contact.service.api.ContactFormMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class ContactFormMailSenderService implements ContactFormMailSender {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender mailSender;

    public ContactFormMailSenderService(JavaMailSender mailSender) {
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
        mailSender.send(simpleMailMessage);
    }
}
