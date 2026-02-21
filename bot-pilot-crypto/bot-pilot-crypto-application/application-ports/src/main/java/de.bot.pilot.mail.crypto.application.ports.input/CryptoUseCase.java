package de.bot.pilot.mail.crypto.application.ports.input;

import de.bot.pilot.mail.crypto.domain.command.DecryptCommand;
import de.bot.pilot.mail.crypto.domain.command.EncryptCommand;

/**
 * Provides cryptographic operations for encryption and decryption.
 * This interface defines the contract for handling cryptographic processes
 * by taking specialized command objects as input for both encryption
 * and decryption routines.
 */
public interface CryptoUseCase {

    /**
     * Encrypts the given plaintext encapsulated in an {@code EncryptCommand}.
     * The method processes the supplied command to generate an encrypted representation
     * of the raw text value contained within the command.
     *
     * @param command the {@code EncryptCommand} containing the plaintext to be encrypted
     *                and a unique identifier for the encryption request
     * @return a {@code String} representing the encrypted version of the plaintext
     */
    String encrypt(EncryptCommand command);

    /**
     * Decrypts the given ciphertext encapsulated in a {@code DecryptCommand}.
     * The method processes the supplied command to generate the plaintext
     * representation of the encrypted data contained within the command.
     *
     * @param command the {@code DecryptCommand} containing the ciphertext to be decrypted
     *                and a unique identifier for the decryption request
     * @return a {@code String} representing the decrypted plaintext
     */
    String decrypt(DecryptCommand command);
}
