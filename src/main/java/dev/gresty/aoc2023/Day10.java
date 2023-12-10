package dev.gresty.aoc2023;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;

import static dev.gresty.aoc2023.Day10.PipeType.*;
import static dev.gresty.aoc2023.Day10.Point.*;

public class Day10 implements IPuzzle {

    public static void main(String[] args) {
        Main.execute(10);
    }

    private final PuzzleInput input;
    private final Point limit;

    public Day10(final PuzzleInput input) {
        this.input = input;
        this.limit = new Point(input.lineCount(), input.lineLength());
    }

    public String part1() {
        final var start = findStart();
        var forward = start.forward();
        var forwardPrev = start;
        var backward = start.backward();
        var backwardPrev = start;
        var distance = 1;
        while (!forward.equals(backward)) {
            forward.distance(distance);
            backward.distance(distance);
            distance++;

            var prev = forward;
            forward = forward.next(forwardPrev);
            forwardPrev = prev;

            prev = backward;
            backward = backward.next(backwardPrev);
            backwardPrev = prev;
        }

        return String.valueOf(distance);
    }

    public String part2() {
        final var start = findStart().mainLoop(true);
        var prev = start;
        var current = start.forward().mainLoop(true);
        while (!current.equals(start)) {
            var newPrev = current;
            current = current.next(prev).mainLoop(true);
            prev = newPrev;
        }

        var inCount = 0;
        for (int row = 0; row < limit.row(); row++ ) {
            var in = false;
            PipeType foundCorner = null;
            for (var col = 0; col < limit.col(); col++) {
                final var pipe = getPipe(new Point(row, col));
                if (pipe.mainLoop) {
                    if (foundCorner == NE) {
                        if (pipe.type == SW) {
                            in = !in;
                            foundCorner = null;
                        } else if (pipe.type == NW) {
                            foundCorner = null;
                        }
                    } else if (foundCorner == SE) {
                        if (pipe.type == NW) {
                            in = !in;
                            foundCorner = null;
                        } else if (pipe.type == SW) {
                            foundCorner = null;
                        }
                    } else if (pipe.type == NS) {
                        in = !in;
                    } else if (pipe.type == NE || pipe.type == SE) {
                        foundCorner = pipe.type;
                    }
                } else if (in) {
                    inCount++;
                }
            }
        }
        return String.valueOf(inCount);
    }

    Pipe findStart() {
        for (var row = 0; row < input.lineCount(); row++) {
            final var col = input.line(row).indexOf('S');
            if (col > -1) {
                return determineStartPipe(new Point(row, col));
            }
        }
        throw new RuntimeException("Cannot find start pipe");
    }

    Pipe determineStartPipe(final Point start) {
        final List<Point> connectedDirections = new ArrayList<>();
        if (checkConnectedTo(start.move(N), S)) {
            connectedDirections.add(N);
        }
        if (checkConnectedTo(start.move(S), N)) {
            connectedDirections.add(S);
        }
        if (checkConnectedTo(start.move(E), W)) {
            connectedDirections.add(E);
        }
        if (checkConnectedTo(start.move(W), E)) {
            connectedDirections.add(W);
        }
        final var startPipe = new Pipe(start, PipeType.forDirections(connectedDirections));
        pipes.put(start, startPipe);
        return startPipe;
    }

    boolean checkConnectedTo(final Point location, final Point fromDirection) {
        return location.inRange(limit) && getPipe(location).type.connectedTo(fromDirection);
    }

    final Map<Point, Pipe> pipes = new HashMap<>();

    Pipe getPipe(final Point location) {
        var pipe = pipes.get(location);
        if (pipe == null) {
            final var type = PipeType.forSymbol(input.line(location.row).charAt(location.col));
            pipe = new Pipe(location, type);
            pipes.put(location, pipe);
        }
        return pipe;
    }


    @RequiredArgsConstructor
    enum PipeType {
        NS(N, S), EW(E, W), NE(N, E), SE(S, E), NW(N, W), SW(S, W), G(null, null);

        static final PipeType[][] pipeTypeForDirections = new PipeType[][] {
                       /*  E    N    S    W   */
                /* E */  { null, NE, SE, EW },
                /* N */  { NE, null, NS, NW },
                /* S */  { SE, NS, null, SW },
                /* W */  { EW, NW, SW, null }
        };

        final Point backward;
        final Point forward;

        boolean connectedTo(final Point direction) {
            return backward == direction || forward == direction;
        }

        static PipeType forDirections(final List<Point> dirs) {
            return pipeTypeForDirections[DIRECTIONS.indexOf(dirs.get(0))][DIRECTIONS.indexOf(dirs.get(1))];
        }

        static PipeType forSymbol(final char symbol) {
            return switch (symbol) {
                case '|' -> NS;
                case '-' -> EW;
                case 'L' -> NE;
                case 'F' -> SE;
                case 'J' -> NW;
                case '7' -> SW;
                case '.' -> G;
                default -> throw new RuntimeException("Unknown symbol");
            };
        }
    }

    record Point(int row, int col) {
        static final Point N = new Point(-1, 0);
        static final Point S = new Point(1, 0);
        static final Point E = new Point(0, 1);
        static final Point W = new Point(0, -1);

        static final List<Point> DIRECTIONS = List.of(E, N, S, W);

        Point move(final Point direction) {
            return new Point(row + direction.row, col + direction.col);
        }

        boolean inRange(final Point limit) {
            return row >= 0 && col >= 0 && row < limit.row && col < limit.col;
        }
    }

    @RequiredArgsConstructor
    @Getter
    @EqualsAndHashCode
    class Pipe {
        final Point location;
        final PipeType type;

        @Setter
        @EqualsAndHashCode.Exclude
        int distance;

        @Setter
        @EqualsAndHashCode.Exclude
        boolean mainLoop;

        Pipe backward() {
            return getPipe(location.move(type.backward));
        }

        Pipe forward() {
            return getPipe(location.move(type.forward));
        }

        Pipe next(final Pipe previous) {
            if (previous.equals(backward())) {
                return forward();
            }
            return backward();
        }
    }
}
