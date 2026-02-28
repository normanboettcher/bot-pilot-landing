package de.bot.pilot.crypto.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * HTTP request body v2 — adds 'purpose' field.
 * <p>
 * WHY purpose AS A STRING IN THE DTO (not EncryptionPurpose):
 * DTOs are API contracts. They use primitive types (String, int, boolean).
 * The controller maps the string to a typed EncryptionPurpose.
 * This means: changing the EncryptionPurpose sealed interface
 * (domain-model module) does not require changing the API DTO.
 * API contract stability ≠ domain model stability.
 *
 * @param value   the plaintext or ciphertext content
 * @param purpose one of: MAIL, MESSAGE, USER_PII
 */
public record CryptoRequest(
        @NotBlank(message = "value must not be blank")
        @Size(max = 65536, message = "value must not exceed 64KB")
        String value,

        @NotBlank(message = "purpose must not be blank")
        @Pattern(regexp = "MAIL_ADDRESS|MESSAGE|COMPANY",
                message = "purpose must be one of: MAIL, MESSAGE, USER_PII")
        String purpose
) {
}
