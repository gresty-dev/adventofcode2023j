package dev.gresty.aoc2023;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day03 implements IPuzzle {

    private final PuzzleInput input;

    public Day03(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        final var answer = partStream().filter(this::isEnginePart).mapToInt(Part::id).sum();
        return String.valueOf(answer);
    }

    public String part2() {
        final var gearRatios = new HashMap<Gear, List<Integer>>();
        partStream().forEach(part -> getGearPart(part).ifPresent(value ->
                gearRatios.compute(value, (g, l) -> {
                    if (l == null) {
                        l = new ArrayList<>();
                    }
                    l.add(part.id);
                    return l;
                })));
        final var answer = gearRatios.values().stream()
                .filter(integers -> integers.size() == 2)
                .mapToInt(integers -> integers.get(0) * integers.get(1))
                .sum();
        return String.valueOf(answer);
    }

    public static void main(String[] args) {
        Main.execute(3);
    }

    boolean isEnginePart(final Part part) {
        for (int x = part.x - 1; x <= part.x + part.idLength; x++) {
            for (int y = part.y - 1; y <= part.y + 1; y++) {
                if (y >= 0 && y < input.lineCount() && x >= 0 && x < input.lineLength()) {
                    var c = input.line(y).charAt(x);
                    if (!Character.isDigit(c) && c != '.') {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    Optional<Gear> getGearPart(final Part part) {
        for (int x = part.x - 1; x <= part.x + part.idLength; x++) {
            for (int y = part.y - 1; y <= part.y + 1; y++) {
                if (y >= 0 && y < input.lineCount() && x >= 0 && x < input.lineLength()) {
                    var c = input.line(y).charAt(x);
                    if (c == '*') {
                        return Optional.of(new Gear(x, y));
                    }
                }
            }
        }
        return Optional.empty();
    }




    Stream<Part> partStream() {
        return IntStream.range(0, input.lineCount())
                .mapToObj(i -> new IndexedElement<>(i, input.line(i)))
                .flatMap(i -> partsInLine(i.index()));
    }

    Stream<Part> partsInLine(final int lineNumber) {
        final var line = input.line(lineNumber);
        final var parts = new ArrayList<Part>();
        var number = 0;
        var length = 0;
        for (int x = 0; x < line.length(); x++) {
            final var c = line.charAt(x);
            if (Character.isDigit(c)) {
                number = 10 * number + c - '0';
                length++;
            } else if (length > 0) {
                parts.add(new Part(number, length, x - length, lineNumber));
                length = 0;
                number = 0;
            }
        }
        if (length > 0) {
            parts.add(new Part(number, length, line.length() - length, lineNumber));
        }
        return parts.stream();
    }

    record Part(int id, int idLength, int x, int y) {}

    record Gear(int x, int y) {}

    record IndexedElement<T>(int index, T element) {}
}
