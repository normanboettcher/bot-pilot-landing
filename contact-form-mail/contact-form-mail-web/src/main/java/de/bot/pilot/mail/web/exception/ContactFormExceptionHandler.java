package de.bot.pilot.mail.web.exception;

import de.bot.pilot.mail.domain.error.EncryptionError;
import de.bot.pilot.mail.domain.exception.CaptchaVerificationException;
import de.bot.pilot.mail.domain.exception.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ContactFormExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactFormExceptionHandler.class);
	private static final String SERVICE_UNAVAILABLE_MESSAGE = "Service temporarily unavailable. Please try again later.";

	@ExceptionHandler(CaptchaVerificationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleCaptchaFailure(CaptchaVerificationException ex) {
		return ex.getMessage();
	}

	@ExceptionHandler(EncryptionException.class)
	public ResponseEntity<String> handleEncryptionFailure(EncryptionException ex) {
		return switch (ex.encryptionError()) {
			case EncryptionError.InvalidInput e -> ResponseEntity.badRequest().body(e.detail());
			case EncryptionError.Unauthorized e -> {
				LOGGER.error("Vault auth failed", e.cause());
				yield ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(SERVICE_UNAVAILABLE_MESSAGE);
			}
			case EncryptionError.Unavailable e -> {
				LOGGER.error("Vault unavailable", e.cause());
				yield ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(SERVICE_UNAVAILABLE_MESSAGE);
			}
			case EncryptionError.OperationFailed e -> {
				LOGGER.error("Vault operation failed", e.cause());
				yield ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(SERVICE_UNAVAILABLE_MESSAGE);
			}
			case EncryptionError.Forbidden e -> {
				LOGGER.error("Vault policy denied", e.cause());
				yield ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(SERVICE_UNAVAILABLE_MESSAGE);
			}
		};
	}

}
