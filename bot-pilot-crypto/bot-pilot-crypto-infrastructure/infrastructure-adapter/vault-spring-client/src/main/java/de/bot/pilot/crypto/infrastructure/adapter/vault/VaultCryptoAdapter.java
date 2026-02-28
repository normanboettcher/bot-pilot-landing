package de.bot.pilot.crypto.infrastructure.adapter.vault;

import de.bot.pilot.crypto.domain.ports.out.CryptoPort;
import de.bot.pilot.crypto.infrastructure.adapter.vault.mapper.VaultErrorMapper;
import de.bot.pilot.mail.crypto.domain.values.Ciphertext;
import de.bot.pilot.mail.crypto.domain.values.Plaintext;
import de.bot.pilot.mail.crypto.domain.values.TransitKey;
import de.bot.pilot.mail.crypto.domain.values.VaultCryptoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.vault.core.VaultTemplate;

import java.util.Base64;
import java.util.Map;

/**
 * VaultCryptoAdapter v2 — uses TransitKey parameter, no hardwired @Value.
 * <p>
 * CHANGE FROM v1:
 * v1: @Value("${vault.transit.key-name}") String keyName — one global key
 * v2: TransitKey transitKey parameter — resolved per-call by application layer
 * <p>
 * WHY THIS CLASS GETS SIMPLER IN v2:
 * The adapter no longer decides which key to use. It receives a fully-resolved
 * TransitKey and executes the Vault call with that key. "Tell, don't ask."
 * <p>
 * This also means VaultCryptoAdapter has NO dependency on TransitKeyRegistry.
 * Two infrastructure modules that were previously coupled are now independent.
 * <p>
 * MOUNT PATH HANDLING:
 * Spring Vault's opsForTransit() uses the default "transit" mount.
 * For custom mount paths (e.g. "transit/mail") we use the lower-level
 * VaultOperations.write() directly, constructing the path from TransitKey.
 */
@Component
public class VaultCryptoAdapter implements CryptoPort {
    private static final Logger log = LoggerFactory.getLogger(VaultCryptoAdapter.class);

    private final VaultTemplate vaultTemplate;
    private final VaultErrorMapper errorMapper;

    public VaultCryptoAdapter(VaultTemplate vaultTemplate, VaultErrorMapper errorMapper) {
        this.vaultTemplate = vaultTemplate;
        this.errorMapper = errorMapper;
    }

    @Override
    public Ciphertext encrypt(Plaintext plaintext, TransitKey transitKey) {
        try {
            // Use the TransitKey to build the full Vault API path
            var response = vaultTemplate.write(
                    transitKey.encryptPath(),    // e.g. "transit/mail/encrypt/mail-content-key"
                    Map.of("plaintext", base64(plaintext.value())));

            var ciphertext = (String) response.getRequiredData().get("ciphertext");
            log.info("vault.encrypt.success mount={} key={}",
                    transitKey.mountPath(), transitKey.keyName());
            return new Ciphertext(ciphertext);

        } catch (Exception ex) {
            throw new VaultCryptoException(
                    errorMapper.forEncrypt(ex, transitKey.keyName()));
        }
    }

    @Override
    public Plaintext decrypt(Ciphertext ciphertext, TransitKey transitKey) {
        try {
            var response = vaultTemplate.write(
                    transitKey.decryptPath(),    // e.g. "transit/mail/decrypt/mail-content-key"
                    Map.of("ciphertext", ciphertext.value()));

            var plaintext64 = (String) response.getRequiredData().get("plaintext");
            var plaintext = new String(Base64.getDecoder().decode(plaintext64));
            log.info("vault.decrypt.success mount={} key={}",
                    transitKey.mountPath(), transitKey.keyName());
            return new Plaintext(plaintext);

        } catch (Exception ex) {
            throw new VaultCryptoException(
                    errorMapper.forDecrypt(ex, transitKey.keyName()));
        }
    }

    private String base64(String plaintext) {
        return new String(java.util.Base64.getEncoder().encode(plaintext.getBytes()));
    }
}
