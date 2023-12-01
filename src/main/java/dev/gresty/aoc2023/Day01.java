package dev.gresty.aoc2023;

import java.util.stream.IntStream;

public class Day01 {

    private static final String[] DIGITS = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    private final PuzzleInput input;

    Day01(final PuzzleInput input) {
        this.input = input;
    }

    String executeA() {
        return Integer.toString(execute(Day01::isDigitA));
    }

    String executeB() {
        return Integer.toString(execute(Day01::isDigitB));
    }
    int execute(final IsDigit isDigit) {
        return input.lines()
                .mapToInt(line -> 10 * firstDigit(line, isDigit) + lastDigit(line, isDigit))
                .sum();
    }

    static int firstDigit(final String line, final IsDigit isDigit) {
        return findDigit(IntStream.range(0, line.length()), line, isDigit, "first digit");
    }

    static int lastDigit(final String line, final IsDigit isDigit) {
        final IntStream decreasing = IntStream.iterate(line.length() - 1, i -> i >= 0, i -> i - 1);
        return findDigit(decreasing, line, isDigit, "last digit");
    }

    static int findDigit(final IntStream indices, final String line, final IsDigit isDigit, final String desc) {
        return indices.map(i -> getDigit(line, i, isDigit))
                .filter(i -> i > 0)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Can't find " + desc + " in " + line));
    }

    static int getDigit(final String line, final int index, final IsDigit isDigit) {
        for (int i = 1; i <= 9; i++) {
            if (isDigit.test(line, index, i)) return i;
        }
        return -1;
    }

    static boolean isDigitA(final String line, final int index, final int digit) {
        return line.charAt(index) - '0' == digit;
    }

    static boolean isDigitB(final String line, final int index, final int digit) {
        if (isDigitA(line, index, digit)) return true;
        return line.startsWith(DIGITS[digit - 1], index);
    }

    public static void main(String[] args) {
        System.out.println(new Day01(PuzzleInput.load(1)).executeA());
        System.out.println(new Day01(PuzzleInput.load(1)).executeB());
    }

    interface IsDigit {
        boolean test(String line, int index, int digit);
    }
}
