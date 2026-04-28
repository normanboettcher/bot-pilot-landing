package de.bot.pilot.mail.domain.error;

/**
 * Discriminated union of every failure mode the `EncryptionPort` can produce.
 * <p>
 * The two top-level branches are structurally distinct by intent: -
 * `InvalidInput` is a caller mistake — the plaintext or ciphertext was blank
 * before any Vault call was even attempted. Its `detail()` is safe to forward
 * to the client as-is. - `InfrastructureError` covers every failure that
 * originates inside or on the way to the Vault Transit engine. Its `detail()`
 * is intentionally generic ("service unavailable") and must **not** include
 * internal context such as key names, paths, or exception messages.
 * <p>
 * Handlers should exhaustively switch on the sealed hierarchy so the compiler
 * enforces coverage of future variants.
 */
public sealed interface EncryptionError permits EncryptionError.InfrastructureError, EncryptionError.InvalidInput {
	/**
	 * Human-readable detail — safe to surface in API response.
	 */
	String detail();

	/**
	 * Marker for errors that originate in the infrastructure layer — specifically
	 * the Vault Transit engine or the network path to it.
	 * <p>
	 * Every variant carries a `cause()` that **must be logged** by the handler so
	 * that the originating Spring Vault exception is not silently swallowed. The
	 * cause must **never** be serialised into the HTTP response body; clients
	 * receive only the generic `detail()` string.
	 * <p>
	 * `cause()` may be `null` on `OperationFailed` in the rare case where Vault
	 * returned a `null` result with no accompanying exception — the adapter
	 * documents this explicitly at the call site.
	 */
	sealed interface InfrastructureError extends EncryptionError permits EncryptionError.Unauthorized,
			EncryptionError.Forbidden, EncryptionError.Unavailable, EncryptionError.OperationFailed {
		/**
		 * Original cause — for logging only, never for clients.
		 */
		Throwable cause();
	}

	// ── Permitted variants ──────────────────────────────────────────────

	/**
	 * Produced when Spring Vault throws `VaultLoginException` or
	 * `VaultTokenRenewalException` — the AppRole credentials were rejected or the
	 * lease could not be renewed before the Transit call was made.
	 * <p>
	 * The handler maps this to **503 Service Unavailable** and logs the cause at
	 * `ERROR` level. This typically signals a misconfigured `APP_ROLE_ID` /
	 * `APP_SECRET_ID` or an expired, revoked token, and requires operator action.
	 */
	record Unauthorized(String detail, Throwable cause) implements InfrastructureError {
	}

	/**
	 * Produced when a `VaultException` whose message contains `"403"` is caught —
	 * the authenticated token lacks the `encrypt` or `decrypt` capability on the
	 * Transit key path configured in `VaultTransitProperties`.
	 * <p>
	 * The handler maps this to **503 Service Unavailable** (same opaque response as
	 * `Unauthorized`) and logs the cause at `ERROR` level. The root cause is a
	 * missing or incorrect Vault policy, not a transient network problem — retrying
	 * will not help.
	 */
	record Forbidden(String detail, Throwable cause) implements InfrastructureError {
	}

	/**
	 * Produced when a `ResourceAccessException` is caught — the HTTP connection to
	 * the Vault server could not be established or timed out before a response
	 * arrived. This is a transient network condition (Vault is down, DNS resolution
	 * failed, or the pod cannot reach the Vault sidecar).
	 * <p>
	 * The handler maps this to **503 Service Unavailable**. Unlike `Forbidden`, a
	 * retry after a brief back-off is reasonable once the underlying connectivity
	 * is restored.
	 */
	record Unavailable(String detail, Throwable cause) implements InfrastructureError {
	}

	/**
	 * Catch-all for Vault Transit failures that are neither an auth problem nor a
	 * connectivity problem — for example, a non-403 `VaultException` (key not
	 * found, key version mismatch, malformed ciphertext) or a `null` Transit result
	 * with no accompanying exception.
	 * <p>
	 * **`cause()` may be `null`** in the latter case: the adapter raises this
	 * variant explicitly when `VaultOperations` returns `null` without throwing, so
	 * there is no upstream exception to attach. Log handlers must guard against
	 * this. The handler maps this to **500 Internal Server Error**.
	 */
	record OperationFailed(String detail, Throwable cause) implements InfrastructureError {
	}

	/**
	 * Raised by the adapter **before** any Vault call when `plainText` or
	 * `cipherText` is blank or `null`. No infrastructure is involved, so there is
	 * no `cause()` and this variant does not extend `InfrastructureError`.
	 * <p>
	 * `field` names the offending parameter (`"plainText"` or `"cipherText"`). The
	 * handler maps this directly to **400 Bad Request** and forwards `detail()`
	 * verbatim to the response body — keep the message client-safe.
	 */
	record InvalidInput(String field, String detail) implements EncryptionError {

	}
}
