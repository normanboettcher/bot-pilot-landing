package de.bot.pilot.mail.crypto.domain.command;

import de.bot.pilot.mail.crypto.domain.values.EncryptionPurpose;

/**
 * Represents a command to initiate the decryption process of a given ciphertext.
 * This command encapsulates the encrypted text and a unique identifier associated
 * with the decryption request.
 *
 * @param rawCiphertext the encrypted text that needs to be decrypted
 * @param requestId     the unique identifier for tracking the decryption request
 */
public record DecryptCommand(String rawCiphertext, String requestId,
                             EncryptionPurpose purpose) {
}
