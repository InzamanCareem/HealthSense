package com.example.healthsenseapp.services;

import com.example.healthsenseapp.models.ForecastResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.concurrent.Task;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRequestService {

    HttpClient client = HttpClient.newHttpClient();
    final String BASE = "http://127.0.0.1:8000/";

    ObjectMapper mapper = new ObjectMapper();

    public Task<ForecastResponse> post(String query) {
        return new Task<>() {
            @Override
            protected ForecastResponse call() throws Exception {

                String json = String.format("""
                {
                    "query": "%s"
                }
                """, query);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE + "forecast"))
                        .version(HttpClient.Version.HTTP_1_1)
                        .header("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(json))
                        .build();

                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

                return mapper.readValue(response.body(), ForecastResponse.class);
            }
        };
    }
}

