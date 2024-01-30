package com.epita.post.external;

import jakarta.enterprise.context.ApplicationScoped;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class BlockService {
    public boolean isBlocked(final String userId, final String blockedId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9001/api/blocked/" + blockedId))
                .header("X-user-id", userId)
                .GET()
                .build();
        try {
            var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200 && Boolean.parseBoolean( response.body());
        } catch (Exception e) {
            return false;
        }
    }
}
