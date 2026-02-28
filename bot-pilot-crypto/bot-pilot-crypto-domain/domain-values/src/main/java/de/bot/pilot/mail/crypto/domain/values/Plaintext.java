package de.bot.pilot.mail.crypto.domain.values;


/**
 * Represents a plaintext value to be used for cryptographic operations.
 * This class ensures that plaintext values are neither null nor blank,
 * validating input at construction time.
 * <p>
 * A {@link VaultCryptoException} is thrown if the input validation fails
 * with a {@link VaultError.InvalidInput} containing error details.
 * <p>
 * Validation criteria:
 * - The plaintext value must not be {@code null}.
 * - The plaintext value must not be blank (only whitespace or empty).
 *
 */
public record Plaintext(String value) {
    public Plaintext {
        if (value == null || value.isBlank()) {
            throw new VaultCryptoException(
                    new VaultError
                            .InvalidInput(String
                            .format("Plaintext value cannot be null or blank: ['%s']", value),
                            new IllegalArgumentException("Plaintext must not be blank"))
            );
        }
    }
}
