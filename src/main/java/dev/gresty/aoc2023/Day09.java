package dev.gresty.aoc2023;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day09 implements IPuzzle {

    private final PuzzleInput input;

    public Day09(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        final var answer = input.lines().map(Day09::values)
                .mapToLong(v -> Day09.getValue(v, v.length + 1))
                .sum();
        return String.valueOf(answer);
    }

    public String part2() {
        final var answer = input.lines().map(Day09::values)
                .mapToLong(v -> Day09.getValue(v, 0))
                .sum();
        return String.valueOf(answer);
    }

    static int[] values(final String line) {
        return Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
    }
    static long getValue(int[] values, int elementNo) {
        int[] variables = variables(values);
        long[] coefficients = coefficients(variables.length, elementNo);
        return IntStream.range(0, variables.length)
                .mapToLong(i -> variables[i] * coefficients[i])
                .sum();
    }

    static int[] variables(int[] values) {
        final var variables = new IntArrayList();
        do {
            variables.add(values[0]);
            values = diff(values);
        } while (!allZero(values));
        return variables.toIntArray();
    }

    static int[] diff(int[] values) {
        final var diff = new int[values.length - 1];
        for (int i = 0; i < values.length - 1; i++) {
            diff[i] = values[i + 1] - values[i];
        }
        return diff;
    }

    static boolean allZero(final int[] values) {
        for (int value : values) {
            if (value != 0) return false;
        }
        return true;
    }

    /*

    10  13  16  21  30  45
    3   3   5   9   15
 -2  0   2   4   6
 2   2   2   2
 0   0   0
     */

    static long[] coefficients(int size, int elementNo) {
        return pascalUsingBinomialExpansion(elementNo, size).toLongArray();
    }

    public static LongList pascalUsingBinomialExpansion(int row, int numTerms) {
        long k = 1;
        final var terms = new LongArrayList(numTerms);
        terms.add(k);
        if (row == 0) {
            for (int i = 1; i < numTerms; i++) { // cheat! but no need to calculate negative rows here.
                k = -k;
                terms.add(k);
            }
        } else {
            for (int i = 1; i < Math.min(row, numTerms); i++) {
                k = k * (row - i) / i;
                terms.add(k);
            }
        }
        return terms;
    }

    public static void main(String[] args) {
        Main.execute(9);
    }
}
