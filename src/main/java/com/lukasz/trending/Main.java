package com.lukasz.trending;

import com.lukasz.trending.cli.CliArgs;
import com.lukasz.trending.client.ApiClient;
import com.lukasz.trending.model.Repository;
import com.lukasz.trending.service.FormaterService;
import com.lukasz.trending.service.RepoService;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        CliArgs cliArgs = CliArgs.parse(args);

        try {
            RepoService service = new RepoService(new ApiClient());

            List<Repository> repos = service.getTrending(cliArgs.duration(), cliArgs.limit());

            FormaterService.print(repos, cliArgs.duration(), cliArgs.limit());
        }
        catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
