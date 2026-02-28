import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * Typed configuration properties for transit key mappings.
 *
 * PATTERN: @ConfigurationProperties (Spring Boot type-safe config binding)
 *
 * Maps application.yml entries like:
 *
 *   vault:
 *     transit:
 *       keys:
 *         MAIL_ADDRESS:
 *           mount-path: transit/mail
 *           key-name: mail-content-key
 *         MESSAGE:
 *           mount-path: transit/message
 *           key-name: message-content-key
 *         COMPANY:
 *           mount-path: transit/company
 *           key-name: user-pii-key
 *
 * WHY MAP<String, KeyEntry> (not a fixed set of fields):
 *   Adding a new purpose = add a YAML entry. Zero code change here.
 *   The map key is the purpose label (matches EncryptionPurpose.fromLabel()).
 *   This makes the config self-describing: YAML key = domain purpose name.
 *
 * @param keys map of purpose label → key entry
 */
@ConfigurationProperties(prefix = "vault.transit")
public record TransitKeyProperties(Map<String, KeyEntry> keys) {

    public TransitKeyProperties {
        if (keys == null || keys.isEmpty())
            throw new IllegalStateException(
                "No transit keys configured. Add vault.transit.keys entries to application.yml");
    }

    /**
     * A single key configuration entry.
     *
     * @param mountPath Vault mount point, e.g. "transit/mail"
     * @param keyName   Key name within the mount
     */
    public record KeyEntry(String mountPath, String keyName) {
        public KeyEntry {
            if (mountPath == null || mountPath.isBlank())
                throw new IllegalStateException("mountPath must not be blank in transit key config");
            if (keyName == null || keyName.isBlank())
                throw new IllegalStateException("keyName must not be blank in transit key config");
        }
    }
}
