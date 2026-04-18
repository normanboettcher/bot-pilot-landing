package de.bot.pilot.mail.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "email_request")
public class EmailRequestPdo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String content;
	private String subject;
	private Timestamp createdAt = Timestamp.from(Instant.now());
	private boolean success;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "contact_form_customer_id", nullable = false)
	private CustomerPdo customer;

	public EmailRequestPdo() {
	}

	public Long getId() {
		return id;
	}
	public String getContent() {
		return content;
	}
	public String getSubject() {
		return subject;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public boolean isSuccess() {
		return success;
	}
	public CustomerPdo getCustomer() {
		return customer;
	}

	public void setContent(String content) {
		this.content = content;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setCustomer(CustomerPdo customer) {
		this.customer = customer;
	}
}
