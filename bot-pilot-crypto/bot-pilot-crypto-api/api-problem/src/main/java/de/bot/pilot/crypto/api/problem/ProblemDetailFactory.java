package de.bot.pilot.crypto.api.problem;

import de.bot.pilot.mail.crypto.domain.values.VaultError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.Instant;

/**
 * Factory — maps VaultError sealed variants → RFC 9457 ProblemDetail.
 * <p>
 * RFC 9457 FIELDS:
 * type      URI — stable identifier for this problem type. Clients SWITCH on this.
 * title     Short human-readable label — same for all occurrences of a type.
 * status    HTTP status code duplicated in body (parseable without headers).
 * detail    Specific explanation for THIS occurrence. Safe to show to users.
 * instance  URI identifying this specific error occurrence → /errors/{requestId}
 * + custom extensions: requestId, timestamp
 * <p>
 * WHY A FACTORY (not inlined in GlobalExceptionHandler):
 * The handler has one job: catch + log. This factory has one job: build the body.
 * Both are testable independently. The factory is pure input→output with no side effects.
 * <p>
 * SEALED SWITCH — COMPILER SAFETY:
 * The switch over VaultError is exhaustive. If a new VaultError variant is added,
 * this class will NOT COMPILE until the new arm is added. The compiler is the linter.
 */
@Component
public class ProblemDetailFactory {

    private static final String BASE = "https://api.bot-pilot.de/problems/";

    public ProblemDetail from(VaultError error, String requestId) {

        // Pattern matching switch — sealed, exhaustive, no default needed
        var detail = switch (error) {

            case VaultError.AuthFailed e -> problem(HttpStatus.UNAUTHORIZED,
                    "vault-auth-failed",
                    "Vault Authentication Failed",
                    e.detail());

            case VaultError.PermissionDenied e -> problem(HttpStatus.FORBIDDEN,
                    "vault-permission-denied",
                    "Vault Permission Denied",
                    e.detail());

            case VaultError.Unavailable e -> problem(HttpStatus.SERVICE_UNAVAILABLE,
                    "vault-unavailable",
                    "Secrets Service Unavailable",
                    "The secrets service is temporarily unavailable. Please retry shortly.");

            case VaultError.EncryptionFailed e ->
                    problem(HttpStatus.INTERNAL_SERVER_ERROR,
                            "vault-encryption-failed",
                            "Encryption Operation Failed",
                            "The encryption operation failed. Please try again.");

            case VaultError.DecryptionFailed e ->
                    problem(HttpStatus.INTERNAL_SERVER_ERROR,
                            "vault-decryption-failed",
                            "Decryption Operation Failed",
                            "The decryption operation failed. Please try again.");

            case VaultError.SecretNotFound e -> problem(HttpStatus.NOT_FOUND,
                    "vault-secret-not-found",
                    "Secret Not Found",
                    e.detail());

            case VaultError.InvalidInput e -> {
                yield problem(HttpStatus.BAD_REQUEST,
                        "invalid-input",
                        "Invalid Request Input",
                        e.detail());
            }
            case VaultError.UnknownPurpose e -> {
                yield problem(HttpStatus.BAD_REQUEST,
                        "unknown-purpose",
                        "Unknown Encryption Purpose",
                        e.detail());
            }
        };

        // Cross-cutting extensions — present on every error response
        detail.setProperty("requestId", requestId);
        detail.setProperty("timestamp", Instant.now().toString());
        detail.setInstance(URI.create("/errors/" + requestId));

        return detail;
    }

    private ProblemDetail problem(HttpStatus status, String slug,
                                  String title, String detailMsg) {
        var pd = ProblemDetail.forStatusAndDetail(status, detailMsg);
        pd.setType(URI.create(BASE + slug));
        pd.setTitle(title);
        return pd;
    }
}
