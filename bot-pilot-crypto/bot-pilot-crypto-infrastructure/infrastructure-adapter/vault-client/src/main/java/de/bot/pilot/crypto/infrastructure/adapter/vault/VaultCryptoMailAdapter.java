package de.bot.pilot.crypto.infrastructure.adapter.vault;

import de.bot.pilot.mail.crypto.domain.values.Ciphertext;
import de.bot.pilot.mail.crypto.domain.values.Plaintext;
import de.bot.pilot.mail.crypto.domain.values.ports.output.CryptoPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class VaultCryptoMailAdapter implements CryptoPort {

    @Override
    public Ciphertext encrypt(Plaintext value) {
        return null;
    }

    @Override
    public Plaintext decrypt(Ciphertext ciphertext) {
        return null;
    }
}
