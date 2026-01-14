package de.bot.pilot.mail.contact.service.api;

import de.bot.pilot.mail.contact.dto.ContactRequest;


/**
 * Interface defining the contract for sending emails based on contact form submissions.
 * <p>
 * This interface is intended to handle the process of sending an email generated from
 * a contact form submission. The implementation of this interface should define the
 * specific behavior for transforming the contact request data into an email and sending it
 * via a configured email service.
 */
public interface ContactFormMailSender {

    /**
     * Sends an email based on the provided contact form submission details.
     * This method takes a {@link ContactRequest} object as input, which contains
     * the user's contact information and message content, and processes it
     * to send an email using the configured email service.
     *
     * @param contactRequest the contact request object that contains user details such as
     *        first name, last name, email address, company name, and message content.
     *        This parameter must not be null and should contain valid and non-blank values
     *        as required by the {@link ContactRequest} class.
     */
    void sendContactFormMail(final ContactRequest contactRequest);
}
