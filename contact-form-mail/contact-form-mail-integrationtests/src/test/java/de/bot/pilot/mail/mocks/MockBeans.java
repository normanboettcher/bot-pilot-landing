package de.bot.pilot.mail.mocks;

import static org.mockito.Mockito.mock;

import de.bot.pilot.mail.domain.port.outbound.CaptchaPort;
import de.bot.pilot.mail.domain.port.outbound.MetricPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.vault.core.VaultOperations;

@Configuration
public class MockBeans {

	@Bean
	public VaultOperations vaultOperations() {
		return mock(VaultOperations.class);
	}

	/*
	 * @Primary overrides CloudflareTurnstileAdapter (the @Component registered by
	 * component-scan). Without this, the real adapter would try to read /dev/null
	 * (empty secret) and POST to Cloudflare — failing every test unconditionally.
	 * Stubbing at the CaptchaPort boundary keeps integration tests free of network
	 * calls while leaving the real adapter in scope for unit testing.
	 */
	@Bean
	@Primary
	public CaptchaPort captchaPort() {
		return mock(CaptchaPort.class);
	}

	@Bean
	@Primary
	public JavaMailSender mailSender() {
		return mock(JavaMailSender.class);
	}

	@Bean
	@Primary
	public MetricPort metricPort() {
		return mock(MetricPort.class);
	}
}
