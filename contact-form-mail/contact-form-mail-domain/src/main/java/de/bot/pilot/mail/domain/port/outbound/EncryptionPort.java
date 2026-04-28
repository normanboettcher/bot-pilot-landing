package de.bot.pilot.mail.domain.port.outbound;

/**
 * Provides encryption and decryption functionalities for secure data handling.
 * This interface defines operations for encrypting plain text to cipher text
 * and decrypting cipher text back to plain text using a configurable encryption algorithm.
 */

public interface EncryptionPort {
    /**
     * Encrypts the plain text using the configured encryption algorithm.
     *
     * @param plainText the text to encrypt
     * @return the encrypted text
     */
    String encrypt(String plainText);

    /**
     * Decrypts the cipher text using the configured decryption algorithm.
     *
     * @param cipherText the text to decrypt
     * @return the decrypted plain text
     */
    String decrypt(String cipherText);
}
