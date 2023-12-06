package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day06Test {

    private static final String EXAMPLE = """
            Time:      7  15   30
            Distance:  9  40  200""";

    @Test
    public void testPart1() {
        assertThat(new Day06(PuzzleInput.of(EXAMPLE)).part1()).isEqualTo("288");
    }

    @Test
    public void testPart2() {
        assertThat(new Day06(PuzzleInput.of(EXAMPLE)).part2()).isEqualTo("71503");
    }
}
