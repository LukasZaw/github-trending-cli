package com.lukasz.trending.service;

import com.lukasz.trending.model.DurationRange;
import com.lukasz.trending.model.Repository;

import java.util.List;

public class FormaterService {
    public static void print(List<Repository> repos, DurationRange duration, int limit) {
        System.out.printf("Trending repositories (duration=%s, limit=%d)%n%n",
                duration.name().toLowerCase(), limit);

        if (repos.isEmpty()) {
            System.out.println("No results.");
            return;
        }

        int i = 1;
        for (Repository r : repos) {
            System.out.printf("%d) %s%n", i++, r.fullName());
            System.out.printf("   ⭐ %d | %s%n", r.stars(), r.language());
            System.out.printf("   %s%n", r.description());
            System.out.printf("   %s%n%n", r.htmlUrl());
        }
    }
}
