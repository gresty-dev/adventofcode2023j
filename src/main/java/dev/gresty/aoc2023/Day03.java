package dev.gresty.aoc2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day03 implements IPuzzle {

    private final PuzzleInput input;

    public Day03(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        return "Not implemented";
    }

    public String part2() {
        return "Not implemented";
    }

    public static void main(String[] args) {
        Main.execute(3);
    }

    Stream<Part> partStream() {
        IntStream.range(0, input.lineCount())
                .mapToObj(i -> new IndexedElement<>(i, input.line(i)))
                .flatMap(i -> partsInLine(i.index))
                .mapToInt(Part::id)
                .sum();
    }

    Stream<Part> partsInLine(final int lineNumber) {
        final var line = input.line(lineNumber);
        final var parts = new ArrayList<Part>();
        var number = 0;
        var length = 0;
        for (int x = 0; x < line.length(); x++) {
            final var c = line.charAt(x);
            if (Character.isDigit(c)) {
                number = 10 * number + 'c' - '0';
                length++;
            } else if (length > 0) {
                parts.add(new Part(number, x - length + 1, lineNumber));
                length = 0;
                number = 0;
            }
        }
        if (length > 0) {
            parts.add(new Part(number, line.length() - length, lineNumber));
        }
        return parts.stream();
    }

    record Part(int id, int x, int y) {}

    record IndexedElement<T>(int index, T element) {}
}
