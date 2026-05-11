package de.bot.pilot.mail.infrastructure.crypto.adapter;

import de.bot.pilot.mail.domain.error.EncryptionError;
import de.bot.pilot.mail.domain.exception.EncryptionException;
import de.bot.pilot.mail.domain.port.outbound.EncryptionPort;
import de.bot.pilot.mail.infrastructure.crypto.config.VaultTransitProperties;
import org.springframework.stereotype.Component;
import org.springframework.vault.VaultException;
import org.springframework.vault.authentication.VaultLoginException;
import org.springframework.vault.authentication.VaultTokenRenewalException;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.Ciphertext;
import org.springframework.vault.support.Plaintext;
import org.springframework.web.client.ResourceAccessException;

@Component
public class VaultEncryptionAdapter implements EncryptionPort {

	private final VaultOperations vaultOperations;
	private final String key;
	private final String path;

	public VaultEncryptionAdapter(VaultOperations vaultOperations, VaultTransitProperties vaultTransitProperties) {
		this.vaultOperations = vaultOperations;
		this.key = vaultTransitProperties.key();
		this.path = vaultTransitProperties.path();
	}

	@Override
	public String encrypt(String plainText) {
		if (plainText == null || plainText.isBlank()) {
			throw new EncryptionException(new EncryptionError.InvalidInput("plainText", "Plaintext must not be blank"));
		}
		try {
			Ciphertext result = vaultOperations.opsForTransit(path).encrypt(key, Plaintext.of(plainText));
			if (result == null || result.getCiphertext() == null) {
				// No upstream exception - Vault returned null; cause is intentionally absent
				throw new EncryptionException(
						new EncryptionError.OperationFailed("Transit encrypt returned null", null));
			}
			return result.getCiphertext();
		} catch (VaultLoginException | VaultTokenRenewalException ex) {
			throw new EncryptionException(new EncryptionError.Unauthorized("Vault auth failed", ex));
		} catch (VaultException e) {
			String msg = e.getMessage() != null ? e.getMessage() : "";
			if (msg.contains("403")) {
				throw new EncryptionException(new EncryptionError.Forbidden("Vault policy denied", e));
			}
			throw new EncryptionException(new EncryptionError.OperationFailed("Transit encrypt failed", e));
		} catch (ResourceAccessException e) {
			throw new EncryptionException(new EncryptionError.Unavailable("Cannot access Vault", e));
		}
	}
}
