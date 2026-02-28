package de.bot.pilot.mail.crypto.domain.command;

import de.bot.pilot.mail.crypto.domain.values.EncryptionPurpose;

/**
 * Represents a command to initiate the encryption process of a given plaintext.
 * This command encapsulates the raw text to be encrypted and a unique identifier
 * associated with the encryption request.
 *
 * @param rawValue  the plaintext that needs to be encrypted
 * @param requestId the unique identifier for tracking the encryption request
 */
public record EncryptCommand(String rawValue, String requestId,
                             EncryptionPurpose purpose) {

}
