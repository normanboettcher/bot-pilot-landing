package de.bot.pilot.mail.contact.persistence.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Timestamp;

/**
 * Represents an email request entity within the system.
 * This class is used to define the structure of email requests
 * and is mapped to the "email_request" table in the database.
 * <p>
 * An EmailRequest is associated with a {@code ContactFormCustomer},
 * representing the customer who initiated the request.
 * <p>
 * The class includes fields such as:
 * - id: Unique identifier for the email request.
 * - content: The body of the email.
 * - subject: The subject of the email.
 * - contactFormCustomer: The customer who initiated the email request.
 * <p>
 * This class also includes a default constructor intended for JPA and
 * overrides the {@code toString} method for meaningful text representation.
 */
@Entity
@Table(name = "email_request")
public class EmailRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String content;
    private String subject;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "contact_form_customer_id", nullable = false)
    private ContactFormCustomer contactFormCustomer;
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());
    private boolean success;

    public EmailRequest() {
        //JPA
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public ContactFormCustomer getContactFormCustomer() {
        return contactFormCustomer;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContactFormCustomer(ContactFormCustomer contactFormCustomer) {
        this.contactFormCustomer = contactFormCustomer;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return String.format("EmailRequest[id='%s', content='%s', subject='%s', " +
                        "contactFormCustomer='%s', timestamp='%s', success='%s']",
                id, content, subject, contactFormCustomer.toString(),
                createdAt.toInstant().toString());
    }
}
