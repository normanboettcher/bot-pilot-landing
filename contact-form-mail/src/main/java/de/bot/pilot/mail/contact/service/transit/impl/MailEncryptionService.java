package de.bot.pilot.mail.contact.service.transit.impl;

import de.bot.pilot.mail.contact.config.VaultTransitProperties;
import de.bot.pilot.mail.contact.service.transit.api.EncryptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultOperations;

@Service("mailEncryptionService")
@Slf4j
public class MailEncryptionService implements EncryptionService {
    private final VaultTransitProperties vaultTransitProperties;
    private final VaultOperations vaultOperations;
    private final String key;
    private final String path;

    public MailEncryptionService(VaultTransitProperties vaultTransitProperties, VaultOperations vaultOperations) {
        this.vaultTransitProperties = vaultTransitProperties;
        this.vaultOperations = vaultOperations;
        this.key = vaultTransitProperties.getMail().getKey();
        this.path = vaultTransitProperties.getMail().getPath();
    }

    @Override
    public String encrypt(final String value) {
        if (value == null) {
            return "";
        }
        try {
            return vaultOperations.opsForTransform(path).encode(key, value);
        } catch (Exception e) {
            log.error("Failed to encrypt value", e);
            // TODO: handle it with a response record
            throw new RuntimeException(e);
        }
    }
}
