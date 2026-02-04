package de.bot.pilot.mail.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

    protected EmailRequest() {
        //JPA
    }

    @Override
    public String toString() {
        return String.format("EmailRequest[id='%s', content='%s', subject='%s', " +
                        "contactFormCustomer='%s']",
                id, content, subject, contactFormCustomer.toString());
    }
}
