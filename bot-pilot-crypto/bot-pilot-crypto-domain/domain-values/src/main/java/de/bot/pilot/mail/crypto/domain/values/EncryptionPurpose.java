package de.bot.pilot.mail.crypto.domain.values;

///
/// Represents a purpose for encryption within the system. This is a sealed interface
/// with predefined variants, ensuring controlled usage and type safety for encryption purposes.
///
/// Permitted variants:
/// - {@link EncryptionPurpose.Mail}
/// - {@link EncryptionPurpose.Message}
/// - {@link EncryptionPurpose.Company}
///
///  Key functionality:
///  - Each variant provides a label that uniquely identifies its encryption purpose.
///  - Includes a static method to parse a string label into the corresponding variant.
///
public sealed interface EncryptionPurpose
        permits EncryptionPurpose.Mail, EncryptionPurpose.Message, EncryptionPurpose.Company {

    String label();

    record Mail() implements EncryptionPurpose {
        public String label() {
            return "MAIL_ADDRESS";
        }
    }

    record Message() implements EncryptionPurpose {
        public String label() {
            return "MESSAGE";
        }
    }

    record Company() implements EncryptionPurpose {
        public String label() {
            return "COMPANY";
        }
    }

    /**
     * Parse from HTTP string. Centralised in domain — not scattered in API layer.
     * Throws IllegalArgumentException → caught in controller → VaultError.InvalidInput → 400.
     */
    static EncryptionPurpose fromLabel(String label) {
        return switch (label.toUpperCase().strip()) {
            case "MAIL_ADDRESS" -> new Mail();
            case "MESSAGE" -> new Message();
            case "COMPANY" -> new Company();
            default -> throw new IllegalArgumentException(
                    "Unknown purpose: '%s'. Valid: MAIL, MESSAGE, USER_PII".formatted(label));
        };
    }
}
