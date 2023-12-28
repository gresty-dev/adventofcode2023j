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
        final var answer = loadPatterns(input).stream().mapToInt(AshRockPattern::findSmudgedReflection).sum();
        return String.valueOf(answer);
    }

    private List<AshRockPattern> loadPatterns(final PuzzleInput input) {
        return input.multiLineObjects(String::isBlank,
                AshRockPatternFactory::new,
                AshRockPatternFactory::addLine,
                AshRockPatternFactory::build);
    }

    static class AshRockPatternFactory {
        final List<String> lines = new ArrayList<>();

        void addLine(final String line) {
            lines.add(line);
        }

        AshRockPattern build() {
            final var rowCount = lines.size();
            final var colCount = lines.getFirst().length();
            var org = new int[rowCount][colCount];
            var transpose = new int[colCount][rowCount];
            for (int r = 0; r < rowCount; r++) {
                final var line = lines.get(r);
                for (int c = 0; c < colCount; c++) {
                    var val = line.charAt(c) == '#' ? 1 : 0;
                    org[r][c] = val;
                    transpose[c][r] = val;
                }
            }
            return new AshRockPattern(toLongArray(org), toLongArray(transpose));
        }

        final long[] toLongArray(final int[][] lines) {
            final var longArray = new long[lines.length];
            for (int i = 0; i < lines.length; i++) {
                longArray[i] = toLong(lines[i]);
            }
            return longArray;
        }

        long toLong(final int[] values) {
            var longValue = 0L;
            for (int value : values) {
                longValue = (longValue << 1) + value;
            }
            return longValue;
        }
    }

    static class AshRockPattern {
        static final int MAX_LENGTH = 30;
        static final long[] MASKS = new long[MAX_LENGTH];
        static {
            var mask = 1L;
            for (int i = 0; i < MAX_LENGTH; i++) {
                MASKS[i] = mask;
                mask = mask << 1;
            }
        }

        final int rowCount;
        final int colCount;
        final long[] lines;
        final long[] transpose;
        final int verticalReflection;
        final int horizontalReflection;

        AshRockPattern(long[] lines, long[] transpose) {
            this.lines = lines;
            this.transpose = transpose;
            this.rowCount = lines.length;
            this.colCount = transpose.length;
            this.verticalReflection = findVerticalReflection(lines, -1);
            this.horizontalReflection = findVerticalReflection(transpose, -1);
        }

        int findReflection() {
            var reflection = 100 * (verticalReflection + 1);
            if (reflection == 0) {
                reflection = horizontalReflection + 1;
            }
            return reflection;
        }

        int findSmudgedReflection() {
            for (var row = 0; row < rowCount; row++) {
                for (var col = 0; col < colCount; col++) {
                    smudge(row, col);
                    var smudgedReflection = 100 * (findVerticalReflection(lines, verticalReflection) + 1);
                    if (smudgedReflection == 0) {
                        smudgedReflection = findVerticalReflection(transpose, horizontalReflection) + 1;
                    }
                    if (smudgedReflection > 0) {
                        return smudgedReflection;
                    }
                    smudge(row, col);
                }
            }
            return 0;
        }

        void smudge(final int row, final int col) {
            final var colMask = MASKS[colCount - col - 1];
            final var rowMask = MASKS[rowCount - row - 1];
            final boolean isSet = (lines[row] & colMask) > 0;
            lines[row] = isSet ? lines[row] - colMask : lines[row] + colMask;
            transpose[col] = isSet ? transpose[col] - rowMask : transpose[col] + rowMask;
        }

        int findVerticalReflection(final long[] pattern, final int except) {
            for (int r = 0; r < pattern.length - 1; r++) {
                if (r != except && isVerticalReflection(pattern, r)) {
                    return r;
                }
            }
            return -1;
        }

        boolean isVerticalReflection(final long[] pattern, final int mirror) {
            for (int i = 0; i <= mirror; i++) {
                if (mirror + 1 + i >= pattern.length) {
                    return true;
                }
                if (pattern[mirror - i] != pattern[mirror + 1 + i]) {
                    return false;
                }
            }
            return true;
        }
    }
}
