package de.bot.pilot.mail.mocks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.core.VaultOperations;

import static org.mockito.Mockito.mock;

@Configuration
public class MockBeans {
	@Bean
	public VaultOperations vaultOperations() {
		return mock(VaultOperations.class);
	}
}
