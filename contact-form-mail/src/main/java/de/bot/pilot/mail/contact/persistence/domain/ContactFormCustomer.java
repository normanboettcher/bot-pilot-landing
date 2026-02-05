package de.bot.pilot.mail.contact.persistence.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer interacting via the contact form on a platform.
 * This class is used to define the structure of a customer entity
 * and is mapped to the "contact_form_customer" table in the database.
 * <p>
 * A ContactFormCustomer contains attributes to capture the customer's details,
 * and is associated with multiple {@code EmailRequest} entities, each representing
 * distinct email communications initiated by the customer.
 * <p>
 * The class includes the following fields:
 * - id: A unique identifier for the customer.
 * - firstName: The first name of the customer.
 * - lastName: The last name of the customer.
 * - email: The email address of the customer.
 * - company: The company associated with the customer.
 * - emailRequests: A list of {@code EmailRequest} entities that are linked to the customer.
 * <p>
 * This class also contains a default constructor for JPA and overrides the {@code toString}
 * method to provide a meaningful text representation of the customer's details.
 */
@Entity
@Table(name = "contact_form_customer")
public class ContactFormCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String company;

    @OneToMany(mappedBy = "contactFormCustomer")
    private List<EmailRequest> emailRequests = new ArrayList<>();

    public ContactFormCustomer() {
        //JPA
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setEmailRequests(List<EmailRequest> emailRequests) {
        this.emailRequests = emailRequests;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public List<EmailRequest> getEmailRequests() {
        return emailRequests;
    }

    @Override
    public String toString() {
        return String.format("ContactFormCustomer=[id='%s', firstName='%s', " +
                        "lastName='%s', email='%s', company='%s']",
                id, firstName, lastName, email, company);
    }
}
