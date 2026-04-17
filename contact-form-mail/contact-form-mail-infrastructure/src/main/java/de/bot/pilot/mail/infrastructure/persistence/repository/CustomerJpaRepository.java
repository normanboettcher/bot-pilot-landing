package de.bot.pilot.mail.infrastructure.persistence.repository;

import de.bot.pilot.mail.infrastructure.persistence.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, Long> {

    CustomerJpaEntity findByEmail(String email);
}
