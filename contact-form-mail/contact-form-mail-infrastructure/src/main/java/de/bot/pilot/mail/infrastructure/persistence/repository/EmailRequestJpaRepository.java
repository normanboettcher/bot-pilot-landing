package de.bot.pilot.mail.infrastructure.persistence.repository;

import de.bot.pilot.mail.infrastructure.persistence.entity.EmailRequestPdo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRequestJpaRepository extends JpaRepository<EmailRequestPdo, Long> {
}
