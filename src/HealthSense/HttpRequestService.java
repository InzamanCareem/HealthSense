package HealthSense;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRequestService {

    HttpClient client = HttpClient.newHttpClient();
    final String BASE = "http://127.0.0.1:8000/";

    public void post(String query) {

        String json = String.format("""
                {
                    "query": "%s"
                }
                """, query);

        System.out.println(json);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status code: " + response.statusCode());
            System.out.println("Response body: " + response.body());

        } catch (IOException | InterruptedException e) {
            System.out.println("Error occurred");
        }
    }

//    public String get() {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(BASE))
//                .header("Content-Type", "application/json")
//                .GET()
//                .build();
//
//        try {
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//              System.out.println("Status code: " + response.statusCode());
//              System.out.println("Response body: " + response.body());
//
//            return response.body();
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
