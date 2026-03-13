package com.lukasz.trending.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukasz.trending.model.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApiClient {

    private static final String API_URL = "https://api.github.com/search/repositories";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ObjectMapper mapper = new ObjectMapper();



    public List<Repository> searchTrending(String createdSince, int limit) throws IOException, InterruptedException {
        String q = "created:>=" + createdSince;
        String url = API_URL
                + "?q=" + URLEncoder.encode(q, StandardCharsets.UTF_8)
                + "&sort=stars&order=desc&per_page=" + limit;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2026-03-10")
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 403) {
            throw new RuntimeException("GitHub API rate limit exceeded (HTTP 403).");
        }
        if (response.statusCode() != 200) {
            throw new RuntimeException("GitHub API error: HTTP " + response.statusCode() + " -> " + response.body());
        }

        return parseRepos(response.body());
    }


    private List<Repository> parseRepos(String json) throws IOException {
        JsonNode root = mapper.readTree(json);
        JsonNode items = root.get("items");

        List<Repository> repos = new ArrayList<>();
        if (items != null && items.isArray()) {
            for (JsonNode item : items) {
                repos.add(new Repository(
                        text(item, "full_name"),
                        text(item, "description"),
                        item.path("stargazers_count").asInt(0),
                        text(item, "language"),
                        text(item, "html_url")
                ));
            }
        }
        return repos;
    }

    private String text(JsonNode node, String field) {
        JsonNode v = node.get(field);
        return (v == null || v.isNull()) ? "-" : v.asText();
    }
}
