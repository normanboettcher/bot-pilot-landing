package de.bot.pilot.mail.domain.port.outbound;

import de.bot.pilot.mail.domain.model.Customer;
import de.bot.pilot.mail.domain.model.EmailRecord;

public interface ContactFormRecordPort {

    /**
     * Persists the customer and its associated email record atomically.
     * Transactional boundary is owned by the adapter implementation.
     */
    void save(Customer customer, EmailRecord emailRecord);
}
