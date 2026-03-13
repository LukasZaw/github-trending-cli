package com.lukasz.trending.model;

public record Repository (
        String fullName,
        String description,
        int stars,
        String language,
        String htmlUrl
) {}
