package de.bot.pilot.mail.infrastructure.persistence.adapter;

import de.bot.pilot.mail.infrastructure.persistence.repository.CustomerJpaRepository;
import de.bot.pilot.mail.infrastructure.persistence.repository.EmailRequestJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ContactFormRecordAdapterTest {

	@Mock
	private CustomerJpaRepository customerRepository;
	@Mock
	private EmailRequestJpaRepository emailRequestRepository;
	private ContactFormRecordAdapter underTest;

	@BeforeEach
	void setUp() {
		underTest = new ContactFormRecordAdapter(customerRepository, emailRequestRepository);
	}

	@Test
	@DisplayName("countEmailRecords delegates to emailRequestRepository.count()")
	void countEmailRecords_delegatesToRepository() {
		// given
		given(emailRequestRepository.count()).willReturn(17L);

		// when
		long result = underTest.countEmailRecords();

		// then
		assertThat(result).isEqualTo(17L);
		verify(emailRequestRepository).count();
	}
}
