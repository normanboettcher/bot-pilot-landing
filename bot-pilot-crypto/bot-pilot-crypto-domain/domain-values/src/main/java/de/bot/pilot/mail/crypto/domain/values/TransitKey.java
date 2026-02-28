package de.bot.pilot.mail.crypto.domain.values;

import java.util.Objects;

/// Domain-Model for a `TransitKey`
///
/// @param mountPath the mount path of the transit key
/// @param keyName   the name of the transit key
public record TransitKey(String mountPath, String keyName) {

    public TransitKey {   // compact canonical constructor
        Objects.requireNonNull(mountPath);
        Objects.requireNonNull(keyName);
        mountPath = mountPath.strip().replaceAll("^/+", "").replaceAll("/+$", "");
        keyName = keyName.strip();
    }

    /// e.g. "transit/mail/encrypt/mail-content-key"
    public String encryptPath() {
        return "%s/encrypt/%s".formatted(mountPath, keyName);
    }

    public String decryptPath() {
        return "%s/decrypt/%s".formatted(mountPath, keyName);
    }
}