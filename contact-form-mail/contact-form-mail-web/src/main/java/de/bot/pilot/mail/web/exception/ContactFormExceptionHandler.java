package de.bot.pilot.mail.web.exception;

import de.bot.pilot.mail.domain.exception.CaptchaVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ContactFormExceptionHandler {

	@ExceptionHandler(CaptchaVerificationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleCaptchaFailure(CaptchaVerificationException ex) {
		return ex.getMessage();
	}
}
