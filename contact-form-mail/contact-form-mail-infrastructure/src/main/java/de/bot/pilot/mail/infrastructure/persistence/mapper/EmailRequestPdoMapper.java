package de.bot.pilot.mail.infrastructure.persistence.mapper;

import de.bot.pilot.mail.domain.model.EmailRecord;
import de.bot.pilot.mail.infrastructure.persistence.entity.CustomerPdo;
import de.bot.pilot.mail.infrastructure.persistence.entity.EmailRequestPdo;

import java.sql.Timestamp;

public final class EmailRequestPdoMapper {

	private EmailRequestPdoMapper() {
	}

	public static EmailRequestPdo toEntity(EmailRecord record, CustomerPdo customer) {
		EmailRequestPdo entity = new EmailRequestPdo();
		entity.setContent(record.content());
		entity.setSubject(record.subject());
		entity.setCreatedAt(Timestamp.from(record.createdAt()));
		entity.setSuccess(record.success());
		entity.setCustomer(customer);
		return entity;
	}
}
