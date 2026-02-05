package de.bot.pilot.mail.contact.persistence.repository.api;

import de.bot.pilot.mail.contact.persistence.domain.EmailRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRequestRepository extends JpaRepository<EmailRequest, Long> {

}
