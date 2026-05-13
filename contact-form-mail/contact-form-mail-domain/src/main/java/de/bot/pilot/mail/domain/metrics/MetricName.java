package de.bot.pilot.mail.domain.metrics;

public enum MetricName {
    CONTACT_FORM_SUBMITTED("contact.form.submitted"), CAPTCHA_VERIFICATION_FAILURE(
            "captcha.verification.failure"), MAIL_SENT("mail.sent");

    private final String name;

    public String getName() {
        return this.name;
    }

    MetricName(String name) {
        this.name = name;
    }
}
