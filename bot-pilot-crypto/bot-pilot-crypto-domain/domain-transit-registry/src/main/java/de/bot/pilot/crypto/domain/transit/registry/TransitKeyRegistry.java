package de.bot.pilot.crypto.domain.transit.registry;

import de.bot.pilot.mail.crypto.domain.values.EncryptionPurpose;
import de.bot.pilot.mail.crypto.domain.values.TransitKey;

import java.util.Optional;
import java.util.Set;

public interface TransitKeyRegistry {

    /**
     * Resolve purpose → TransitKey.
     * Returns Optional — the caller (CryptoService) decides the missing policy.
     * WHY NOT THROW HERE: keeps the registry reusable in contexts
     * where "missing" is acceptable (e.g. feature flags, A/B testing).
     */
    Optional<TransitKey> resolve(EncryptionPurpose purpose);

    /**
     * All registered purposes — used by startup validation and /health.
     */
    Set<EncryptionPurpose> registeredPurposes();
}
