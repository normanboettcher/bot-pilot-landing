import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class HealthCheck {
    private static final Logger logger = Logger.getLogger(HealthCheck.class.getName());

    /**
     * Main method that checks the health of a service by sending an HTTP GET request
     * to a predefined health check endpoint. If the status code of the response is not 200,
     * it terminates the program with a non-zero exit status.
     *
     * @throws IOException          if an I/O error occurs when sending or receiving the HTTP request
     * @throws InterruptedException if the operation is interrupted while waiting for the response
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        try (var client = HttpClient.newHttpClient()) {
            logger.info("Sending health check request to localhost:8080/actuator/health");
            var request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/actuator/health"))
                    .build();
            var response = client.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 200) {
                System.exit(1);
            }
        } catch (Exception e) {
            logger.severe("Health check failed: [" + e.getMessage() + "]");
            System.exit(1);
        }
    }
}
