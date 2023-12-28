package dev.gresty.aoc2023;

import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class PuzzleInput {
    private final List<String> data;

    private PuzzleInput(final Stream<String> input) {
        data = input.toList();
    }

    public int lineLength() {
        return data.getFirst().length();
    }

    public int lineCount() {
        return data.size();
    }

    public String line(final int index) {
        return data.get(index);
    }

    Stream<String> lines() {
        return data.stream();
    }

    <T> Stream<T> objects(final Function<String, T> factory) {
        return lines().map(factory);
    }

    <F, T> List<T> multiLineObjects(
            final Predicate<String> isSeparator,
            final Supplier<F> newFactory,
            final BiConsumer<F, String> addToFactory,
            final Function<F, T> makeObject) {

        final var objects = new ArrayList<T>();
        final var iter = lines().iterator();
        F factory = null;
        while (iter.hasNext()) {
            final var line = iter.next();
            if (isSeparator.test(line)) {
                if (factory != null) {
                    objects.add(makeObject.apply(factory));
                    factory = null;
                }
            } else {
                if (factory == null) {
                    factory = newFactory.get();
                }
                addToFactory.accept(factory, line);
            }
        }
        if (factory != null) {
            objects.add(makeObject.apply(factory));
        }
        return objects;
    }

    char[][] as2dCharArray() {
        final var array = new char[lineCount()][];
        for (int r = 0; r < lineCount(); r++) {
            final var line = line(r);
            array[r] = line.toCharArray();
        }
        return array;
    }

    @SneakyThrows
    static PuzzleInput load(final int day) {
        final String filename = filename(day);
        URL resource = PuzzleInput.class.getResource(filename);
        if (resource == null)
            throw new RuntimeException("No such input file " + filename);
        return new PuzzleInput(Files.lines(Path.of(resource.toURI())));
    }

    static PuzzleInput of(final String multiLineString) {
        return new PuzzleInput(multiLineString.lines());
    }

    static String filename(final int day) {
        return String.format("%02d", day);
    }
}
