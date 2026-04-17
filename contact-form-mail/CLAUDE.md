# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build (skip tests)
./mvnw package -DskipTests

# Run tests
./mvnw test

# Run a single test class
./mvnw test -Dtest=ContactFormMailApplicationTests

# Run the application locally
./mvnw spring-boot:run

# Build container image (dev)
./mvnw package -DskipTests && podman build -f deployment/dev/Containerfile -t contact-form-mail:dev .

# Build container image (prod)
podman build -f deployment/prod/Containerfile -t contact-form-mail:prod .
```

## Architecture

Spring Boot 4 REST service (Java 25, Maven) that handles contact form submissions from a landing page. Single endpoint: `POST /contact`.

**Request flow:**
1. `ContactFormController` receives the `ContactRequest` (validated via Bean Validation)
2. `ContactFormCaptchaService` verifies the Cloudflare Turnstile token against the remote API — reads the secret from a file at `CAPTCHA_SECRET_PATH`
3. `ContactFormMailSenderService` sends a `SimpleMailMessage` via SMTP and persists the attempt to MariaDB regardless of send success/failure

**Secrets handling — file-based, not env vars:**
- SMTP password: loaded at startup from the file path in `SMTP_PASSWORD_FILE` env var (`MailSenderInitializer`)
- Captcha secret: read on every request from the file path in `captcha.secret.path` / `CAPTCHA_SECRET_PATH`
- DB credentials: injected dynamically by Spring Cloud Vault (AppRole auth, database secrets engine with role `contact-form-dev-role`)

**Vault integration:**
- Vault URI and AppRole credentials come from env vars: `VAULT_TARGET_URL`, `APP_ROLE_ID`, `APP_SECRET_ID`
- `spring.config.import: optional:vault://` — app starts without Vault in test profile
- `spring.cloud.vault.config.lifecycle.enabled: true` — leases are renewed automatically

**Persistence:**
- Two JPA entities: `ContactFormCustomer` (one) → `EmailRequest` (many)
- `ddl-auto: validate` — schema must be pre-created; no auto-migration
- MariaDB driver; datasource credentials supplied by Vault at runtime

**Test profile** (`src/test/resources/application.yml`): disables Vault, uses a local SMTP stub (port 25), and stubs the captcha secret path. The `@SpringBootTest` context load test is the only test currently present.
