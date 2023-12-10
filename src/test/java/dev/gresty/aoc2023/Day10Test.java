package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day10Test {

    private static final String EXAMPLE_A = """
            -L|F7
            7S-7|
            L|7||
            -L-J|
            L|-JF""";

    private static final String EXAMPLE_B = """
            7-F7-
            .FJ|7
            SJLL7
            |F--J
            LJ.LJ""";

    private static final String EXAMPLE_C = """
            ...........
            .S-------7.
            .|F-----7|.
            .||.....||.
            .||.....||.
            .|L-7.F-J|.
            .|..|.|..|.
            .L--J.L--J.
            ...........""";

    private static final String EXAMPLE_D = """
            .F----7F7F7F7F-7....
            .|F--7||||||||FJ....
            .||.FJ||||||||L7....
            FJL7L7LJLJ||LJ.L-7..
            L--J.L7...LJS7F-7L7.
            ....F-J..F7FJ|L7L7L7
            ....L7.F7||L7|.L7L7|
            .....|FJLJ|FJ|F7|.LJ
            ....FJL-7.||.||||...
            ....L---J.LJ.LJLJ...""";

    private static final String EXAMPLE_E = """
            FF7FSF7F7F7F7F7F---7
            L|LJ||||||||||||F--J
            FL-7LJLJ||||||LJL-77
            F--JF--7||LJLJ7F7FJ-
            L---JF-JLJ.||-FJLJJ7
            |F|F-JF---7F7-L7L|7|
            |FFJF7L7F-JF7|JL---7
            7-L-JL7||F7|L7F-7F7|
            L.L7LFJ|||||FJL7||LJ
            L7JLJL-JLJLJL--JLJ.L""";
    @Test
    public void testPart1() {
        assertThat(new Day10(PuzzleInput.of(EXAMPLE_A)).part1()).isEqualTo("4");
        assertThat(new Day10(PuzzleInput.of(EXAMPLE_B)).part1()).isEqualTo("8");
    }

    @Test
    public void testPart2() {
        assertThat(new Day10(PuzzleInput.of(EXAMPLE_C)).part2()).isEqualTo("4");
        assertThat(new Day10(PuzzleInput.of(EXAMPLE_D)).part2()).isEqualTo("8");
        assertThat(new Day10(PuzzleInput.of(EXAMPLE_E)).part2()).isEqualTo("10");
    }
}
