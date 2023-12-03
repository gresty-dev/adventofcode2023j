package dev.gresty.aoc2023;

import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class PuzzleInput {
    private final List<String> data;

    private PuzzleInput(final Stream<String> input) {
        data = input.toList();
    }

    public int lineLength() {
        return data.get(0).length();
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
