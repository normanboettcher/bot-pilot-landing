package de.bot.pilot.mail.contact.persistence.repository.api;

import de.bot.pilot.mail.contact.persistence.domain.ContactFormCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactFormCustomerRepository extends JpaRepository<ContactFormCustomer, Long> {
    ContactFormCustomer findByEmail(String email);
}
