package com.lukasz.trending.cli;

import com.lukasz.trending.model.DurationRange;

public record CliArgs (DurationRange duration, int limit) {
    public static CliArgs parse(String[] args) {
        DurationRange duration = DurationRange.WEEK;
        int limit = 10;

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "--duration" -> {
                    if (i + 1 >= args.length) throw new IllegalArgumentException("No value for --duration");
                    duration = DurationRange.from(args[++i]);
                }
                case "--limit" -> {
                    if (i + 1 >= args.length) throw new IllegalArgumentException("Brak wartości dla --limit");
                    limit = Integer.parseInt(args[++i]);
                    if (limit <= 0) throw new IllegalArgumentException("--limit musi być > 0");
                }
                default -> throw new IllegalArgumentException("Unknown Argument: " + args[i]);
            }
        }

        return new CliArgs(duration, limit);
    }
}