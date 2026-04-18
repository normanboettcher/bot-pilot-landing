package de.bot.pilot.mail.domain.port.outbound;

import de.bot.pilot.mail.domain.exception.CaptchaVerificationException;

public interface CaptchaPort {

	/**
	 * Verifies the captcha token issued by the client.
	 *
	 * @throws CaptchaVerificationException
	 *             if verification fails or the remote service rejects the token
	 */
	void verify(String token, String remoteIp);
}
