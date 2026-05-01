package de.bot.pilot.mail.infrastructure.crypto.adapter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import de.bot.pilot.mail.domain.error.EncryptionError;
import de.bot.pilot.mail.domain.exception.EncryptionException;
import de.bot.pilot.mail.infrastructure.crypto.config.VaultTransitProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.vault.VaultException;
import org.springframework.vault.authentication.VaultLoginException;
import org.springframework.vault.authentication.VaultTokenRenewalException;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.core.VaultTransitOperations;
import org.springframework.vault.support.Ciphertext;
import org.springframework.vault.support.Plaintext;
import org.springframework.web.client.ResourceAccessException;

@ExtendWith(MockitoExtension.class)
class VaultEncryptionAdapterTest {

	private static final String KEY = "my-key";
	private static final String PATH = "transit";
	private static final String PLAIN_TEXT = "hello";

	@Mock
	private VaultOperations vaultOperations;

	@Mock
	private VaultTransitOperations vaultTransitOperations;

	private VaultEncryptionAdapter adapter;

	@BeforeEach
	void setUp() {
		VaultTransitProperties properties = new VaultTransitProperties(KEY, PATH);
		adapter = new VaultEncryptionAdapter(vaultOperations, properties);
	}

	@Test
	@DisplayName("Happy path: returns ciphertext string from Vault Transit")
	void encrypt_validInput_returnsCiphertext() {
		// given
		Ciphertext ciphertext = Ciphertext.of("vault:v1:abc123==");
		when(vaultOperations.opsForTransit(PATH)).thenReturn(vaultTransitOperations);
		when(vaultTransitOperations.encrypt(eq(KEY), any(Plaintext.class))).thenReturn(ciphertext);

		// when
		String result = adapter.encrypt(PLAIN_TEXT);

		// then
		assertThat(result).isEqualTo("vault:v1:abc123==");
	}

	@Test
	@DisplayName("Blank input: throws EncryptionException with InvalidInput error before any Vault call")
	void encrypt_blankInput_throwsInvalidInput() {
		// given
		String blankInput = "   ";

		// when / then
		assertThatThrownBy(() -> adapter.encrypt(blankInput)).isInstanceOf(EncryptionException.class).satisfies(ex -> {
			EncryptionError error = ((EncryptionException) ex).encryptionError();
			assertThat(error).isInstanceOf(EncryptionError.InvalidInput.class);
			assertThat(((EncryptionError.InvalidInput) error).field()).isEqualTo("plainText");
		});
	}

	@Test
	@DisplayName("Null input: throws EncryptionException with InvalidInput error before any Vault call")
	void encrypt_nullInput_throwsInvalidInput() {
		// given
		String nullInput = null;

		// when / then
		assertThatThrownBy(() -> adapter.encrypt(nullInput)).isInstanceOf(EncryptionException.class).satisfies(ex -> {
			EncryptionError error = ((EncryptionException) ex).encryptionError();
			assertThat(error).isInstanceOf(EncryptionError.InvalidInput.class);
		});
	}

	@Test
	@DisplayName("VaultLoginException: wraps as EncryptionError.Unauthorized")
	void encrypt_vaultLoginException_wrapsAsUnauthorized() {
		// given
		VaultLoginException cause = new VaultLoginException("Login failed");
		when(vaultOperations.opsForTransit(PATH)).thenReturn(vaultTransitOperations);
		when(vaultTransitOperations.encrypt(eq(KEY), any(Plaintext.class))).thenThrow(cause);

		// when / then
		assertThatThrownBy(() -> adapter.encrypt(PLAIN_TEXT)).isInstanceOf(EncryptionException.class).satisfies(ex -> {
			EncryptionError error = ((EncryptionException) ex).encryptionError();
			assertThat(error).isInstanceOf(EncryptionError.Unauthorized.class);
			assertThat(((EncryptionError.Unauthorized) error).cause()).isSameAs(cause);
		});
	}

