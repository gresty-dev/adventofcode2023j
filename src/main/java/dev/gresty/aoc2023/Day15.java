package dev.gresty.aoc2023;

import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Day15 implements IPuzzle {

    public static void main(String[] args) {
        Main.execute(15);
    }

    private final PuzzleInput input;

    public Day15(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        final var answer = input.csv().mapToInt(Day15::hash).sum();
        return String.valueOf(answer);
    }

    public String part2() {
        final var facility = new Facility();
        input.csv().map(this::toInstruction).forEach(i -> i.execute(facility));
        return String.valueOf(facility.focusingPower());
    }

    static int hash(final String string) {
        return string.chars().reduce(0, (acc, val) -> ((acc + val) * 17) % 256);
    }

    Instruction toInstruction(final String string) {
        if (string.indexOf('-') > -1) {
            return Remove.parse(string);
        }
        return SetFocalLength.parse(string);
    }

    static abstract class Instruction {
        final String label;

        Instruction(String label) {
            this.label = label;
        }

        abstract void execute(final Facility facility);
    }

    static class Remove extends Instruction {

        public Remove(final String label) {
            super(label);
        }

        @Override
        void execute(Facility facility) {
            facility.remove(label);
        }

        static Remove parse(final String string) {
            return new Remove(string.substring(0, string.length() - 1));
        }
    }

    static class SetFocalLength extends Instruction {

        final int focalLength;

        public SetFocalLength(String label, int focalLength) {
            super(label);
            this.focalLength = focalLength;
        }

        @Override
        void execute(Facility facility) {
            facility.setFocalLength(label, focalLength);
        }

        static SetFocalLength parse(final String string) {
            final var indexOfEquals = string.indexOf('=');
            final var label = string.substring(0, indexOfEquals);
            final var focalLength = Integer.parseInt(string.substring(indexOfEquals + 1));
            return new SetFocalLength(label, focalLength);
        }
    }

    static class Facility {
        final List<Box> boxes = new ArrayList<>();
        {
            for (int i = 0; i < 256; i++) {
                boxes.add(new Box());
            }
        }

        void remove(final String label) {
            boxes.get(hash(label)).remove(label);
        }

        void setFocalLength(final String label, final int focalLength) {
            boxes.get(hash(label)).setFocalLength(label, focalLength);
        }

        int focusingPower() {
            return IntStream.range(0, boxes.size())
                    .map(b -> (b + 1) * boxes.get(b).focusingPower())
                    .sum();
        }
    }

    @RequiredArgsConstructor
    static class Box {
        final LinkedList<Slot> slots = new LinkedList<>();

        void remove(final String label) {
            slots.remove(Slot.make(label));
        }

        void setFocalLength(final String label, final int focalLength) {
            final var newSlot = Slot.make(label, focalLength);
            final var index = slots.indexOf(newSlot);
            if (index == -1) {
                slots.add(newSlot);
            } else {
                slots.get(index).focalLength(focalLength);
            }
        }

        int focusingPower() {
            return IntStream.range(0, slots.size())
                    .map(s -> slots.get(s).focalLength * (s + 1))
                    .sum();
        }
    }

    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(exclude = "focalLength")
    static class Slot {
        final String name;
        @Setter
        int focalLength;

        static Slot make(final String label) {
            return new Slot(label);
        }

        static Slot make(final String label, final int focalLength) {
            return new Slot(label, focalLength);
        }
    }



}
