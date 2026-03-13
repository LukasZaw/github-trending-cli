package com.lukasz.trending.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.time.LocalDate;

public class ApiClient {
    private final HttpClient client = HttpClient.newHttpClient();

    public String getRepos() throws Exception {
        String date = LocalDate.now().minusDays(2).toString();

        String url = "https://api.github.com/search/repositories?q=created:%3E2026-03-01&sort=stars";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

}
