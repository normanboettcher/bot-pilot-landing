package de.bot.pilot.mail.infrastructure.persistence.adapter;

import de.bot.pilot.mail.domain.model.Customer;
import de.bot.pilot.mail.domain.model.EmailRecord;
import de.bot.pilot.mail.domain.port.outbound.ContactFormRecordPort;
import de.bot.pilot.mail.infrastructure.persistence.entity.CustomerJpaEntity;
import de.bot.pilot.mail.infrastructure.persistence.entity.EmailRequestJpaEntity;
import de.bot.pilot.mail.infrastructure.persistence.mapper.CustomerEntityMapper;
import de.bot.pilot.mail.infrastructure.persistence.mapper.EmailRecordEntityMapper;
import de.bot.pilot.mail.infrastructure.persistence.repository.CustomerJpaRepository;
import de.bot.pilot.mail.infrastructure.persistence.repository.EmailRequestJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class ContactFormRecordAdapter implements ContactFormRecordPort {

    private final CustomerJpaRepository customerRepository;
    private final EmailRequestJpaRepository emailRequestRepository;

    public ContactFormRecordAdapter(CustomerJpaRepository customerRepository,
                                    EmailRequestJpaRepository emailRequestRepository) {
        this.customerRepository = customerRepository;
        this.emailRequestRepository = emailRequestRepository;
    }

    @Override
    @Transactional
    public void save(Customer customer, EmailRecord emailRecord) {
        CustomerJpaEntity savedCustomer = customerRepository.save(CustomerEntityMapper.toEntity(customer));
        EmailRequestJpaEntity emailRequestEntity = EmailRecordEntityMapper.toEntity(emailRecord, savedCustomer);
        emailRequestRepository.save(emailRequestEntity);
    }
}