	@Test
	@DisplayName("VaultTokenRenewalException: wraps as EncryptionError.Unauthorized")
	void encrypt_vaultTokenRenewalException_wrapsAsUnauthorized() {
		// given
		VaultTokenRenewalException cause = new VaultTokenRenewalException("Token renewal failed");
		when(vaultOperations.opsForTransit(PATH)).thenReturn(vaultTransitOperations);
		when(vaultTransitOperations.encrypt(eq(KEY), any(Plaintext.class))).thenThrow(cause);

		// when / then
		assertThatThrownBy(() -> adapter.encrypt(PLAIN_TEXT)).isInstanceOf(EncryptionException.class).satisfies(ex -> {
			EncryptionError error = ((EncryptionException) ex).encryptionError();
			assertThat(error).isInstanceOf(EncryptionError.Unauthorized.class);
			assertThat(((EncryptionError.Unauthorized) error).cause()).isSameAs(cause);
		});
	}

	@Test
	@DisplayName("VaultException with '403' in message: wraps as EncryptionError.Forbidden")
	void encrypt_vaultException403_wrapsAsForbidden() {
		// given
		VaultException cause = new VaultException("status 403 policy denied");
		when(vaultOperations.opsForTransit(PATH)).thenReturn(vaultTransitOperations);
		when(vaultTransitOperations.encrypt(eq(KEY), any(Plaintext.class))).thenThrow(cause);

		// when / then
		assertThatThrownBy(() -> adapter.encrypt(PLAIN_TEXT)).isInstanceOf(EncryptionException.class).satisfies(ex -> {
			EncryptionError error = ((EncryptionException) ex).encryptionError();
			assertThat(error).isInstanceOf(EncryptionError.Forbidden.class);
			assertThat(((EncryptionError.Forbidden) error).cause()).isSameAs(cause);
		});
	}

	@Test
	@DisplayName("VaultException without '403': wraps as EncryptionError.OperationFailed")
	void encrypt_vaultExceptionNon403_wrapsAsOperationFailed() {
		// given
		VaultException cause = new VaultException("key not found");
		when(vaultOperations.opsForTransit(PATH)).thenReturn(vaultTransitOperations);
		when(vaultTransitOperations.encrypt(eq(KEY), any(Plaintext.class))).thenThrow(cause);

		// when / then
		assertThatThrownBy(() -> adapter.encrypt(PLAIN_TEXT)).isInstanceOf(EncryptionException.class).satisfies(ex -> {
			EncryptionError error = ((EncryptionException) ex).encryptionError();
			assertThat(error).isInstanceOf(EncryptionError.OperationFailed.class);
			assertThat(((EncryptionError.OperationFailed) error).cause()).isSameAs(cause);
		});
	}

	@Test
	@DisplayName("ResourceAccessException: wraps as EncryptionError.Unavailable")
	void encrypt_resourceAccessException_wrapsAsUnavailable() {
		// given
		ResourceAccessException cause = new ResourceAccessException("Connection refused");
		when(vaultOperations.opsForTransit(PATH)).thenReturn(vaultTransitOperations);
		when(vaultTransitOperations.encrypt(eq(KEY), any(Plaintext.class))).thenThrow(cause);

		// when / then
		assertThatThrownBy(() -> adapter.encrypt(PLAIN_TEXT)).isInstanceOf(EncryptionException.class).satisfies(ex -> {
			EncryptionError error = ((EncryptionException) ex).encryptionError();
			assertThat(error).isInstanceOf(EncryptionError.Unavailable.class);
			assertThat(((EncryptionError.Unavailable) error).cause()).isSameAs(cause);
		});
	}

	@Test
	@DisplayName("Transit returns null Ciphertext: wraps as EncryptionError.OperationFailed with null cause")
	void encrypt_nullCiphertextResult_wrapsAsOperationFailed() {
		// given
		when(vaultOperations.opsForTransit(PATH)).thenReturn(vaultTransitOperations);
		when(vaultTransitOperations.encrypt(eq(KEY), any(Plaintext.class))).thenReturn(null);

		// when / then
		assertThatThrownBy(() -> adapter.encrypt(PLAIN_TEXT)).isInstanceOf(EncryptionException.class).satisfies(ex -> {
			EncryptionError error = ((EncryptionException) ex).encryptionError();
			assertThat(error).isInstanceOf(EncryptionError.OperationFailed.class);
			assertThat(((EncryptionError.OperationFailed) error).cause()).isNull();
		});
	}
}
