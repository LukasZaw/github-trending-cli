package com.lukasz.trending;

import com.lukasz.trending.client.ApiClient;
import com.lukasz.trending.model.Repository;
import com.lukasz.trending.service.RepoService;

import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) throws Exception{

        ApiClient api = new ApiClient();
        
        String json = api.getRepos();

        RepoService ser = new RepoService();

        List<Repository> repos = ser.parseRepos(json);

        System.out.println(repos.get(0).name);

        Repository repo = new Repository();

        System.out.println("Hello World!");
    }
}
