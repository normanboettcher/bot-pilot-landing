package de.bot.pilot.mail.infrastructure.persistence.mapper;

import de.bot.pilot.mail.domain.model.Customer;
import de.bot.pilot.mail.infrastructure.persistence.entity.CustomerJpaEntity;

public final class CustomerEntityMapper {

    private CustomerEntityMapper() {
    }

    public static CustomerJpaEntity toEntity(Customer customer) {
        CustomerJpaEntity entity = new CustomerJpaEntity();
        entity.setFirstName(customer.firstName());
        entity.setLastName(customer.lastName());
        entity.setEmail(customer.email());
        entity.setCompany(customer.company());
        return entity;
    }
}
