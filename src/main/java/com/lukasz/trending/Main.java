package com.lukasz.trending;

import com.lukasz.trending.cli.CliArgs;
import com.lukasz.trending.client.ApiClient;
import com.lukasz.trending.model.Repository;
import com.lukasz.trending.service.FormaterService;
import com.lukasz.trending.service.RepoService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            if (CliArgs.isHelpRequested(args)) {
                CliArgs.printUsage();
                return;
            }

            CliArgs cliArgs = CliArgs.parse(args);
            RepoService service = new RepoService(new ApiClient());

            List<Repository> repos = service.getTrending(cliArgs.duration(), cliArgs.limit(), cliArgs.language());

            FormaterService.print(repos, cliArgs.duration(), cliArgs.limit(), cliArgs.language());
        } catch (IllegalArgumentException e) {
            System.err.println("Argument error: " + e.getMessage());
            CliArgs.printUsage();
            System.exit(2);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
