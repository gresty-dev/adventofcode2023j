package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day14Test {

    private static final String EXAMPLE = """
            O....#....
            O.OO#....#
            .....##...
            OO.#O....O
            .O.....O#.
            O.#..O.#.#
            ..O..#O..O
            .......O..
            #....###..
            #OO..#....""";

    @Test
    public void testPart1() {
        assertThat(new Day14(PuzzleInput.of(EXAMPLE)).part1()).isEqualTo("136");
    }

    @Test
    public void testPart2() {
        assertThat(new Day14(PuzzleInput.of(EXAMPLE)).part2()).isEqualTo("64");
    }
}
