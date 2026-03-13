package com.lukasz.trending.model;

import java.time.LocalDate;

public enum DurationRange{
    DAY, WEEK, MONTH, YEAR;

    public static DurationRange from(String value) {
        return switch (value.toLowerCase()) {
            case "day" -> DAY;
            case "week" -> WEEK;
            case "month" -> MONTH;
            case "year" -> YEAR;
            default -> throw new IllegalArgumentException("Niepoprawny duration: " + value);
        };
    }

    public LocalDate sinceDate(LocalDate now) {
        return switch (this) {
            case DAY -> now.minusDays(1);
            case WEEK -> now.minusDays(7);
            case MONTH -> now.minusDays(30);
            case YEAR -> now.minusDays(365);
        };
    }
}
