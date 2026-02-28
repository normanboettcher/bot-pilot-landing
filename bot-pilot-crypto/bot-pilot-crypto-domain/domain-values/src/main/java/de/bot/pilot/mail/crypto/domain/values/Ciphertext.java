package de.bot.pilot.mail.crypto.domain.values;


public record Ciphertext(String value) {
    public Ciphertext {
        if (value == null || value.isBlank()) {
            throw new VaultCryptoException(
                    new VaultError
                            .InvalidInput(
                            String.format("Ciphertext value cannot be null or blank: ['%s']", value),
                            new IllegalArgumentException("Ciphertext must not be blank")
                    )
            );
        }
    }
}
