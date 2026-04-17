package de.bot.pilot.mail.config;

import de.bot.pilot.mail.application.port.inbound.ContactFormUseCase;
import de.bot.pilot.mail.application.service.ContactFormService;
import de.bot.pilot.mail.domain.port.outbound.CaptchaPort;
import de.bot.pilot.mail.domain.port.outbound.ContactFormRecordPort;
import de.bot.pilot.mail.domain.port.outbound.MailPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeanConfig {

    @Bean
    public ContactFormUseCase contactFormUseCase(CaptchaPort captchaPort,
                                                 MailPort mailPort,
                                                 ContactFormRecordPort contactFormRecordPort) {
        return new ContactFormService(captchaPort, mailPort, contactFormRecordPort);
    }
}
