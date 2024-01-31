package com.epita.home_timeline.external;

import jakarta.enterprise.context.ApplicationScoped;
import org.bson.types.ObjectId;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class SocialService {

    public List<ObjectId> getFollows(ObjectId userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9002/api/follows/" + userId))
                .GET()
                .build();
        try {
            var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return Stream.of(response.body().replaceAll("[\\[\\]\"]", "").split(",")).map(ObjectId::new).toList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public List<ObjectId> getFollowers(ObjectId userId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:9002/api/followers/" + userId))
                .GET()
                .build();
        try {
            var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return Stream.of(response.body().replaceAll("[\\[\\]\"]", "").split(",")).map(ObjectId::new).toList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
