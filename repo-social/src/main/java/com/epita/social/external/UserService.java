package com.epita.social.external;

import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@ApplicationScoped
public class UserService {
    public boolean userExists(ObjectId id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9000/api/user/by_user_id/" + id))
                .GET()
                .build();
        try {
            var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;
        } catch (Exception e) {
            return false;
        }
    }
}
