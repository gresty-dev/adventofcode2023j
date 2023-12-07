package dev.gresty.aoc2023;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day07 implements IPuzzle {

    static final String CARDS = "23456789TJQKA";
    static final int JACK = Hand.cardValue('J');
    static final int JOKER = 0;

    private final PuzzleInput input;

    public Day07(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        return String.valueOf(execute(false));
    }

    public String part2() {
        return String.valueOf(execute(true));
    }

    long execute(final boolean withJokers) {
        final var ranked = input.lines()
                .map(line -> Hand.parse(line, withJokers))
                .sorted()
                .toArray(Hand[]::new);
        return IntStream.range(0, ranked.length)
                .mapToLong(i -> ((long) i + 1) * ranked[i].bid)
                .sum();
    }

    public static void main(String[] args) {
        Main.execute(7);
    }

    enum HandType {
        HIGH, ONE, TWO, THREE, FULL, FOUR, FIVE
    }

    record Hand(int[] cards, int bid, HandType type) implements Comparable<Hand> {

        static int cardValue(int c) {
            return CARDS.indexOf(c) + 1;
        }

        static Hand parse(final String line, boolean withJokers) {
            final var parts = line.split(" ");
            return create(parts[0].chars()
                            .map(Hand::cardValue)
                            .map(c -> (withJokers && c == JACK) ? JOKER : c) // convert Jokers (9) to -1
                            .toArray(),
                    Integer.parseInt(parts[1]), withJokers);
        }

        static Hand create(int[] cards, int bid, boolean withJokers) {
            final var counts = countCards(cards);
            final var jokers = withJokers ? counts[JOKER] : 0;
            if (jokers > 0) {
                counts[JOKER] = 0;
            }

            Arrays.sort(counts);
            final var maxIndex = counts.length - 1;
            counts[maxIndex] += jokers;
            return new Hand(cards, bid, determineType(counts[maxIndex], counts[maxIndex - 1]));
        }

        static HandType determineType(final int cardCount1, final int cardCount2) {
            if (cardCount1 == 5) return HandType.FIVE;
            if (cardCount1 == 4) return HandType.FOUR;
            if (cardCount1 == 3) {
                if (cardCount2 == 2) return HandType.FULL;
                return HandType.THREE;
            }
            if (cardCount1 == 2) {
                if (cardCount2 == 2) return HandType.TWO;
                return HandType.ONE;
            }
            return HandType.HIGH;
        }

        static int[] countCards(final int[] cards) {
            final var counts = new int[CARDS.length() + 1];
            for (var card : cards) {
                counts[card]++;
            }
            return counts;
        }

        @Override
        public int compareTo(Hand other) {
            if (type == other.type) {
                for (int i = 0; i < cards.length; i++) {
                    if (cards[i] != other.cards[i]) {
                        return Integer.compare(cards[i], other.cards[i]);
                    }
                }
                return 0;
            }
            return Integer.compare(type.ordinal(), other.type.ordinal());
        }
    }
}
