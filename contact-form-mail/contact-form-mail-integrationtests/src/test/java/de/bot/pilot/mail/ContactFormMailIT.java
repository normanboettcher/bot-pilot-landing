package de.bot.pilot.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.bot.pilot.mail.domain.exception.CaptchaVerificationException;
import de.bot.pilot.mail.domain.port.outbound.CaptchaPort;
import de.bot.pilot.mail.infrastructure.persistence.entity.CustomerPdo;
import de.bot.pilot.mail.infrastructure.persistence.entity.EmailRequestPdo;
import de.bot.pilot.mail.infrastructure.persistence.repository.CustomerJpaRepository;
import de.bot.pilot.mail.infrastructure.persistence.repository.EmailRequestJpaRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.Ciphertext;
import org.springframework.vault.support.Plaintext;

@SpringBootTest(classes = ContactFormMailApplication.class)
@AutoConfigureMockMvc
class ContactFormMailIT {

	private static final String TRANSIT_PATH = "path";
	private static final String TRANSIT_KEY = "key";

	private static final String VALID_REQUEST_BODY = """
			{
			  "firstName": "Ada",
			  "lastName": "Lovelace",
			  "email": "ada@example.com",
			  "company": "Acme Corp",
			  "message": "Hello, I would like to get in touch.",
			  "captchaToken": "test-token"
			}
			""";

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CustomerJpaRepository customerRepository;

	@Autowired
	private EmailRequestJpaRepository emailRequestRepository;

	@Autowired
	private VaultOperations vaultOperations;

	@Autowired
	private CaptchaPort captchaPort;

	@BeforeEach
	void setUp() {
		emailRequestRepository.deleteAll();
		customerRepository.deleteAll();
		reset(vaultOperations, captchaPort);
	}

	@Test
	@DisplayName("Happy path: 202 Accepted, encrypted values persisted, mail failure recorded as success=false")
	void submit_validRequest_persistsEncryptedDataAndReturnsWith202() throws Exception {
		// given
		// Encrypt order inside ContactFormService: message, subject, company, email,
		// firstName, lastName
		VaultTransitOperations transitOps = stubTransitEncryption();
		when(transitOps.encrypt(eq(TRANSIT_KEY), any(Plaintext.class))).thenReturn(
				Ciphertext.of("vault:v1:enc:message"), Ciphertext.of("vault:v1:enc:subject"),
				Ciphertext.of("vault:v1:enc:company"), Ciphertext.of("vault:v1:enc:email"),
				Ciphertext.of("vault:v1:enc:firstName"), Ciphertext.of("vault:v1:enc:lastName"));

		// when
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(VALID_REQUEST_BODY))
				.andExpect(status().isAccepted());

		// then
		List<CustomerPdo> customers = customerRepository.findAll();
		assertThat(customers).hasSize(1);
		CustomerPdo customer = customers.getFirst();
		assertThat(customer.getFirstName()).isEqualTo("vault:v1:enc:firstName");
		assertThat(customer.getLastName()).isEqualTo("vault:v1:enc:lastName");
		assertThat(customer.getEmail()).isEqualTo("vault:v1:enc:email");
		assertThat(customer.getCompany()).isEqualTo("vault:v1:enc:company");

		List<EmailRequestPdo> emailRequests = emailRequestRepository.findAll();
		assertThat(emailRequests).hasSize(1);
		EmailRequestPdo emailRequest = emailRequests.getFirst();
		assertThat(emailRequest.getContent()).isEqualTo("vault:v1:enc:message");
		assertThat(emailRequest.getSubject()).isEqualTo("vault:v1:enc:subject");
		// No SMTP server on port 25 — MailDeliveryException is swallowed by
		// trySendMail(), recorded as false
		assertThat(emailRequest.isSuccess()).isFalse();
	}

	@Test
	@DisplayName("No plaintext in DB: raw input values do not appear in any persisted column")
	void submit_validRequest_noPlaintextLeaksIntoDb() throws Exception {
		// given
		VaultTransitOperations transitOps = stubTransitEncryption();
		when(transitOps.encrypt(eq(TRANSIT_KEY), any(Plaintext.class))).thenReturn(
				Ciphertext.of("vault:v1:enc:message"), Ciphertext.of("vault:v1:enc:subject"),
				Ciphertext.of("vault:v1:enc:company"), Ciphertext.of("vault:v1:enc:email"),
				Ciphertext.of("vault:v1:enc:firstName"), Ciphertext.of("vault:v1:enc:lastName"));

		// when
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(VALID_REQUEST_BODY))
				.andExpect(status().isAccepted());

		// then
		CustomerPdo customer = customerRepository.findAll().getFirst();
		assertThat(customer.getFirstName()).doesNotContain("Ada");
		assertThat(customer.getLastName()).doesNotContain("Lovelace");
		assertThat(customer.getEmail()).doesNotContain("ada@example.com");
		assertThat(customer.getCompany()).doesNotContain("Acme Corp");

		EmailRequestPdo emailRequest = emailRequestRepository.findAll().getFirst();
		assertThat(emailRequest.getContent()).doesNotContain("Hello, I would like to get in touch.");
		assertThat(emailRequest.getSubject()).doesNotContain("Acme Corp");
	}

	@Test
	@DisplayName("Captcha fails: 400 Bad Request returned and DB remains empty")
	void submit_captchaFails_returns400AndNothingPersisted() throws Exception {
		// given
		doThrow(new CaptchaVerificationException("Cloudflare Turnstile verification failed")).when(captchaPort)
				.verify(any(), any());

		// when / then
		mockMvc.perform(post("/contact").contentType(MediaType.APPLICATION_JSON).content(VALID_REQUEST_BODY))
				.andExpect(status().isBadRequest());

		assertThat(customerRepository.count()).isZero();
		assertThat(emailRequestRepository.count()).isZero();
	}

	private VaultTransitOperations stubTransitEncryption() {
		VaultTransitOperations transitOps = Mockito.mock(VaultTransitOperations.class);
		when(vaultOperations.opsForTransit(TRANSIT_PATH)).thenReturn(transitOps);
		return transitOps;
	}
}
