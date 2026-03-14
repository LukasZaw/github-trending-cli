package com.lukasz.trending.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DurationRangeTest {

    @Test
    void shouldParseSupportedValues() {
        assertEquals(DurationRange.DAY, DurationRange.from("day"));
        assertEquals(DurationRange.WEEK, DurationRange.from("week"));
        assertEquals(DurationRange.MONTH, DurationRange.from("month"));
        assertEquals(DurationRange.YEAR, DurationRange.from("year"));
    }

    @Test
    void shouldRejectUnsupportedValue() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> DurationRange.from("hour")
        );

        assertEquals("Invalid --duration: hour. Allowed values: day|week|month|year", exception.getMessage());
    }

    @Test
    void shouldComputeSinceDate() {
        LocalDate now = LocalDate.of(2026, 3, 14);

        assertEquals(LocalDate.of(2026, 3, 13), DurationRange.DAY.sinceDate(now));
        assertEquals(LocalDate.of(2026, 3, 7), DurationRange.WEEK.sinceDate(now));
        assertEquals(LocalDate.of(2026, 2, 12), DurationRange.MONTH.sinceDate(now));
        assertEquals(LocalDate.of(2025, 3, 14), DurationRange.YEAR.sinceDate(now));
    }
}


