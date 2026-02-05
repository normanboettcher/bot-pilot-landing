package de.bot.pilot.mail.contact.controller;

import de.bot.pilot.mail.contact.dto.ContactRequest;
import de.bot.pilot.mail.contact.service.api.CaptchaService;
import de.bot.pilot.mail.contact.service.api.ContactFormMailSender;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/contact")
public class ContactFormController {
    private final ContactFormMailSender contactFormMailSender;
    private final CaptchaService captchaService;

    public ContactFormController(ContactFormMailSender contactFormMailSender, CaptchaService captchaService) {
        this.contactFormMailSender = contactFormMailSender;
        this.captchaService = captchaService;
    }

    @PostMapping
    public ResponseEntity<Void> send(@Valid @RequestBody ContactRequest contactRequest, HttpServletRequest request) throws IOException {
        //captchaService.verify(contactRequest.captchaToken(), request.getRemoteAddr());
        contactFormMailSender.sendContactFormMail(contactRequest);
        return ResponseEntity.accepted().build();
    }
}
