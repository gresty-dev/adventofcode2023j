package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day15Test {

    private static final String EXAMPLE = """
            rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7""";

    @Test
    public void testPart1() {
        assertThat(new Day15(PuzzleInput.of(EXAMPLE)).part1()).isEqualTo("1320");
    }

    @Test
    public void testPart2() {
        assertThat(new Day15(PuzzleInput.of(EXAMPLE)).part2()).isEqualTo("145");
    }

    @Test
    public void testHash() {
        assertThat(Day15.hash("HASH")).isEqualTo(52);
    }
}
