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
import java.util.ArrayList;
import java.util.List;

public class ApiClient {

    private static final String API_URL = "https://api.github.com/search/repositories";
    private static final String API_VERSION = "2022-11-28";
    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ObjectMapper mapper = new ObjectMapper();



    public List<Repository> searchTrending(String createdSince, int limit, String language) throws IOException, InterruptedException {
        String q = "created:>=" + createdSince;
        if (language != null && !language.isBlank()) {
            q += " language:" + language.trim();
        }
        String url = API_URL
                + "?q=" + URLEncoder.encode(q, StandardCharsets.UTF_8)
                + "&sort=stars&order=desc&per_page=" + limit;

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", API_VERSION);

        String token = System.getenv("GITHUB_TOKEN");
        if (token != null && !token.isBlank()) {
            requestBuilder.header("Authorization", "Bearer " + token.trim());
        }

        HttpRequest request = requestBuilder.GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 401) {
            throw new RuntimeException("GitHub API unauthorized (HTTP 401). Check GITHUB_TOKEN.");
        }
        if (response.statusCode() == 403) {
            throw new RuntimeException("GitHub API rate limit exceeded (HTTP 403). Set GITHUB_TOKEN to increase the limit.");
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
