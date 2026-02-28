package de.bot.pilot.mail.crypto.application.service;

import de.bot.pilot.mail.crypto.domain.values.VaultCryptoException;
import de.bot.pilot.mail.crypto.domain.values.VaultError;
import de.bot.pilot.crypto.domain.ports.out.CryptoPort;
import de.bot.pilot.crypto.domain.transit.registry.TransitKeyRegistry;
import de.bot.pilot.mail.crypto.application.ports.input.CryptoUseCase;
import de.bot.pilot.mail.crypto.domain.command.DecryptCommand;
import de.bot.pilot.mail.crypto.domain.command.EncryptCommand;
import de.bot.pilot.mail.crypto.domain.values.Ciphertext;
import de.bot.pilot.mail.crypto.domain.values.EncryptionPurpose;
import de.bot.pilot.mail.crypto.domain.values.Plaintext;
import de.bot.pilot.mail.crypto.domain.values.TransitKey;
import org.springframework.stereotype.Service;

@Service
public class CryptoService implements CryptoUseCase {

    private final CryptoPort cryptoPort;
    private final TransitKeyRegistry transitKeyRegistry;

    public CryptoService(CryptoPort cryptoPort, TransitKeyRegistry transitKeyRegistry) {
        this.cryptoPort = cryptoPort;
        this.transitKeyRegistry = transitKeyRegistry;
    }

    @Override
    public String encrypt(EncryptCommand command) {
        var plaintext = new Plaintext(command.rawValue());
        var transitKey = resolveKey(command.purpose());
        return cryptoPort.encrypt(plaintext).value();
    }

    @Override
    public String decrypt(DecryptCommand command) {
        var ciphertext = new Ciphertext(command.rawCiphertext());
        return cryptoPort.decrypt(ciphertext).value();
    }

    private TransitKey resolveKey(EncryptionPurpose purpose) {
        return transitKeyRegistry.resolve(purpose)
                .orElseThrow(() -> new VaultCryptoException(
                        new VaultError.UnknownPurpose(purpose)));
    }
}
