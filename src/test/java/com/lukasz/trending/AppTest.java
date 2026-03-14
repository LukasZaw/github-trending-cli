package com.lukasz.trending;

import com.lukasz.trending.cli.CliArgs;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppTest {
    @Test
    public void shouldDetectHelpFlag() {
        assertTrue(CliArgs.isHelpRequested(new String[]{"--help"}));
        assertTrue(CliArgs.isHelpRequested(new String[]{"-h"}));
        assertFalse(CliArgs.isHelpRequested(new String[]{"--duration", "week"}));
    }
}
