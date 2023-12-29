package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day16Test {

    private static final String EXAMPLE = """
            .|...\\....
            |.-.\\.....
            .....|-...
            ........|.
            ..........
            .........\\
            ..../.\\\\..
            .-.-/..|..
            .|....-|.\\
            ..//.|....""";

    @Test
    public void testPart1() {
        assertThat(new Day16(PuzzleInput.of(EXAMPLE)).part1()).isEqualTo("46");
    }

    @Test
    public void testPart2() {
        assertThat(new Day16(PuzzleInput.of(EXAMPLE)).part2()).isEqualTo("51");
    }
}
