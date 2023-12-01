package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day00Test {

    private static final String EXAMPLE_A = """
            ExampleA""";

    private static final String EXAMPLE_B = """
            ExampleB""";

    @Test
    public void testExampleA() {
        assertThat(new Day00(PuzzleInput.of(EXAMPLE_A)).part1()).isEqualTo("Not implemented");
    }

    @Test
    public void testExampleB() {
        assertThat(new Day00(PuzzleInput.of(EXAMPLE_B)).part2()).isEqualTo("Not implemented");
    }
}
