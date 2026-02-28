package de.bot.pilot.crypto.infrastructure.adapter.vault.mapper;

import de.bot.pilot.mail.crypto.domain.values.VaultError;
import org.springframework.stereotype.Component;
import org.springframework.vault.authentication.VaultLoginException;
import org.springframework.vault.VaultException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

/**
 * Anti-Corruption Layer translator — Spring Vault exceptions → VaultError variants.
 * <p>
 * WHY A SEPARATE COMPONENT (not inlined in adapter):
 * Single Responsibility: this class only does one thing — translate.
 * It is independently testable with plain JUnit — no Spring context, no Vault.
 * Feed it exceptions, assert the VaultError variant returned.
 * <p>
 * PATTERN: Java 25 Pattern Matching Switch with Guarded Patterns
 * case HttpStatusCodeException e when e.getStatusCode().value() == 403
 * This is a GUARDED PATTERN — type check + condition in a single arm.
 * No casting, no nested ifs, no temp variables.
 * <p>
 * EXHAUSTIVENESS NOTE:
 * The switch is over Exception (not a sealed type), so a default arm is needed.
 * However, the ordered arms ensure specific types are matched first.
 * The pattern matching JVM performs instanceof + cast in a single bytecode op.
 * <p>
 * ADDING NEW VAULT ERROR TYPES:
 * Add a new case arm here. The compiler will immediately catch any
 * switch over VaultError (sealed) that doesn't handle the new variant.
 */
@Component
public class VaultErrorMapper {

    /**
     * Map an exception from a Vault encrypt call to a VaultError variant.
     */
    public VaultError forEncrypt(Exception ex, String key) {
        return map(ex, key, "encrypt");
    }

    /**
     * Map an exception from a Vault decrypt call to a VaultError variant.
     * Decrypt has a special case: HTTP 400 from Vault means the ciphertext
     * itself is invalid (corrupted/tampered) — a client error, not a server error.
     */
    public VaultError forDecrypt(Exception ex, String key) {
        if (ex instanceof HttpStatusCodeException h && h.getStatusCode().value() == 400) {
            return new VaultError.InvalidInput(
                    "Ciphertext is invalid or has been tampered with", h);
        }
        return map(ex, key, "decrypt");
    }

    /**
     * Core mapping logic — pattern matching switch over Spring Vault exception types.
     * <p>
     * Order matters: more specific types must come before more general ones.
     * Java evaluates case arms top-to-bottom and picks the FIRST match.
     */
    private VaultError map(Exception ex, String key, String op) {
        return switch (ex) {

            // ── Auth / login problems ───────────────────────────────────────
            case VaultLoginException e -> new VaultError.AuthFailed(
                    "Vault authentication failed. Check AppRole credentials.", e);

            // ── HTTP responses from Vault — guarded patterns on status code ─
            case HttpStatusCodeException e when e.getStatusCode().value() == 401 ->
                    new VaultError.AuthFailed(
                            "Vault returned 401. Token may be expired or invalid.", e);

            case HttpStatusCodeException e when e.getStatusCode().value() == 403 ->
                    new VaultError.PermissionDenied(
                            "Vault policy denied the '%s' operation on key '%s'.".formatted(op, key), e);

            case HttpStatusCodeException e when e.getStatusCode().value() == 404 ->
                    new VaultError.SecretNotFound(
                            "Transit key '%s' not found in Vault.".formatted(key), e);

            case HttpStatusCodeException e ->
                // All other HTTP errors from Vault (500, 502, etc.)
                    new VaultError.EncryptionFailed(
                            "Vault returned HTTP %d during %s.".formatted(
                                    e.getStatusCode().value(), op), e);

            // ── Network / connectivity issues ───────────────────────────────
            case ResourceAccessException e -> new VaultError.Unavailable(
                    "Cannot reach Vault. Check network or Vault seal status.", e);

            case VaultException e -> new VaultError.Unavailable(
                    "Vault client error during %s: %s".formatted(op, e.getMessage()), e);

            // ── Catch-all — unknown exception from Spring Vault ─────────────
            default -> "encrypt".equals(op)
                    ? new VaultError.EncryptionFailed(
                    "Unexpected error during encrypt.", ex)
                    : new VaultError.DecryptionFailed(
                    "Unexpected error during decrypt.", ex);
        };
    }
}
