package de.bot.pilot.mail.infrastructure.persistence.mapper;

import de.bot.pilot.mail.domain.model.EmailRecord;
import de.bot.pilot.mail.infrastructure.persistence.entity.CustomerJpaEntity;
import de.bot.pilot.mail.infrastructure.persistence.entity.EmailRequestJpaEntity;

import java.sql.Timestamp;

public final class EmailRecordEntityMapper {

    private EmailRecordEntityMapper() {
    }

    public static EmailRequestJpaEntity toEntity(EmailRecord record, CustomerJpaEntity customer) {
        EmailRequestJpaEntity entity = new EmailRequestJpaEntity();
        entity.setContent(record.content());
        entity.setSubject(record.subject());
        entity.setCreatedAt(Timestamp.from(record.createdAt()));
        entity.setSuccess(record.success());
        entity.setCustomer(customer);
        return entity;
    }
}
