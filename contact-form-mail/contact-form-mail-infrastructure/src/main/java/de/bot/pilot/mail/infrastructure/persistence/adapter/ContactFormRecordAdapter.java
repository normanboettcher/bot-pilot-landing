package de.bot.pilot.mail.infrastructure.persistence.adapter;

import de.bot.pilot.mail.domain.model.Customer;
import de.bot.pilot.mail.domain.model.EmailRecord;
import de.bot.pilot.mail.domain.port.outbound.ContactFormRecordPort;
import de.bot.pilot.mail.infrastructure.persistence.entity.CustomerPdo;
import de.bot.pilot.mail.infrastructure.persistence.entity.EmailRequestPdo;
import de.bot.pilot.mail.infrastructure.persistence.mapper.CustomerPdoMapper;
import de.bot.pilot.mail.infrastructure.persistence.mapper.EmailRequestPdoMapper;
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
        CustomerPdo savedCustomer = customerRepository.save(CustomerPdoMapper.toEntity(customer));
        EmailRequestPdo emailRequestEntity = EmailRequestPdoMapper.toEntity(emailRecord, savedCustomer);
        emailRequestRepository.save(emailRequestEntity);
    }
}
