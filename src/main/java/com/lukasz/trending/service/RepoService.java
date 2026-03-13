package com.lukasz.trending.service;

import com.lukasz.trending.client.ApiClient;
import com.lukasz.trending.model.DurationRange;
import com.lukasz.trending.model.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.fasterxml.jackson.databind.*;

public class RepoService {
    private final ApiClient client;

    public RepoService(ApiClient client) {
        this.client = client;
    }

    public List<Repository> getTrending(DurationRange range, int limit) throws Exception {
        String since = range.sinceDate(LocalDate.now()).toString();
        List<Repository> repos = client.searchTrending(since, limit);
        repos.sort(Comparator.comparingInt(Repository::stars).reversed());
        return repos;
    }

}
