package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day03Test {

    private static final String EXAMPLE = """
            467..114..
            ...*......
            ..35..633.
            ......#...
            617*......
            .....+.58.
            ..592.....
            ......755.
            ...$.*....
            .664.598..""";

    @Test
    public void testExampleA() {
        assertThat(new Day03(PuzzleInput.of(EXAMPLE)).part1()).isEqualTo("Not implemented");
    }

    @Test
    public void testExampleB() {
        assertThat(new Day03(PuzzleInput.of(EXAMPLE)).part2()).isEqualTo("Not implemented");
    }
}
