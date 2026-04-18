package de.bot.pilot.mail.domain.model;

/**
 * Immutable command object carrying everything the contact form submitted.
 * Captcha token and remote IP travel with the submission so the application
 * layer can verify the request without a second inbound call.
 */
public record ContactSubmission(String firstName, String lastName, String email, String company, String message,
		String captchaToken, String remoteIp) {
	public String fullName() {
		return firstName + " " + lastName;
	}
}
