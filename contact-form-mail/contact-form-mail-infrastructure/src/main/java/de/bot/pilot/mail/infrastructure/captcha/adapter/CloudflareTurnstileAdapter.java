package de.bot.pilot.mail.infrastructure.captcha.adapter;

import de.bot.pilot.mail.domain.exception.CaptchaVerificationException;
import de.bot.pilot.mail.domain.port.outbound.CaptchaPort;
import de.bot.pilot.mail.infrastructure.captcha.config.CaptchaProperties;
import de.bot.pilot.mail.infrastructure.captcha.dto.TurnstileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Component
public class CloudflareTurnstileAdapter implements CaptchaPort {

	private static final Logger log = LoggerFactory.getLogger(CloudflareTurnstileAdapter.class);
	private static final String VERIFY_URL = "https://challenges.cloudflare.com/turnstile/v0/siteverify";

	private final RestClient restClient = RestClient.create();
	private final CaptchaProperties properties;

	public CloudflareTurnstileAdapter(CaptchaProperties properties) {
		this.properties = properties;
	}

	@Override
	public void verify(String token, String remoteIp) {
		log.debug("Verifying Turnstile token for remoteIp={}", remoteIp);
		String secret = readSecret();
		TurnstileResponse response = restClient.post().uri(VERIFY_URL)
				.body(Map.of("secret", secret, "response", token, "remoteip", remoteIp)).retrieve()
				.body(TurnstileResponse.class);

		if (response == null || !response.success()) {
			throw new CaptchaVerificationException("Cloudflare Turnstile verification failed");
		}
	}

	private String readSecret() {
		try {
			return Files.readString(Path.of(properties.path())).trim();
		} catch (IOException e) {
			throw new UncheckedIOException("Cannot read captcha secret from " + properties.path(), e);
		}
	}
}
