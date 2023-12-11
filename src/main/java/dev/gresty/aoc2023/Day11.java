package dev.gresty.aoc2023;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.util.ArrayList;
import java.util.List;

public class Day11 implements IPuzzle {

    public static void main(String[] args) {
        Main.execute(11);
    }

    private final PuzzleInput input;

    public Day11(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        return "Not implemented";
    }

    public String part2() {
        return "Not implemented";
    }

    class Universe {
        final List<Point> galaxies = new ArrayList<>();
        final IntList rowOffsets = new IntArrayList();
        final IntList colOffsets = new IntArrayList();

        Universe(final PuzzleInput input) {
            final boolean[] galaxyColumn = new boolean[input.lineLength()];
            for (int r = 0; r < input.lineCount(); r++) {
                final var line = input.line(r);
                final int offset = r > 0 ? rowOffsets.getInt(r - 1) : 0;
                if (!line.contains("#")) {
                    rowOffsets.add(1 + offset);
                } else {
                    for (int c = 0; c < line.length(); c++) {
                        if (line.charAt(c) == '#') {
                            galaxies.add(new Point(r, c));
                            galaxyColumn[c] = true;
                        }
                    }
                    rowOffsets.add(offset);
                }
            }
            for (int c = 0; c < input.lineLength(); c++) {
                final int offset = c > 0 ? colOffsets.getInt(c - 1) : 0;
                colOffsets.add(offset + (galaxyColumn[c] ? 0 : 1));
            }
        }
    }

}
