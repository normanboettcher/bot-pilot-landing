package de.bot.pilot.mail.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Inbound web DTO. Bean Validation annotations belong here, not in the domain.
 * Mapped to {@link de.bot.pilot.mail.domain.model.ContactSubmission} before crossing the port boundary.
 */
public record ContactRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank @Email String email,
        @NotBlank String company,
        @NotBlank String message,
        @NotBlank String captchaToken
) {
}
