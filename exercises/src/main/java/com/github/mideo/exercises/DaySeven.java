package com.github.mideo.exercises;

import com.github.mideo.InputReader;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@DailyExercise
public class DaySeven implements Exercise<Integer, Integer> {
    static char START_CHAR = 'S';
    static char TACHYON_SPLITTER = '^';

    record PositionInManifold(int y, int x) {
        PositionInManifold next() {
            return new PositionInManifold(y+1, x);
        }

        List<PositionInManifold> split() {
            return List.of(
                    new PositionInManifold(y, x + 1),
                    new PositionInManifold(y, x - 1)
            );
        }

    }

    record Manifold(String input, int width, int height) {
        public Manifold(String input) {
            this(sanitizeInput(input), sanitizeInputWidth(input), (input.length() / sanitizeInputWidth(input))-1);
        }


        char valueAt(PositionInManifold position) {
            return input.charAt(position.y() * width + position.x());
        }

        public int numberOfTachyonBeamSplits(PositionInManifold start, int moveCount, Set<PositionInManifold> splitPoints) {
            var position = start.next();
            for (int i = 1; i <= moveCount; i++) {
                if (position.y() * width + position.x() >= input.length()) break;
                if (valueAt(position) == TACHYON_SPLITTER) {
                    if (splitPoints.contains(position)) break;
                    splitPoints.add(position);
                    var split = position.split();
                    numberOfTachyonBeamSplits(split.get(0), moveCount - i, splitPoints);
                    numberOfTachyonBeamSplits(split.get(1), moveCount - i, splitPoints);
                    break;
                }
                position = position.next();

            }
            return splitPoints.size();

        }

        private static String sanitizeInput(String input) {
            return input.replaceAll("\n", "");
        }

        private static int sanitizeInputWidth(String input) {
            return input.indexOf("\n");
        }

        public int numberOfTachyonBeamDimensions(PositionInManifold start, int moveCount) {
            var splitPoints = new HashSet<PositionInManifold>();
            var endRows = IntStream.range(0, width)
                    .mapToObj(x -> new PositionInManifold(height-1, x))
                    .toList();

            numberOfTachyonBeamSplits(start, moveCount, splitPoints);
            var sorted = splitPoints.stream()
                    .sorted(Comparator.comparingInt(PositionInManifold::y))
                    .toList();

            return endRows.stream().mapToInt(end -> findPathsFromStartToEndViaSortedSplits(start, end, sorted)).sum();
        }

        private int findPathsFromStartToEndViaSortedSplits(PositionInManifold start, PositionInManifold end, List<PositionInManifold> sorted) {
            return 0;
        }


    }

    @Override
    public String name() {
        return "Day 7: Laboratories";
    }

    @Override
    public Integer partOne() {
        var input = InputReader.readInput("DaySevenInput.txt").orElseThrow();
        var manifold = new Manifold(input);
        var startPosition = new PositionInManifold(
                input.indexOf(DaySeven.START_CHAR) / manifold.width(),
                input.indexOf(DaySeven.START_CHAR) % manifold.width()
        );
        return manifold.numberOfTachyonBeamSplits(startPosition, manifold.height(), new HashSet<>());
    }

    @Override
    public Integer partTwo() {
        return 0;
    }
}
