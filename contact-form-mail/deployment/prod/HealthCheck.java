import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

void main() throws IOException, InterruptedException {
    var client = HttpClient.newHttpClient();
    var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/actuator/health"))
            .build();
    var response = client.send(request, HttpResponse.BodyHandlers.discarding());
    if (response.statusCode() != 200) {
        System.exit(1);
    }
}