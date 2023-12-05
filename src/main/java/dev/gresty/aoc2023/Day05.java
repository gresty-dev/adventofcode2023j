package dev.gresty.aoc2023;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day05 implements IPuzzle {

    private final PuzzleInput input;

    public Day05(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        var almanac = Almanac.parse(input);
        var answer = almanac.seeds.longStream()
                .map(almanac::locationForSeed)
                .min()
                .orElse(0);
        return String.valueOf(answer);
    }

    public String part2() {
        return "Not implemented";
    }

    public static void main(String[] args) {
        Main.execute(5);
    }

    record Mapping(long destinationStart, long sourceStart, long length) {

        boolean contains(final long source) {
            return source >= sourceStart && source < sourceStart + length;
        }

        long destination(final long source) {
            return source - sourceStart + destinationStart;
        }

        static Mapping parse(final String line) {
            final var values = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
            return new Mapping(values[0], values[1], values[2]);
        }
    }

    record Map(String name, Set<Mapping> mappings) {
        long map(final long source) {
            return mappings.stream().filter(m -> m.contains(source))
                    .findFirst()
                    .map(m -> m.destination(source))
                    .orElse(source);
        }
    }

    record Almanac(LongSet seeds, List<Map> maps) {

        long locationForSeed(final long seed) {
            var value = seed;
            for (var map : maps) {
                value = map.map(value);
            }
            return value;
        }

        static Almanac parse(final PuzzleInput input) {
            final var seeds = Arrays.stream(input.line(0).split(" ")).skip(1).mapToLong(Long::parseLong)
                    .collect(LongOpenHashSet::new, LongOpenHashSet::add, LongOpenHashSet::addAll);
            final var maps = new ArrayList<Map>();
            var currentMapName = "";
            var currentMappings = new HashSet<Mapping>();
            for (int i = 2; i < input.lineCount(); i++) {
                var line = input.line(i);
                if (line.contains(":")) {
                    currentMapName = line.substring(0, line.length() - 1);
                } else if (line.isEmpty()) {
                    maps.add(new Map(currentMapName, currentMappings));
                    currentMapName = "";
                    currentMappings = new HashSet<>();
                } else {
                    currentMappings.add(Mapping.parse(line));
                }
            }
            if (!currentMapName.isEmpty()) {
                maps.add(new Map(currentMapName, currentMappings));
            }
            return new Almanac(seeds, maps);
        }
    }
}
