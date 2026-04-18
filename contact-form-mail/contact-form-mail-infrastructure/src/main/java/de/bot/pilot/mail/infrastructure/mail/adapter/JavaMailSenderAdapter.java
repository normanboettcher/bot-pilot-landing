package de.bot.pilot.mail.infrastructure.mail.adapter;

import de.bot.pilot.mail.domain.exception.MailDeliveryException;
import de.bot.pilot.mail.domain.model.MailMessage;
import de.bot.pilot.mail.domain.port.outbound.MailPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class JavaMailSenderAdapter implements MailPort {

    private final JavaMailSender mailSender;
    private final String smtpUsername;

    public JavaMailSenderAdapter(JavaMailSender mailSender,
                                 @Value("${spring.mail.username}") String smtpUsername) {
        this.mailSender = mailSender;
        this.smtpUsername = smtpUsername;
    }

    @Override
    public void sendNotification(MailMessage message) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(smtpUsername);
        mail.setFrom(smtpUsername);
        mail.setSubject(message.subject());
        mail.setText(message.body());
        try {
            mailSender.send(mail);
        } catch (MailException e) {
            throw new MailDeliveryException("SMTP delivery failed: " + e.getMessage(), e);
        }
    }
}
