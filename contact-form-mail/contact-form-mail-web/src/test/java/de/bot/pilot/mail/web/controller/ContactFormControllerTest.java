package de.bot.pilot.mail.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bot.pilot.mail.application.port.inbound.ContactFormUseCase;
import de.bot.pilot.mail.domain.error.EncryptionError;
import de.bot.pilot.mail.domain.exception.CaptchaVerificationException;
import de.bot.pilot.mail.domain.exception.EncryptionException;
import de.bot.pilot.mail.web.exception.ContactFormExceptionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({ContactFormController.class, ContactFormExceptionHandler.class})
class ContactFormControllerTest {

	private static final String VALID_BODY = """
			{
			  "firstName": "Ada",
			  "lastName": "Lovelace",
			  "email": "ada@example.com",
			  "company": "Acme",
			  "message": "Hello world",
			  "captchaToken": "token-abc"
			}
			""";

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ContactFormUseCase contactFormUseCase;

	@Test
	@DisplayName("Valid request: responds 202 Accepted and delegates to use case exactly once")
	void submit_validRequest_returns202AndDelegatesToUseCase() throws Exception {
		// given
		// use case does nothing (default mock behaviour)

		// when / then
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
				.andExpect(status().isAccepted());
		verify(contactFormUseCase, times(1)).submit(any());
	}

	@Test
	@DisplayName("Blank firstName: Bean Validation rejects request with 400 before use case is called")
	void submit_blankFirstName_returns400WithoutCallingUseCase() throws Exception {
		// given
		String body = """
				{
				  "firstName": "",
				  "lastName": "Lovelace",
				  "email": "ada@example.com",
				  "company": "Acme",
				  "message": "Hello world",
				  "captchaToken": "token-abc"
				}
				""";

		// when / then
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest());
		verify(contactFormUseCase, never()).submit(any());
	}

	@Test
	@DisplayName("Invalid email format: Bean Validation rejects request with 400")
	void submit_invalidEmail_returns400() throws Exception {
		// given
		String body = """
				{
				  "firstName": "Ada",
				  "lastName": "Lovelace",
				  "email": "not-an-email",
				  "company": "Acme",
				  "message": "Hello world",
				  "captchaToken": "token-abc"
				}
				""";

		// when / then
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest());
		verify(contactFormUseCase, never()).submit(any());
	}

	@Test
	@DisplayName("CaptchaVerificationException: handler returns 400 with the exception message in the body")
	void submit_captchaFails_returns400WithMessage() throws Exception {
		// given
		doThrow(new CaptchaVerificationException("Captcha verification failed")).when(contactFormUseCase).submit(any());

		// when / then
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
				.andExpect(status().isBadRequest()).andExpect(content().string("Captcha verification failed"));
	}

	@Test
	@DisplayName("EncryptionException(InvalidInput): handler returns 400 with the detail in the body")
	void submit_encryptionInvalidInput_returns400WithDetail() throws Exception {
		// given
		var error = new EncryptionError.InvalidInput("plainText", "Plaintext must not be blank");
		doThrow(new EncryptionException(error)).when(contactFormUseCase).submit(any());

		// when / then
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
				.andExpect(status().isBadRequest()).andExpect(content().string("Plaintext must not be blank"));
	}

	@Test
	@DisplayName("EncryptionException(Unauthorized): handler returns 503")
	void submit_encryptionUnauthorized_returns503() throws Exception {
		// given
		var error = new EncryptionError.Unauthorized("Vault auth failed", new RuntimeException("token expired"));
		doThrow(new EncryptionException(error)).when(contactFormUseCase).submit(any());

		// when / then
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
				.andExpect(status().isServiceUnavailable());
	}

	@Test
	@DisplayName("EncryptionException(Unavailable): handler returns 503")
	void submit_encryptionUnavailable_returns503() throws Exception {
		// given
		var error = new EncryptionError.Unavailable("Cannot reach Vault", new RuntimeException("connect refused"));
		doThrow(new EncryptionException(error)).when(contactFormUseCase).submit(any());

		// when / then
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
				.andExpect(status().isServiceUnavailable());
	}

	@Test
	@DisplayName("EncryptionException(OperationFailed): handler returns 500")
	void submit_encryptionOperationFailed_returns500() throws Exception {
		// given
		var error = new EncryptionError.OperationFailed("Transit encrypt returned null", null);
		doThrow(new EncryptionException(error)).when(contactFormUseCase).submit(any());

		// when / then
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
				.andExpect(status().isInternalServerError());
	}

	@Test
	@DisplayName("EncryptionException(Forbidden): handler returns 503")
	void submit_encryptionForbidden_returns503() throws Exception {
		// given
		var error = new EncryptionError.Forbidden("Vault policy denied", new RuntimeException("403"));
		doThrow(new EncryptionException(error)).when(contactFormUseCase).submit(any());

		// when / then
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
				.andExpect(status().isServiceUnavailable());
	}
}
