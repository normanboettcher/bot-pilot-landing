package de.bot.pilot.mail.contact.service.mapper;

import de.bot.pilot.mail.contact.dto.ContactRequest;
import de.bot.pilot.mail.contact.persistence.domain.ContactFormCustomer;

public class ContactFormCustomerMapper {

    public static ContactFormCustomer mapContactFormCustomer(ContactRequest contactRequest) {
        ContactFormCustomer customer = new ContactFormCustomer();
        customer.setFirstName(contactRequest.firstName());
        customer.setLastName(contactRequest.lastName());
        customer.setEmail(contactRequest.email());
        customer.setCompany(contactRequest.company());
        return customer;
    }
}
