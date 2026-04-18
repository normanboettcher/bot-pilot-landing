package de.bot.pilot.mail.infrastructure.persistence.repository;

import de.bot.pilot.mail.infrastructure.persistence.entity.CustomerPdo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerPdo, Long> {

	CustomerPdo findByEmail(String email);
}
