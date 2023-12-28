package dev.gresty.aoc2023;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 implements IPuzzle {

    public static void main(String[] args) {
        Main.execute(14);
    }

    private final PuzzleInput input;

    public Day14(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        final var platform = new Platform(input.as2dCharArray());
        final var answer = platform.tiltNorth();
        return String.valueOf(answer);
    }

    public String part2() {
        final var platform = new Platform(input.as2dCharArray());
        final var answer = platform.loadAfterCycles(1_000_000_000);
        return String.valueOf(answer);
    }

    static class Platform {
        final char[][] array;

        final List<State> states = new ArrayList<>();

        Platform(char[][] array) {
            this.array = array;
        }

        int loadAfterCycles(final int count) {
            for (int i = 0; i < count; i++) {
                final var load = cycle();
                final var newState = State.stateFor(array, load);
                final var cycleStart = states.indexOf(newState);
                if (cycleStart >= 0) {
                    var cycleLength = i - cycleStart;
                    var finalState = (count - cycleStart - 1) % cycleLength + cycleStart;
                    return states.get(finalState).load;
                } else {
                    states.add(newState);
                }
            }
            return states.getLast().load;
        }

        int cycle() {
            tiltNorth();
            tiltWest();
            tiltSouth();
            return tiltEast();
        }

        int tiltNorth() {
            var load = 0;
            for (var c = 0; c < array[0].length; c++) {
                load += tiltColumnNorth(c);
            }
            return load;
        }

        void tiltSouth() {
            for (var c = 0; c < array[0].length; c++) {
                tiltColumnSouth(c);
            }
        }

        void tiltWest() {
            for (var r = 0; r < array.length; r++) {
                tiltRowWest(r);
            }
        }

        int tiltEast() {
            var load = 0;
            for (var r = 0; r < array.length; r++) {
                load += tiltRowEast(r);
            }
            return load;
        }

        int tiltColumnNorth(final int col) {
            var load = 0;
            var emptySpace = 0;
            for (var row = 0; row < array.length; row++) {
                if (array[row][col] == 'O') {
                    if (emptySpace < row) {
                        array[row][col] = '.';
                        array[emptySpace][col] = 'O';
                        load += load(emptySpace);
                        emptySpace++;
                    } else {
                        load += load(row);
                        emptySpace = row + 1;
                    }
                } else if (array[row][col] == '#') {
                    if (emptySpace <= row) {
                        emptySpace = row + 1;
                    }
                }
            }
            return load;
        }

        void tiltColumnSouth(final int col) {
            var from = array.length - 1;
            var to = 0;
            var emptySpace = from;
            for (var row = from; row >= to; row--) {
                if (array[row][col] == 'O') {
                    if (emptySpace > row) {
                        array[row][col] = '.';
                        array[emptySpace][col] = 'O';
                        emptySpace--;
                    } else {
                        emptySpace = row - 1;
                    }
                } else if (array[row][col] == '#') {
                    if (emptySpace >= row) {
                        emptySpace = row - 1;
                    }
                }
            }
        }

        void tiltRowWest(final int row) {
            var emptySpace = 0;
            for (var col = 0; col < array[0].length; col++) {
                if (array[row][col] == 'O') {
                    if (emptySpace < col) {
                        array[row][col] = '.';
                        array[row][emptySpace] = 'O';
                        emptySpace++;
                    } else {
                        emptySpace = col + 1;
                    }
                } else if (array[row][col] == '#') {
                    if (emptySpace <= col) {
                        emptySpace = col + 1;
                    }
                }
            }
        }

        int tiltRowEast(final int row) {
            var from = array[0].length - 1;
            var to = 0;
            var load = 0;
            var emptySpace = from;
            for (var col = from; col >= to; col--) {
                if (array[row][col] == 'O') {
                    if (emptySpace > col) {
                        array[row][col] = '.';
                        array[row][emptySpace] = 'O';
                        load += load(row);
                        emptySpace--;
                    } else {
                        load += load(row);
                        emptySpace = col - 1;
                    }
                } else if (array[row][col] == '#') {
                    if (emptySpace >= col) {
                        emptySpace = col - 1;
                    }
                }
            }
            return load;
        }

        int load(final int row) {
            return array.length - row;
        }
    }

    @RequiredArgsConstructor
    @EqualsAndHashCode(exclude = "load")
    static class State {
        final int hash;
        final char[][] array;
        final int load;

        static State stateFor(final char[][] array, final int load) {
            final var copy = new char[array.length][];
            for (int i = 0; i < array.length; i++) {
                copy[i] = Arrays.copyOf(array[i], array[i].length);
            }
            return new State(Arrays.deepHashCode(array), copy, load);
        }
    }
}
