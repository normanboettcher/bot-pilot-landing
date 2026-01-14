package de.bot.pilot.mail.contact.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Represents a contact request containing details
 * provided by a user intending to make a contact request.
 * The record ensures essential fields are provided
 * and validated.
 * <p>
 * Fields:
 * <ul>
 * <li>firstName: The first name of the user. It must not be blank. </li>
 * <li>lastName: The last name of the user. It must not be blank. </li>
 * <li> email: The email address of the user. It must be a valid email format and not blank. </li>
 * <li> company: The company name associated with the user. It must not be blank. </li>
 * <li> message: The message content sent by the user. It must not be blank. </li>
 * </ul>
 * <p>
 * This class also provides a utility method to retrieve the user's full name.
 */
public record ContactRequest(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String company,
        @NotBlank
        String message,
        @NotBlank
        String captchaToken
) {
    public String fullName() {
        return firstName + " " + lastName;
    }
}
