package com.lukasz.trending.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Repository {
    public int id;
    public String name;
    public String full_name;
    public String description;
    public int stargazers_count;
    public String language;
}
