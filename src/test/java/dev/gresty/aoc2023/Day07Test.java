package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day07Test {

    private static final String EXAMPLE = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483""";

    @Test
    public void testPart1() {
        assertThat(new Day07(PuzzleInput.of(EXAMPLE)).part1()).isEqualTo("6440");
    }

    @Test
    public void testPart2() {
        assertThat(new Day07(PuzzleInput.of(EXAMPLE)).part2()).isEqualTo("5905");
    }
}
