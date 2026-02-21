package de.bot.pilot.crypto.infrastructure.adapter.vault;

import de.bot.pilot.crypto.domain.ports.out.CryptoPort;
import de.bot.pilot.mail.crypto.domain.values.Ciphertext;
import de.bot.pilot.mail.crypto.domain.values.Plaintext;
import org.springframework.stereotype.Component;

@Component
public class VaultCryptoAdapter implements CryptoPort {

    @Override
    public Ciphertext encrypt(Plaintext value) {
        return null;
    }

    @Override
    public Plaintext decrypt(Ciphertext ciphertext) {
        return null;
    }
}
