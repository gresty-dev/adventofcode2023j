package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day08Test {

    private static final String EXAMPLE_1 = """
            RL
                        
            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)""";

    private static final String EXAMPLE_2 = """
            LLR
                        
            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)""";

    private static final String EXAMPLE_3 = """
            LR
                        
            11A = (11B, XXX)
            11B = (XXX, 11Z)
            11Z = (11B, XXX)
            22A = (22B, XXX)
            22B = (22C, 22C)
            22C = (22Z, 22Z)
            22Z = (22B, 22B)
            XXX = (XXX, XXX)""";

    @Test
    public void testPart1() {
        assertThat(new Day08(PuzzleInput.of(EXAMPLE_1)).part1()).isEqualTo("2");
        assertThat(new Day08(PuzzleInput.of(EXAMPLE_2)).part1()).isEqualTo("6");
    }

    @Test
    public void testPart2() {
        assertThat(new Day08(PuzzleInput.of(EXAMPLE_3)).part2()).isEqualTo("6");
    }
}
