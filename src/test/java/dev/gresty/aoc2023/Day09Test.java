package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day09Test {

    private static final String EXAMPLE = """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45""";

    @Test
    public void testPart1() {
        assertThat(new Day09(PuzzleInput.of(EXAMPLE)).part1()).isEqualTo("114");
    }

    @Test
    public void testPart2() {
        assertThat(new Day09(PuzzleInput.of(EXAMPLE)).part2()).isEqualTo("2");
    }

    @Test
    public void testPascal() {
        assertThat(Day09.pascalUsingBinomialExpansion(0, 4).toLongArray()).containsExactly(1, -1, 1, -1);
        assertThat(Day09.pascalUsingBinomialExpansion(1, 1).toLongArray()).containsExactly(1);
        assertThat(Day09.pascalUsingBinomialExpansion(2, 2).toLongArray()).containsExactly(1, 1);
        assertThat(Day09.pascalUsingBinomialExpansion(3, 3).toLongArray()).containsExactly(1, 2, 1);
        assertThat(Day09.pascalUsingBinomialExpansion(4, 4).toLongArray()).containsExactly(1, 3, 3, 1);
        assertThat(Day09.pascalUsingBinomialExpansion(5, 5).toLongArray()).containsExactly(1, 4, 6, 4, 1);
    }

    @Test
    public void testVariables() {
        assertThat(Day09.variables(new int[] {10, 13, 16, 21, 30, 45})).containsExactly(10, 3, 0, 2);
    }
}
