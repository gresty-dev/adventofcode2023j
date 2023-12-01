package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PuzzleInputTest {

    @Test
    public void testFilename() {
        assertThat(PuzzleInput.filename(0)).isEqualTo("00");
        assertThat(PuzzleInput.filename(1)).isEqualTo("01");
        assertThat(PuzzleInput.filename(10)).isEqualTo("10");
        assertThat(PuzzleInput.filename(25)).isEqualTo("25");
    }
}