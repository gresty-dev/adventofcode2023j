package dev.gresty.aoc2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day12 implements IPuzzle {

    public static void main(String[] args) {
        Main.execute(12);
    }

    private final PuzzleInput input;

    public Day12(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        var answer = input.lines().map(l -> RowCondition.parse(l, 1))
                .map(this::solve)
                .mapToInt(List::size)
                .sum();
        return String.valueOf(answer);
    }

    public String part2() {
        var answer = input.lines().map(l -> RowCondition.parse(l, 5))
                .map(this::solve)
                .mapToInt(List::size)
                .sum();
//        return String.valueOf(answer);
        return "Not implemented";
    }

    List<String> solve(final RowCondition rowCondition) {
        final var solutions = new ArrayList<String>();
        final int[] workingGroups = new int[rowCondition.damageGroups.length];
        final int workingSpringCount = rowCondition.length() - Arrays.stream(rowCondition.damageGroups).sum();
        final var workingMask = rowCondition.knownWorking();
        final var damagedMask = rowCondition.knownDamaged();

        setWorkingCountAtIndex(0, workingGroups, 0, workingSpringCount, counts -> {
            BitSet row = convertToBitSet(rowCondition.damageGroups, counts, rowCondition.length());
            if (isValid(row, workingMask, damagedMask, rowCondition.length())) {
                solutions.add(convertToString(row));
            }
        });
//        System.out.println(solutions.size());
        return solutions;
    }

    BitSet convertToBitSet(int[] damageGroups, int[] workingGroups, int length) {
        BitSet row = new BitSet(length);
        int bit = 0;
        for (int i = 0; i < damageGroups.length; i++) {
            bit += workingGroups[i];
            row.set(bit, bit + damageGroups[i]);
            bit += damageGroups[i];
        }
        return row;
    }

    boolean isValid(BitSet row, BitSet working, BitSet damaged, int length) {
        BitSet rowAndDamaged = ((BitSet) row.clone());
        rowAndDamaged.and(damaged);
        if (!rowAndDamaged.equals(damaged)) {
            return false;
        }
        BitSet rowAndWorking = ((BitSet) row.clone());
        rowAndWorking.flip(0, length);
        rowAndWorking.and(working);
        return rowAndWorking.equals(working);
    }

    String convertToString(BitSet row) {
        return row.stream().mapToObj(b -> b == 0 ? "." : "#").collect(Collectors.joining());
    }

    /*
    length = 4
    index = 2
    max = 6
    total = 3
    maxAtIndex = 6 - 3 - 1 (because one more index is remaining after this)
     */

    void setWorkingCountAtIndex(int index, int[] workingCounts, int total, int max, Consumer<int[]> onCompletion) {
        int min = (index == 0 ? 0 : 1);
        int maxAtIndex = max - total - (workingCounts.length - index - 1);
        for (int workingCount = min; workingCount <= maxAtIndex; workingCount++) {
            workingCounts[index] = workingCount;
            if (index + 1 < workingCounts.length) {
                setWorkingCountAtIndex(index + 1, workingCounts, total + workingCount, max, onCompletion);
            } else {
                onCompletion.accept(workingCounts);
            }
        }
    }

    record RowCondition(String springs, int[] damageGroups) {
        BitSet knownWorking() {
            return IntStream.range(0, springs.length())
                    .filter(i -> springs.charAt(i) == '.')
                    .collect(() -> new BitSet(length()), BitSet::set, BitSet::or);
        }

        BitSet knownDamaged() {
            return IntStream.range(0, springs.length())
                    .filter(i -> springs.charAt(i) == '#')
                    .collect(() -> new BitSet(length()), BitSet::set, BitSet::or);
        }

        int length() {
            return springs.length();
        }

        static RowCondition parse(String line, int multiplier) {
            String[] onSpace = line.split(" ");
            String rowString = onSpace[0] + ("?" + onSpace[0]).repeat(multiplier - 1);
            String damagedCountString = onSpace[1] + ("," + onSpace[1]).repeat(multiplier - 1);
            var damagedCounts = Arrays.stream(damagedCountString.split(",")).mapToInt(Integer::parseInt).toArray();
            return new RowCondition(rowString, damagedCounts);
        }
    }

}
