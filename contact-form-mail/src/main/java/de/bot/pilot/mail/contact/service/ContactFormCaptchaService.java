package de.bot.pilot.mail.contact.service;

import de.bot.pilot.mail.contact.dto.CaptchaResponse;
import de.bot.pilot.mail.contact.service.api.CaptchaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
@Slf4j
public class ContactFormCaptchaService implements CaptchaService {

    private final RestClient restClient = RestClient.create();

    @Value("${captcha.secret.path}")
    private String secretPath;

    @Override
    public void verify(String token, String ip) throws IOException {
        String secret = Files.readString(Path.of(secretPath)).trim();
        Map<String, String> params = Map.of(
                "secret", secret,
                "response", token,
                "remoteip", ip
        );

        CaptchaResponse resp = restClient.post()
                .uri("https://challenges.cloudflare.com/turnstile/v0/siteverify")
                .body(params)
                .retrieve()
                .body(CaptchaResponse.class);

        if (resp == null || !resp.success()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Captcha verification failed");
        }
    }
}
