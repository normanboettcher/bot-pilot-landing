package de.bot.pilot.mail.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contact_form_customer")
public class CustomerPdo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String company;

    @OneToMany(mappedBy = "customer")
    private List<EmailRequestPdo> emailRequests = new ArrayList<>();

    public CustomerPdo() {
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getCompany() { return company; }
    public List<EmailRequestPdo> getEmailRequests() { return emailRequests; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setCompany(String company) { this.company = company; }
    public void setEmailRequests(List<EmailRequestPdo> emailRequests) { this.emailRequests = emailRequests; }
}
