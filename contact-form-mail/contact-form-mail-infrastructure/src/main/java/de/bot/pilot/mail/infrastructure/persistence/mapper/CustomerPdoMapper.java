package de.bot.pilot.mail.infrastructure.persistence.mapper;

import de.bot.pilot.mail.domain.model.Customer;
import de.bot.pilot.mail.infrastructure.persistence.entity.CustomerPdo;

public final class CustomerPdoMapper {

	private CustomerPdoMapper() {
	}

	public static CustomerPdo toEntity(Customer customer) {
		CustomerPdo entity = new CustomerPdo();
		entity.setFirstName(customer.firstName());
		entity.setLastName(customer.lastName());
		entity.setEmail(customer.email());
		entity.setCompany(customer.company());
		return entity;
	}
}
