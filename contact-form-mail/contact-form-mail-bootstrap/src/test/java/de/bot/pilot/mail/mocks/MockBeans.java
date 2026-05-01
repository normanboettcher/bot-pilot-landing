package de.bot.pilot.mail.mocks;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.core.VaultOperations;

@Configuration
public class MockBeans {
	@Bean
	public VaultOperations vaultOperations() {
		return org.mockito.Mockito.mock(VaultOperations.class);
	}
}
