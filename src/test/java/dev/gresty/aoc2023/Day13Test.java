package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day13Test {

    private static final String EXAMPLE = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.
                        
            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#""";

    @Test
    public void testPart1() {
        assertThat(new Day13(PuzzleInput.of(EXAMPLE)).part1()).isEqualTo("405");
    }

    @Test
    public void testPart2() {
        assertThat(new Day13(PuzzleInput.of(EXAMPLE)).part2()).isEqualTo("Not implemented");
    }
}
