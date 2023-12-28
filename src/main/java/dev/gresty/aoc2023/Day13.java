package dev.gresty.aoc2023;

import java.util.ArrayList;
import java.util.List;

public class Day13 implements IPuzzle {

    public static void main(String[] args) {
        Main.execute(13);
    }

    private final PuzzleInput input;

    public Day13(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        final var answer = loadPatterns(input).stream().mapToInt(AshRockPattern::findReflection).sum();
        return String.valueOf(answer);
    }

    public String part2() {
        return "Not implemented";
    }

    private List<AshRockPattern> loadPatterns(final PuzzleInput input) {
        return input.multiLineObjects(String::isBlank, AshRockPattern::new, AshRockPattern::addLine);
    }

    static class AshRockPattern {
        final List<String> lines = new ArrayList<>();

        void addLine(final String line) {
            lines.add(line);
        }

        int findReflection() {
            var reflection = 100 * (findVerticalReflection(lines) + 1);
            if (reflection == 0) {
                reflection = findVerticalReflection(transpose()) + 1;
            }
            return reflection;
        }

        List<String> transpose() {
            final List<String> transpose = new ArrayList<>();
            for (int row = 0; row < lines.getFirst().length(); row++) {
                final var builder = new StringBuilder();
                for (String s : lines) {
                    builder.append(s.charAt(row));
                }
                transpose.add(builder.toString());
            }
            return transpose;
        }

        int findVerticalReflection(final List<String> pattern) {
            for (int r = 0; r < pattern.size() - 1; r++) {
                if (isVerticalReflection(pattern, r)) {
                    return r;
                }
            }
            return -1;
        }

        boolean isVerticalReflection(final List<String> pattern, final int mirror) {
            for (int i = 0; i <= mirror; i++) {
                if (mirror + 1 + i >= pattern.size()) {
                    return true;
                }
                if (!pattern.get(mirror - i).equals(pattern.get(mirror + 1 + i))) {
                    return false;
                }
            }
            return true;
        }
    }

}
