package com.lukasz.trending.cli;

import com.lukasz.trending.model.DurationRange;

public record CliArgs (DurationRange duration, int limit, String language) {
    public static final DurationRange DEFAULT_DURATION = DurationRange.WEEK;
    public static final int DEFAULT_LIMIT = 10;

    public static CliArgs parse(String[] args) {
        DurationRange duration = DEFAULT_DURATION;
        int limit = DEFAULT_LIMIT;
        String language = null;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--duration", "-d"-> {
                    if (i + 1 >= args.length) throw new IllegalArgumentException("Missing value for --duration.");
                    duration = DurationRange.from(args[++i]);
                }
                case "--limit" -> {
                    if (i + 1 >= args.length) throw new IllegalArgumentException("Missing value for --limit.");
                    try {
                        limit = Integer.parseInt(args[++i]);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("--limit must be an integer.");
                    }
                    if (limit <= 0) throw new IllegalArgumentException("--limit must be > 0");
                }
                case "--help", "-h" -> {
                    // Main decides whether to print help and what exit code to use.
                }
                case "--language", "-l" -> {
                    if (i + 1 >= args.length) throw new IllegalArgumentException("Missing value for --language.");
                    language = args[++i].trim();
                    if (language.isEmpty()) throw new IllegalArgumentException("--language must not be empty.");
                }
                default -> throw new IllegalArgumentException("Unknown argument: " + args[i]);
            }
        }

        return new CliArgs(duration, limit, language);
    }

    public static boolean isHelpRequested(String[] args) {
        for (String arg : args) {
            if ("--help".equals(arg) || "-h".equals(arg)) {
                return true;
            }
        }
        return false;
    }

    public static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  java -jar target/github-trending-cli-1.0-SNAPSHOT.jar [--duration day|week|month|year] [--limit N] [--language LANG|-l LANG]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  --duration   Time range filter (default: " + DEFAULT_DURATION.name().toLowerCase() + ")");
        System.out.println("  --limit      Number of repositories to fetch (default: " + DEFAULT_LIMIT + ")");
        System.out.println("  --language   Filter repositories by programming language");
        System.out.println("  -l           Short alias for --language");
        System.out.println("  --help, -h   Show this help");
    }
}