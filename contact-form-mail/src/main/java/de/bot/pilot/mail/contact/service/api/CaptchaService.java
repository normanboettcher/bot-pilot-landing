package de.bot.pilot.mail.contact.service.api;

import java.io.IOException;

public interface CaptchaService {
    void verify(String token, String ip) throws IOException;
}
