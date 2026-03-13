package com.lukasz.trending.service;

import com.lukasz.trending.model.Repository;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.*;

public class RepoService {
    public List<Repository> parseRepos(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        JsonNode items = root.get("items");

        List<Repository> repos = new ArrayList<>();

        for (JsonNode node : items) {
            Repository repo = mapper.treeToValue(node, Repository.class);
            repos.add(repo);
        }

        return repos;
    }
}
