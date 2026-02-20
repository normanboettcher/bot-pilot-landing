package de.bot.pilot.mail.contact.config;

import jakarta.validation.constraints.NotBlank;

public class VaultTransitMessageProperties {
    @NotBlank
    private String key;
    @NotBlank
    private String path;

    public String getKey() {
        return key;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
