package de.bot.pilot.crypto.domain.error;

/**
 * Represents a sealed interface for modeling various types of errors that can occur
 * within a vault system. Each error type provides details and an optional cause
 * to describe the nature and origin of the error.
 */
public sealed interface VaultError permits
        VaultError.AuthFailed,
        VaultError.PermissionDenied,
        VaultError.Unavailable,
        VaultError.EncryptionFailed,
        VaultError.DecryptionFailed,
        VaultError.SecretNotFound,
        VaultError.InvalidInput {

    /**
     * Provides a detailed description of the vault error.
     * Human-readable.
     *
     * @return a string representing the descriptive detail of the error.
     */
    String detail();

    /**
     * Retrieves the underlying cause of the vault error, if available.
     * <p>
     * For logging purposes only. Never for expiration to the client.
     *
     * @return the {@link Throwable} instance representing the root cause of the error,
     * or {@code null} if no cause is provided or applicable.
     */
    Throwable cause();

    // -- Permitted Variants

    /**
     * Token expired, AppRole secret invalid, renewal failed. → HTTP 401
     */
    record AuthFailed(String detail, Throwable cause) implements VaultError {
    }

    /**
     * Vault policy denies the requested operation on this path. → HTTP 403
     */
    record PermissionDenied(String detail, Throwable cause) implements VaultError {
    }

    /**
     * Vault sealed, connection refused, network timeout. → HTTP 503
     */
    record Unavailable(String detail, Throwable cause) implements VaultError {
    }

    /**
     * Transit engine encrypt call failed unexpectedly. → HTTP 500
     */
    record EncryptionFailed(String detail, Throwable cause) implements VaultError {
    }

    /**
     * Transit engine decrypt call failed unexpectedly. → HTTP 500
     */
    record DecryptionFailed(String detail, Throwable cause) implements VaultError {
    }

    /**
     * Requested Vault path does not exist. → HTTP 404
     */
    record SecretNotFound(String detail, Throwable cause) implements VaultError {
    }

    /**
     * Client sent bad input (blank plaintext, malformed ciphertext). → HTTP 400
     */
    record InvalidInput(String detail, Throwable cause) implements VaultError {

        @Override
        public Throwable cause() {
            // no need - it is a client mistake
            return null;
        }
    }

}
