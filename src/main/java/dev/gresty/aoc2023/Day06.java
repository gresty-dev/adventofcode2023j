package dev.gresty.aoc2023;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day06 implements IPuzzle {

    private final PuzzleInput input;

    public Day06(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        final var times = numbersPart1(input.line(0));
        final var distances = numbersPart1(input.line(1));
        final var answer = IntStream.range(0, times.length)
                .mapToObj(i -> timesForDistance(times[i], distances[i]))
                .mapToLong(a -> a[1] - a[0] + 1)
                .reduce(1, (a, b) -> a * b);
        return String.valueOf(answer);
    }

    public String part2() {
        final var time = numbersPart2(input.line(0));
        final var distance = numbersPart2(input.line(1));
        final var t = timesForDistance(time, distance);
        return String.valueOf(t[1] - t[0] + 1);
    }


    int[] numbersPart1(final String line) {
        return Arrays.stream(line.substring(line.indexOf(":") + 1).trim().split("\\s+"))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    long numbersPart2(final String line) {
        return Long.parseLong(String.join("", line.substring(line.indexOf(":") + 1).trim().split("\\s+")));
    }

    long[] timesForDistance(long maxTime, long distance) {
        final var a = Math.sqrt(Math.pow(maxTime, 2) - 4 * distance);
        final var t1 = (maxTime + a) / 2;
        final var t2 = (maxTime - a) / 2;
        if (t1 < t2) {
            return doublesToLongs(t1, t2);
        }
        return doublesToLongs(t2, t1);
    }

    long[] doublesToLongs(double a, double b) {
        if (a % 1 == 0) a++;
        if (b % 1 == 0) b--;
        return new long[] { (long) Math.ceil(a), (long) Math.floor(b) };
    }

    /*
    d = (m - t) * t
    t2 - mt + d = 0
    t = m +- sqrt(m2 - 4d) / 2
     */

    public static void main(String[] args) {
        Main.execute(6);
    }
}
