package com.lukasz.trending.cli;

import com.lukasz.trending.model.DurationRange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CliArgsTest {

    @Test
    void shouldUseDefaultsWhenNoArgs() {
        CliArgs args = CliArgs.parse(new String[]{});

        assertEquals(DurationRange.WEEK, args.duration());
        assertEquals(10, args.limit());
        assertNull(args.language());
    }

    @Test
    void shouldParseDurationAndLimit() {
        CliArgs args = CliArgs.parse(new String[]{"--duration", "month", "--limit", "25"});

        assertEquals(DurationRange.MONTH, args.duration());
        assertEquals(25, args.limit());
        assertNull(args.language());
    }

    @Test
    void shouldParseLongLanguageOption() {
        CliArgs args = CliArgs.parse(new String[]{"--language", "python"});

        assertEquals("python", args.language());
    }

    @Test
    void shouldParseShortLanguageOption() {
        CliArgs args = CliArgs.parse(new String[]{"-l", "java"});

        assertEquals("java", args.language());
    }

    @Test
    void shouldRejectEmptyLanguage() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CliArgs.parse(new String[]{"--language", "   "})
        );

        assertEquals("--language must not be empty.", exception.getMessage());
    }

    @Test
    void shouldRejectNonNumericLimit() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CliArgs.parse(new String[]{"--limit", "abc"})
        );

        assertEquals("--limit must be an integer.", exception.getMessage());
    }

    @Test
    void shouldRejectNonPositiveLimit() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CliArgs.parse(new String[]{"--limit", "0"})
        );

        assertEquals("--limit must be > 0", exception.getMessage());
    }

    @Test
    void shouldRejectUnknownArgument() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> CliArgs.parse(new String[]{"--dur", "week"})
        );

        assertEquals("Unknown argument: --dur", exception.getMessage());
    }
}



