package de.bot.pilot.mail.domain.exception;

import de.bot.pilot.mail.domain.error.EncryptionError;

/**
 * Represents an exception that is thrown when an encryption-related error
 * occurs within the system.
 */
public final class EncryptionException extends RuntimeException {
	private final EncryptionError encryptionError;

	public EncryptionException(EncryptionError encryptionError) {
		super(encryptionError.detail(),
				encryptionError instanceof EncryptionError.InfrastructureError infra ? infra.cause() : null);
		this.encryptionError = encryptionError;
	}

	public EncryptionError encryptionError() {
		return encryptionError;
	}
}
