package de.bot.pilot.mail.infrastructure.crypto.adapter;

import de.bot.pilot.mail.domain.port.outbound.EncryptionPort;
import de.bot.pilot.mail.infrastructure.crypto.config.VaultTransitProperties;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultOperations;
import org.springframework.vault.support.Ciphertext;
import org.springframework.vault.support.Plaintext;

@Component
public class VaultEncryptionAdapter implements EncryptionPort {

    private final VaultOperations vaultOperations;
    private final String key;
    private final String path;

    public VaultEncryptionAdapter(VaultOperations vaultOperations, VaultTransitProperties vaultTransitProperties) {
        this.vaultOperations = vaultOperations;
        this.key = vaultTransitProperties.key();
        this.path = vaultTransitProperties.path();
    }

    @Override
    public String encrypt(String plainText) {
        Ciphertext result = vaultOperations.opsForTransit(path).encrypt(key, Plaintext.of(plainText));
        return result.getCiphertext();
    }

    @Override
    public String decrypt(String cipherText) {
        Plaintext result = vaultOperations.opsForTransit(path).decrypt(key, Ciphertext.of(cipherText));
        return result.asString();
    }
}
