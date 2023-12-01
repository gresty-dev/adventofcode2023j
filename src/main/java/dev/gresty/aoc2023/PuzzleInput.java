package dev.gresty.aoc2023;

import lombok.SneakyThrows;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class PuzzleInput {
    private final List<String> data;

    private PuzzleInput(final Stream<String> input) {
        data = input.toList();
    }

    Stream<String> lines() {
        return data.stream();
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