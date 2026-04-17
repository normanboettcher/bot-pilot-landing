package de.bot.pilot.mail.domain.port.outbound;

import de.bot.pilot.mail.domain.exception.MailDeliveryException;
import de.bot.pilot.mail.domain.model.MailMessage;

public interface MailPort {

    /**
     * Delivers a contact form notification.
     * The adapter resolves to/from addresses from its own SMTP configuration.
     *
     * @throws MailDeliveryException if the underlying transport fails
     */
    void sendNotification(MailMessage message);
}
