package dev.gresty.aoc2023;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day04 implements IPuzzle {

    private final PuzzleInput input;

    private int idStart;
    private int idEnd;
    private int winnersStart;
    private int winnerCount;
    private int selectedStart;
    private int selectedCount;

    public Day04(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        prepare();
        final var answer = input.lines().map(this::parse).mapToInt(Card::points).sum();
        return String.valueOf(answer);
    }

    public String part2() {
        prepare();
        final var cardCounts = new long[input.lineCount() + 1];
        Arrays.fill(cardCounts, 1);
        cardCounts[0] = 0;
        input.lines().map(this::parse)
                .forEach(card -> {
                    final var id = card.id;
                    for (int w = 1; w <= card.winCount(); w++) {
                        cardCounts[id + w] += cardCounts[id];
                    }
                });
        final var answer = Arrays.stream(cardCounts).sum();
        return String.valueOf(answer);
    }

    void prepare() {
        var line = input.line(0);
        idStart = line.indexOf(" ") + 1;
        idEnd = line.indexOf(":");
        winnersStart = idEnd + 2;
        int winnersEnd = line.indexOf("|");
        winnerCount = (winnersEnd - winnersStart) / 3;
        selectedStart = winnersEnd + 2;
        int selectedEnd = line.length();
        selectedCount = (selectedEnd - selectedStart) / 3 + 1;
    }

    Card parse(final String line) {
        final var id = Integer.parseInt(line.substring(idStart, idEnd).trim());
        final var winners = IntStream.range(0, winnerCount).map(i -> 3 * i + winnersStart)
                .map(i -> Integer.parseInt(line.substring(i, i + 2).trim()))
                .toArray();
        final var selected = IntStream.range(0, selectedCount).map(i -> 3 * i + selectedStart)
                .map(i -> Integer.parseInt(line.substring(i, i + 2).trim()))
                .toArray();

        return new Card(id, winners, selected);
    }

    public static void main(String[] args) {
        Main.execute(4);
    }

    record Card(int id, int[] winners, int[] selected) {

        int points() {
            final var winCount = (int) winCount();
            if (winCount == 0) return 0;
            return 1 << (winCount - 1);
        }

        long winCount() {
            int count = 0;
            for (var s : selected) {
                count += isWinner(s);
            }
            return count;
        }

        int isWinner(final int value) {
            for (var w : winners) {
                if (w == value) return 1;
            }
            return 0;
        }
    }
}
