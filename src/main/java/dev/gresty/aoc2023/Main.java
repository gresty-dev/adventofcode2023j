package dev.gresty.aoc2023;

import lombok.SneakyThrows;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    static void execute(final int day) {
        final IPuzzle puzzle = configurePuzzle(day);
        output(day, 1, time(puzzle::part1));
        output(day, 2, time(puzzle::part2));
    }

    @SneakyThrows
    private static IPuzzle configurePuzzle(final int day) {
        final String dayClassName = String.format("dev.gresty.aoc2023.Day%02d", day);
        final Class<IPuzzle> puzzleClass = (Class<IPuzzle>) Class.forName(dayClassName);
        final PuzzleInput input = PuzzleInput.load(day);
        return puzzleClass.getConstructor(PuzzleInput.class).newInstance(input);
    }

    private static Result time(final Supplier<String> puzzlePart) {
        final Instant start = Instant.now();
        final String answer = puzzlePart.get();
        final Instant end = Instant.now();
        final Duration duration = Duration.between(start, end);
        return new Result(answer, duration);
    }

    private static void output(final int day, final int part, final Result result) {
        System.out.println("Day " + day + ", part " + part + ": " + result.answer + " (" + result.time.toMillis() + ")");
    }

    record Result(String answer, Duration time) {}
}