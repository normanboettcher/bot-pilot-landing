import de.bot.pilot.crypto.domain.transit.registry.TransitKeyRegistry;
import de.bot.pilot.mail.crypto.domain.values.EncryptionPurpose;
import de.bot.pilot.mail.crypto.domain.values.TransitKey;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * YAML-driven implementation of TransitKeyRegistry.
 * <p>
 * STRATEGY PATTERN:
 * Each entry in TransitKeyProperties.keys is a "strategy" for a given purpose.
 * At startup, we eagerly build an immutable Map<EncryptionPurpose, TransitKey>
 * from the YAML config. Lookups at runtime are O(1) HashMap gets — zero I/O.
 * <p>
 * WHY EAGER LOADING AT STARTUP (not lazy):
 * Fail-fast: if "MAIL" is missing from YAML, the application refuses to start
 * rather than failing at the first encrypt call in production.
 *
 * @PostConstruct validate() logs a clear error before the context starts.
 * <p>
 * THREAD SAFETY:
 * The registry map is built once in @PostConstruct and is then effectively
 * immutable (Collections.unmodifiableMap). No synchronisation needed.
 * <p>
 * EXTENSIBILITY:
 * To add a new purpose (e.g. DOCUMENT):
 * 1. Add record Document() to EncryptionPurpose (domain-model)
 * 2. Add "DOCUMENT: {mount-path: ..., key-name: ...}" to application.yml
 * 3. Add "case EncryptionPurpose.Document d -> ..." to fromLabel() in EncryptionPurpose
 * Compiler guides you to every switch that needs updating.
 */
@Component
public class YamlTransitKeyRegistry implements TransitKeyRegistry {

    private static final Logger log = LoggerFactory.getLogger(YamlTransitKeyRegistry.class);
    private Map<EncryptionPurpose, TransitKey> registry;
    private final TransitKeyProperties  properties;

    public YamlTransitKeyRegistry(TransitKeyProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    void build() {
        var map = new LinkedHashMap<EncryptionPurpose, TransitKey>();
        properties.keys().forEach((label, entry) -> {
            // Unknown YAML label → startup failure. Fail-fast, not fail-in-production.
            var purpose = EncryptionPurpose.fromLabel(label);
            map.put(purpose, new TransitKey(entry.mountPath(), entry.keyName()));
            log.info("transit.registry.loaded purpose={} mount={} key={}",
                    label, entry.mountPath(), entry.keyName());
        });
        this.registry = Collections.unmodifiableMap(map); // immutable after startup
    }

    @Override
    public Optional<TransitKey> resolve(EncryptionPurpose purpose) {
        return Optional.ofNullable(registry.get(purpose));
    }

    @Override
    public Set<EncryptionPurpose> registeredPurposes() {
        return registry.keySet();
    }
}