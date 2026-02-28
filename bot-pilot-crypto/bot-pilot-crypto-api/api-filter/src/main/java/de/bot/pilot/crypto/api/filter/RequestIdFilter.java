package de.bot.pilot.crypto.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

/**
 * Request ID filter — cross-cutting concern, no domain dependency.
 * <p>
 * WHY ITS OWN MODULE:
 * Cross-cutting infrastructure. No dependency on domain, application, or vault.
 * Can be extracted to a shared library and reused across microservices.
 * Builds in parallel with all other leaf modules.
 * <p>
 * WHAT IT DOES:
 * 1. Reads X-Request-ID from incoming header (caller sets it for tracing chains)
 * or generates a fresh ID if absent.
 * 2. Puts it in SLF4J MDC → appears in EVERY log line for this request automatically.
 * 3. Sets it as a request attribute → available in handler + factory.
 * 4. Echoes it in response header → caller can correlate their logs to ours.
 * 5. ALWAYS clears MDC in finally → prevents leak into virtual thread reuse.
 *
 * @Order(Ordered.HIGHEST_PRECEDENCE) ensures this runs before ALL other filters.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestIdFilter extends OncePerRequestFilter {

    public static final String HEADER = "X-Request-ID";
    public static final String ATTR_REQUEST_ID = "requestId";
    public static final String MDC_KEY = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {

        var requestId = Optional.ofNullable(request.getHeader(HEADER))
                .filter(s -> !s.isBlank())
                .orElseGet(RequestIdFilter::generate);

        MDC.put(MDC_KEY, requestId);
        request.setAttribute(ATTR_REQUEST_ID, requestId);
        response.setHeader(HEADER, requestId);  // echo back for client correlation

        try {
            chain.doFilter(request, response);
        } finally {
            // CRITICAL: always clear MDC — virtual threads are pooled;
            // without this, a subsequent request on the same carrier thread
            // would inherit the previous request's MDC values.
            MDC.clear();
        }
    }

    private static String generate() {
        return "req_" + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }
}
