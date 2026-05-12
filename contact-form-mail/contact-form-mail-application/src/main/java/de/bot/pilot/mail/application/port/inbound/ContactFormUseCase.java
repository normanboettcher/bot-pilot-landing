package de.bot.pilot.mail.application.port.inbound;

import de.bot.pilot.mail.domain.model.ContactSubmission;

/**
 * Inbound port — the only entry point for submitting a contact form. Driving
 * adapters (e.g. REST controller) depend on this interface, never on the
 * interactor.
 */
public interface ContactFormUseCase {
	// TODO: Use a SendMailResult record instead of void
	void submit(ContactSubmission submission);
}
