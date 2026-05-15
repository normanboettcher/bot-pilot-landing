package de.bot.pilot.mail.domain.metrics;

public enum MetricName {
	CONTACT_FORM_SUBMITTED("contact.form.submitted"), CAPTCHA_VERIFICATION_FAILURE(
			"captcha.verification.failure"), MAIL_SENT("mail.sent"), ACTIVE_SUBMISSIONS(
					"contact.form.active"), EMAIL_RECORDS_TOTAL("email.records.total"), SMTP_UP("smtp.up");

	private final String name;

	public String getName() {
		return this.name;
	}

	MetricName(String name) {
		this.name = name;
	}
}
