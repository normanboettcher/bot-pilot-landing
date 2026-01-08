package de.bot.pilot.mail.contact.controller;

import de.bot.pilot.mail.contact.dto.ContactRequest;
import de.bot.pilot.mail.contact.service.ContactFormMailSender;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactFormController {
    private final ContactFormMailSender contactFormMailSender;


    public ContactFormController(ContactFormMailSender contactFormMailSender) {
        this.contactFormMailSender = contactFormMailSender;
    }

    @PostMapping
    public ResponseEntity<Void> send(@Valid @RequestBody ContactRequest contactRequest) {
        contactFormMailSender.sendContactFormMail(contactRequest);
        return ResponseEntity.accepted().build();
    }
}
