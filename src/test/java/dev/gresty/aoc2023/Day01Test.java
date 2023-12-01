package dev.gresty.aoc2023;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Day01Test {

    private static final String EXAMPLE_A = """
            1abc2
            pqr3stu8vwx
            a1b2c3d4e5f
            treb7uchet""";

    private static final String EXAMPLE_B = """
            two1nine
            eightwothree
            abcone2threexyz
            xtwone3four
            4nineeightseven2
            zoneight234
            7pqrstsixteen""";

    @Test
    public void testExampleA() {
        assertThat(new Day01(PuzzleInput.of(EXAMPLE_A)).executeA()).isEqualTo("142");
    }

    @Test
    public void testExampleB() {
        assertThat(new Day01(PuzzleInput.of(EXAMPLE_B)).executeB()).isEqualTo("281");
    }

    @Test
    public void testFirstDigit() {
        assertThat(Day01.firstDigit("1abc2", Day01::isDigitA)).isEqualTo(1);
        assertThat(Day01.firstDigit("pqr3stu8vwx", Day01::isDigitA)).isEqualTo(3);
        assertThat(Day01.firstDigit("a1b2c3d4e5f", Day01::isDigitA)).isEqualTo(1);
        assertThat(Day01.firstDigit("treb7uchet", Day01::isDigitA)).isEqualTo(7);
    }

    @Test
    public void testLastDigit() {
        assertThat(Day01.lastDigit("1abc2", Day01::isDigitA)).isEqualTo(2);
        assertThat(Day01.lastDigit("pqr3stu8vwx", Day01::isDigitA)).isEqualTo(8);
        assertThat(Day01.lastDigit("a1b2c3d4e5f", Day01::isDigitA)).isEqualTo(5);
        assertThat(Day01.lastDigit("treb7uchet", Day01::isDigitA)).isEqualTo(7);
    }
}
