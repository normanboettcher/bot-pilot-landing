package de.bot.pilot.mail.domain.exception;

public final class CaptchaVerificationException extends RuntimeException {

    public CaptchaVerificationException(String message) {
        super(message);
    }

    public CaptchaVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
