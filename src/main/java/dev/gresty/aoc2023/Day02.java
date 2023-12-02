package dev.gresty.aoc2023;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.max;

public class Day02 implements IPuzzle {

    private final PuzzleInput input;

    public Day02(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        final var balls = new CubeSet(12, 13, 14);
        final var answer = input.objects(Day02::createGame)
                .filter(g -> g.possibleWith(balls))
                .mapToInt(Game::id)
                .sum();
        return Integer.toString(answer);
    }

    public String part2() {
        final var answer = input.objects(Day02::createGame)
                .map(Game::minimumCubeSet)
                .mapToLong(CubeSet::power)
                .sum();
        return Long.toString(answer);
    }

    public static void main(String[] args) {
        Main.execute(2);
    }

    static Game createGame(final String line) {
        final var gameSplit = line.split(": ");
        final var game = new Game(Integer.parseInt(gameSplit[0].split(" ")[1]));
        final var drawSplit = gameSplit[1].split("; ");
        for (var draw : drawSplit) {
            game.addDraw(createCubeSet(draw));
        }
        return game;
    }

    static CubeSet createCubeSet(final String draw) {
        final var cubes = draw.split(", ");
        final Map<String, Integer> cubeCounts = new HashMap<>();
        for (var cube : cubes) {
            final var cubeDetails = cube.split(" ");
            cubeCounts.put(cubeDetails[1], Integer.parseInt(cubeDetails[0]));
        }
        return new CubeSet(cubeCounts.getOrDefault("red", 0),
                cubeCounts.getOrDefault("green", 0),
                cubeCounts.getOrDefault("blue", 0));
    }

    @RequiredArgsConstructor
    static class Game {
        @Getter
        private final int id;
        private final List<CubeSet> draws = new ArrayList<>();

        void addDraw(final CubeSet draw) {
            draws.add(draw);
        }

        boolean possibleWith(final CubeSet balls) {
            return draws.stream().allMatch(d -> d.lessThanOrEqualTo(balls));
        }

        CubeSet minimumCubeSet() {
            return draws.stream().reduce(CubeSet.EMPTY, CubeSet::maximum);
        }
    }

    record CubeSet(int red, int green, int blue) {

        static final CubeSet EMPTY = new CubeSet(0, 0, 0);

        boolean lessThanOrEqualTo(final CubeSet other) {
            return red <= other.red && green <= other.green && blue <= other.blue();
        }

        CubeSet maximum(final CubeSet other) {
            return new CubeSet(max(red, other.red), max(green, other.green), max(blue, other.blue));
        }

        long power() {
            return ((long) red) * green * blue;
        }
    }
}
