package dev.gresty.aoc2023;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntMaps;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day08 implements IPuzzle {

    private final PuzzleInput input;

    public Day08(final PuzzleInput input) {
        this.input = input;
    }

    public String part1() {
        final var instructions = parseInstructions(input.line(0));
        final var network = Network.parse(input.lines().skip(2));
        return String.valueOf(pathLength(instructions, network.start, Node::isEnd));
    }

    public String part2() {
        final var instructions = parseInstructions(input.line(0));
        final var network = Network.parse(input.lines().skip(2));

        final var answer = network.startingNodes()
                .mapToInt(node -> pathLength(instructions, node, Node::isGhostEnd))
                .mapToObj(this::primeFactors)
                .reduce(Int2IntMaps.singleton(2, 0), this::lowestCommonMultiple)
                .int2IntEntrySet().stream()
                .mapToLong(e -> (long) Math.pow(e.getIntKey(), e.getIntValue()))
                .reduce(1, (a, b) -> a * b);

        return String.valueOf(answer);
    }

    int pathLength(final int[] instructions, final Node start, final Predicate<Node> endCondition) {
        var node = start;
        var steps = 0;
        while (!endCondition.test(node)) {
            for (final var instruction : instructions) {
                node = node.move(instruction);
                steps++;
                if (endCondition.test(node)) {
                    break;
                }
            }
        }
        return steps;
    }

    public Int2IntMap primeFactors(int number) {
        int n = number;
        Int2IntMap factors = new Int2IntOpenHashMap();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                factors.put(i, factors.get(i) + 1);
                n /= i;
            }
        }
        return factors;
    }

    public Int2IntMap lowestCommonMultiple(Int2IntMap a, Int2IntMap b) {
        final var result = new Int2IntOpenHashMap(a);
        b.keySet().forEach(k -> result.put(k, Math.max(b.get(k), result.get(k))));
        return result;
    }

    static int[] parseInstructions(final String line) {
        return line.chars().map(c -> c == 'L' ? 0 : 1).toArray();
    }

    public static void main(String[] args) {
        Main.execute(8);
    }

    record Network (Map<String, Node> nodesByName, Node start) {

        Stream<Node> startingNodes() {
            return nodesByName.values().stream().filter(Node::isGhostStart);
        }

        static Network parse(final Stream<String> lines) {
            final var nodesByName = new HashMap<String, Node>();
            lines.forEach(line -> {
                final var matcher = Node.PATTERN.matcher(line);
                if (!matcher.find()) {
                    throw new RuntimeException("Could not match " + line);
                }
                final var name = matcher.group(1);
                final var left = matcher.group(2);
                final var right = matcher.group(3);
                final var node = nodesByName.computeIfAbsent(name, Node::new);
                final var leftNode = nodesByName.computeIfAbsent(left, Node::new);
                final var rightNode = nodesByName.computeIfAbsent(right, Node::new);
                node.next[0] = leftNode;
                node.next[1] = rightNode;
            });
            return new Network(nodesByName, nodesByName.get("AAA"));
        }
    }

    @RequiredArgsConstructor
    @Getter
    static class Node {
        static final Pattern PATTERN = Pattern.compile("^([A-Z0-9]{3}) = \\(([A-Z0-9]{3}), ([A-Z0-9]{3})\\)$");

        final String name;
        final Node[] next = new Node[2];

        Node move(final int to) {
            return next[to];
        }

        boolean isGhostStart() {
            return name.endsWith("A");
        }

        boolean isGhostEnd() {
            return name.endsWith("Z");
        }

        boolean isEnd() {
            return name.equals("ZZZ");
        }
    }
}
