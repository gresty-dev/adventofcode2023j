package dev.gresty.aoc2023;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;

import java.util.*;
import java.util.stream.Collectors;

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
        var almanac = Almanac.parse(input);
        var answer = almanac.nearestLocationForSeedRanges();
        return String.valueOf(answer);
    }

    public static void main(String[] args) {
        Main.execute(5);
    }

    record Mapping(LongRange sourceRange, LongRange destinationRange) {

        boolean contains(final long source) {
            return sourceRange.contains(source);
        }

        long destination(final long source) {
            return source + offset();
        }

        long offset() {
            return destinationRange.start - sourceRange.start;
        }

        SplitRange map(final LongRange range) {
            return range.offsestRangeUsing(sourceRange, offset());
        }

        static Mapping parse(final String line) {
            final var values = Arrays.stream(line.split(" ")).mapToLong(Long::parseLong).toArray();
            return new Mapping(
                    new LongRange(values[1], values[1] + values[2]),
                    new LongRange(values[0], values[0] + values[2]));
        }
    }

    record Map(String name, List<Mapping> mappings) {
        long map(final long source) {
            return mappings.stream().filter(m -> m.contains(source))
                    .findFirst()
                    .map(m -> m.destination(source))
                    .orElse(source);
        }

        Set<LongRange> map(final LongRange range) {
            return recursiveMap(0, range);
        }

        private Set<LongRange> recursiveMap(final int mappingNumber, final LongRange range) {
            if (mappingNumber >= mappings.size()) {
                return Set.of(range);
            }
            final SplitRange split = mappings.get(mappingNumber).map(range);
            final var mapped = new HashSet<LongRange>();
            if (split.intersection != null) mapped.add(split.intersection);
            if (split.before != null) mapped.addAll(recursiveMap(mappingNumber + 1, split.before));
            if (split.after != null) mapped.addAll(recursiveMap(mappingNumber + 1, split.after));
            return mapped;
        }
    }

    record Almanac(LongList seeds, List<Map> maps) {

        Set<LongRange> seedRanges() {
            final var ranges = new HashSet<LongRange>();
            for (int i = 0; i < seeds.size(); i += 2) {
                ranges.add(new LongRange(seeds.getLong(i), seeds.getLong(i + 1) + seeds.getLong(i)));
            }
            return ranges;
        }

        long locationForSeed(final long seed) {
            var value = seed;
            for (var map : maps) {
                value = map.map(value);
            }
            return value;
        }

        long nearestLocationForSeedRanges() {
            return seedRanges().stream().flatMap(range -> recursivelyMapSeedRange(0, range).stream())
                    .collect(TreeSet<LongRange>::new, TreeSet::add, TreeSet::addAll)
                    .first()
                    .start;
        }

        Set<LongRange> recursivelyMapSeedRange(final int mapNumber, final LongRange seedRange) {
            if (mapNumber >= maps.size()) return Set.of(seedRange);
            final var mapped = maps.get(mapNumber).map(seedRange);
            return mapped.stream().flatMap(range -> recursivelyMapSeedRange(mapNumber + 1, range).stream())
                    .collect(Collectors.toSet());
        }

        static Almanac parse(final PuzzleInput input) {
            final var seeds = Arrays.stream(input.line(0).split(" ")).skip(1).mapToLong(Long::parseLong)
                    .collect(LongArrayList::new, LongArrayList::add, LongArrayList::addAll);
            final var maps = new ArrayList<Map>();
            var currentMapName = "";
            var currentMappings = new ArrayList<Mapping>();
            for (int i = 2; i < input.lineCount(); i++) {
                var line = input.line(i);
                if (line.contains(":")) {
                    currentMapName = line.substring(0, line.length() - 1);
                } else if (line.isEmpty()) {
                    maps.add(new Map(currentMapName, currentMappings));
                    currentMapName = "";
                    currentMappings = new ArrayList<>();
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

    record LongRange(long start, long end) implements Comparable<LongRange> {

        boolean contains(final long value) {
            return value >= start && value < end;
        }

        LongRange offset(final long offset) {
            return new LongRange(start + offset, end + offset);
        }

        SplitRange offsestRangeUsing(final LongRange splitter, final long offset) {
            if (end <= splitter.start) {
                return new SplitRange(this, null, null);
            }
            if (start >= splitter.end) {
                return new SplitRange(null, null, this);
            }
            if (start >= splitter.start && end <= splitter.end) {
                return new SplitRange(null,
                        new LongRange(start, end).offset(offset),
                        null);
            }
            if (start < splitter.start && end <= splitter.end) {
                return new SplitRange(new LongRange(start, splitter.start),
                        new LongRange(splitter.start, end).offset(offset),
                        null);
            }
            if (start >= splitter.start) {
                return new SplitRange(null,
                        new LongRange(start, splitter.end).offset(offset),
                        new LongRange(splitter.end, end));
            }
            return new SplitRange(new LongRange(start, splitter.start),
                    new LongRange(splitter.start, splitter.end).offset(offset),
                    new LongRange(splitter.end, end));
        }

        @Override
        public int compareTo(LongRange o) {
            return Long.compare(start, o.start);
        }
    }

    record SplitRange(LongRange before, LongRange intersection, LongRange after) {}
}
