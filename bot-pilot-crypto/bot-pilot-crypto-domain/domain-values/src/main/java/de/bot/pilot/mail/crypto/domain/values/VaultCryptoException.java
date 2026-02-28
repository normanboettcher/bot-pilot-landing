package de.bot.pilot.mail.crypto.domain.values;

/**
 * Represents an exception specific to cryptographic operations within a vault system.
 * This exception serves as a specialized wrapper for various types of {@link VaultError}
 * instances that describe the underlying cause of the failure.
 * <p>
 * This exception is immutable and is intended to provide relevant error details
 * along with the optional underlying cause for debugging and operational logging.
 */
public final class VaultCryptoException extends RuntimeException {
    private final VaultError vaultError;

    public VaultCryptoException(VaultError vaultError) {
        super(vaultError.detail(), vaultError.cause());
        this.vaultError = vaultError;
    }

    public VaultError error() {
        return vaultError;
    }
}
