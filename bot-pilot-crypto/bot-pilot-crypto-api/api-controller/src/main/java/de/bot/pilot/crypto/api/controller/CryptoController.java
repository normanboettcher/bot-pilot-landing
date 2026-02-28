package de.bot.pilot.crypto.api.controller;


import de.bot.pilot.crypto.api.dto.CryptoRequest;
import de.bot.pilot.crypto.api.dto.CryptoResponse;
import de.bot.pilot.crypto.api.filter.RequestIdFilter;
import de.bot.pilot.mail.crypto.application.ports.input.CryptoUseCase;
import de.bot.pilot.mail.crypto.domain.command.DecryptCommand;
import de.bot.pilot.mail.crypto.domain.command.EncryptCommand;
import de.bot.pilot.mail.crypto.domain.values.EncryptionPurpose;
import de.bot.pilot.mail.crypto.domain.values.VaultCryptoException;
import de.bot.pilot.mail.crypto.domain.values.VaultError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * CryptoController v2 — maps purpose string → EncryptionPurpose.
 * <p>
 * SINGLE CHANGE FROM v1:
 * parsePurpose() converts the DTO string to a typed domain object.
 * IllegalArgumentException from EncryptionPurpose.fromLabel() is caught
 * here and re-thrown as VaultCryptoException(InvalidInput) so the
 * GlobalExceptionHandler returns RFC 9457 400 — not a 500 from an
 * unhandled exception.
 * <p>
 * NOTE: The @Pattern validation in CryptoRequest already rejects unknown
 * purpose strings before they reach this method. parsePurpose() is a
 * defensive second layer — Belt AND suspenders.
 */
@RestController
@RequestMapping("/api/v1/crypto")
public class CryptoController {

    private final CryptoUseCase cryptoUseCase;

    public CryptoController(CryptoUseCase cryptoUseCase) {
        this.cryptoUseCase = cryptoUseCase;
    }

    @PostMapping("/encrypt")
    public ResponseEntity<CryptoResponse> encrypt(
            @Valid @RequestBody CryptoRequest request,
            HttpServletRequest servletRequest) {

        var requestId = requestId(servletRequest);
        var purpose = parsePurpose(request.purpose());
        var result = cryptoUseCase.encrypt(new EncryptCommand(request.value(), requestId, purpose));
        return ResponseEntity.ok(new CryptoResponse(result, requestId));
    }

    @PostMapping("/decrypt")
    public ResponseEntity<CryptoResponse> decrypt(
            @Valid @RequestBody CryptoRequest request,
            HttpServletRequest servletRequest) {

        var requestId = requestId(servletRequest);
        var purpose = parsePurpose(request.purpose());
        var result = cryptoUseCase.decrypt(new DecryptCommand(request.value(), requestId, purpose));
        return ResponseEntity.ok(new CryptoResponse(result, requestId));
    }

    /**
     * Convert purpose string to typed EncryptionPurpose.
     * Wraps IllegalArgumentException → VaultCryptoException so the
     * GlobalExceptionHandler always sees a typed domain exception.
     */
    private EncryptionPurpose parsePurpose(String purpose) {
        try {
            return EncryptionPurpose.fromLabel(purpose);
        } catch (IllegalArgumentException ex) {
            throw new VaultCryptoException(
                    new VaultError.InvalidInput(ex.getMessage(), ex));
        }
    }

    private String requestId(HttpServletRequest req) {
        return (String) req.getAttribute(RequestIdFilter.ATTR_REQUEST_ID);
    }
}
