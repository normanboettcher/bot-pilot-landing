package de.bot.pilot.crypto.domain.ports.out;

import de.bot.pilot.mail.crypto.domain.values.Ciphertext;
import de.bot.pilot.mail.crypto.domain.values.Plaintext;
import de.bot.pilot.mail.crypto.domain.values.TransitKey;
import de.bot.pilot.mail.crypto.domain.values.VaultCryptoException;

/**
 * Represents a cryptographic interface for performing encryption and decryption
 * operations. The {@code CryptoPort} ensures secure handling and transformation
 * of sensitive data, such as plaintext and ciphertext, during cryptographic processes.
 * All implementations of this interface must support robust error handling to
 * address invalid inputs and cryptographic failures.
 * <p>
 * Encryption and decryption processes follow strict input validation rules:
 * - Input parameters must not be null or blank.
 * - Any failure during cryptographic operations will result in a {@link VaultCryptoException}
 * containing detailed error information encapsulated in specific
 * {@link de.bot.pilot.mail.crypto.domain.values.VaultError} instances.
 */
public interface CryptoPort {

    /**
     * Encrypts the given plaintext value and returns the result as a {@link Ciphertext}.
     * This method performs cryptographic operations to securely transform the input,
     * ensuring sensitive data is protected in storage or transmission.
     *
     * @param value the plaintext value to be encrypted; must not be {@code null} or blank.
     *              If the input is invalid, a {@link VaultCryptoException} will be thrown
     *              containing a {@link de.bot.pilot.mail.crypto.domain.values.VaultError.EncryptionFailed}
     * @return an instance of {@link Ciphertext} encapsulating the encrypted data.
     */
    Ciphertext encrypt(Plaintext value, TransitKey transitKey);

    /**
     * Decrypts the provided {@link Ciphertext} and returns the resulting {@link Plaintext}.
     * This method securely reverses the encryption process, restoring the original plaintext.
     * <p>
     * Note:
     * - The input {@link Ciphertext} must not be null or blank.
     * - If the decryption operation encounters an error (e.g., invalid ciphertext format,
     * decryption engine failure), a {@link VaultCryptoException} will be thrown containing
     * a {@link de.bot.pilot.mail.crypto.domain.values.VaultError.DecryptionFailed}.
     *
     * @param ciphertext the encrypted data to be decrypted; must not be null or blank.
     * @return the resulting plaintext extracted from the provided ciphertext.
     * @throws VaultCryptoException if decryption fails or the input is invalid.
     */
    Plaintext decrypt(Ciphertext ciphertext, TransitKey transitKey);
}
