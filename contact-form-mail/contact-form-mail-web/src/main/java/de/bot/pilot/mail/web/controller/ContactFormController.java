package de.bot.pilot.mail.web.controller;

import de.bot.pilot.mail.application.port.inbound.ContactFormUseCase;
import de.bot.pilot.mail.domain.model.ContactSubmission;
import de.bot.pilot.mail.web.dto.ContactRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactFormController {

    private final ContactFormUseCase contactFormUseCase;

    public ContactFormController(ContactFormUseCase contactFormUseCase) {
        this.contactFormUseCase = contactFormUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> submit(@Valid @RequestBody ContactRequest request,
                                       HttpServletRequest httpRequest) {
        ContactSubmission submission = new ContactSubmission(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.company(),
                request.message(),
                request.captchaToken(),
                httpRequest.getRemoteAddr()
        );
        contactFormUseCase.submit(submission);
        return ResponseEntity.accepted().build();
    }
}
