package de.bot.pilot.crypto.api.dto;

/**
 * HTTP response body record.
 *
 * Deliberately minimal — the result string and the requestId for correlation.
 * Clients can match requestId to the X-Request-ID response header.
 */
public record CryptoResponse(String result, String requestId) {}
