package dev.gresty.aoc2023;

import lombok.AllArgsConstructor;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Day16 implements IPuzzle {

    public static void main(String[] args) {
        Main.execute(16);
    }

    private final PuzzleInput input;

    public Day16(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        var contraption = new Contraption(input.as2dCharArray());
        var answer = contraption.energizeDefault();
        return String.valueOf(answer);
    }

    public String part2() {
        var contraption = new Contraption(input.as2dCharArray());
        var answer = contraption.energizeMax();
        return String.valueOf(answer);
    }

    static class Contraption {
        static final int UP = 1;
        static final int LEFT = 2;
        static final int DOWN = 4;
        static final int RIGHT = 8;

        final char[][] grid;
        final int width;
        final int height;

        Contraption(char[][] grid) {
            this.grid = grid;
            width = grid[0].length;
            height = grid.length;
        }

        int energizeDefault() {
            return new BeamPath(new Beam(0, -1, RIGHT)).energize();
        }

        int energizeMax() {
            var max = 0;
            for (var row = 0; row < height; row++) {
                max = Math.max(max, new BeamPath(new Beam(row, -1, RIGHT)).energize());
                max = Math.max(max, new BeamPath(new Beam(row, width, LEFT)).energize());
            }
            for (var col = 0; col < width; col++) {
                max = Math.max(max, new BeamPath(new Beam(-1, col, DOWN)).energize());
                max = Math.max(max, new BeamPath(new Beam(height, col, UP)).energize());
            }
            return max;
        }

        class BeamPath {
            final int[][] beamPaths;
            final Queue<Beam> beams;
            int energized;

            public BeamPath(final Beam initialBeam) {
                this.beamPaths = new int[height][width];
                this.beams = new LinkedBlockingQueue<>();
                beams.add(initialBeam);
            }

            boolean setBeamPath(final Beam beam) {
                var paths = beamPaths[beam.row][beam.column];
                if (paths == 0) {
                    energized++;
                }
                if (paths == (paths | beam.direction)) {
                    // we've been here before, in this direction
                    return false;
                }
                beamPaths[beam.row][beam.column] = paths | beam.direction;
                return true;
            }

            void enqueue(final Beam beam) {
                beams.add(beam);
            }

            int energize() {
                Beam beam;
                while ((beam = beams.poll()) != null) {
                    beam.move(this);
                }
                return energized;
            }
        }

        @AllArgsConstructor
        class Beam {
            int row;
            int column;
            int direction;

            void move(final BeamPath beamPath) {
                column += direction == LEFT ? -1 : direction == RIGHT ? 1 : 0;
                row += direction == UP ? -1 : direction == DOWN ? 1 : 0;
                if (row < 0 || row >= height || column < 0 || column >= width) {
                    return;
                }
                if (!beamPath.setBeamPath(this)) {
                    // we've been here before, in this direction
                    return;
                }
                var element = grid[row][column];
                if (element == '/') {
                    if (direction == LEFT || direction == RIGHT) turnLeft();
                    else turnRight();
                } else if (element == '\\') {
                    if (direction == LEFT || direction == RIGHT) turnRight();
                    else turnLeft();
                } else if (element == '-') {
                    if (direction == DOWN || direction == UP) {
                        direction = LEFT;
                        beamPath.enqueue(split());
                    }
                } else if (element == '|') {
                    if (direction == LEFT || direction == RIGHT) {
                        direction = UP;
                        beamPath.enqueue(split());
                    }
                }
                beamPath.enqueue(this);
            }

            void turnRight() {
                direction = direction == UP ? RIGHT : direction >> 1;
            }

            void turnLeft() {
                direction = direction == RIGHT ? UP : direction << 1;
            }

            Beam split() {
                int newDirection = direction == LEFT ? RIGHT :
                        direction == RIGHT ? LEFT :
                                direction == UP ? DOWN : UP;
                return new Beam(row, column, newDirection);
            }
        }
    }
}
