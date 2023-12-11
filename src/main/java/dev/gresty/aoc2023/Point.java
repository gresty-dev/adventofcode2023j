package dev.gresty.aoc2023;

import java.util.List;

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
