package de.bot.pilot.mail.crypto.application.service;

import de.bot.pilot.crypto.domain.ports.out.CryptoPort;
import de.bot.pilot.mail.crypto.application.ports.input.CryptoUseCase;
import de.bot.pilot.mail.crypto.domain.command.DecryptCommand;
import de.bot.pilot.mail.crypto.domain.command.EncryptCommand;
import de.bot.pilot.mail.crypto.domain.values.Ciphertext;
import de.bot.pilot.mail.crypto.domain.values.Plaintext;
import org.springframework.stereotype.Service;

@Service
public class CryptoService implements CryptoUseCase {

    private final CryptoPort cryptoPort;

    public CryptoService(CryptoPort cryptoPort) {
        this.cryptoPort = cryptoPort;
    }

    @Override
    public String encrypt(EncryptCommand command) {
        var plaintext = new Plaintext(command.rawValue());
        return cryptoPort.encrypt(plaintext).value();
    }

    @Override
    public String decrypt(DecryptCommand command) {
        var ciphertext = new Ciphertext(command.rawCiphertext());
        return cryptoPort.decrypt(ciphertext).value();
    }

}
