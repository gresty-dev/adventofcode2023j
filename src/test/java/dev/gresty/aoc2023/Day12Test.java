package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day12Test {

    private static final String EXAMPLE = """
            ???.### 1,1,3
            .??..??...?##. 1,1,3
            ?#?#?#?#?#?#?#? 1,3,1,6
            ????.#...#... 4,1,1
            ????.######..#####. 1,6,5
            ?###???????? 3,2,1""";

    @Test
    public void testPart1() {
        assertThat(new Day12(PuzzleInput.of(EXAMPLE)).part1()).isEqualTo("21");
    }

    @Test
    public void testPart2() {
        assertThat(new Day12(PuzzleInput.of(EXAMPLE)).part2()).isEqualTo("525152");
    }
}
