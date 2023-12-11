package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day11Test {

    private static final String EXAMPLE = """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....""";

    @Test
    public void testPart1() {
        assertThat(new Day11(PuzzleInput.of(EXAMPLE)).part1()).isEqualTo("374");
    }

    @Test
    public void testPart2() {
        assertThat(new Day11(PuzzleInput.of(EXAMPLE)).part2(10)).isEqualTo("1030");
        assertThat(new Day11(PuzzleInput.of(EXAMPLE)).part2(100)).isEqualTo("8410");
    }
}
