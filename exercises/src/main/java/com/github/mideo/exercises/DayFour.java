package com.github.mideo.exercises;

import com.github.mideo.InputReader;

import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@DailyExercise
public class DayFour implements Exercise<Long, Long> {
    static char paperRollChar = '@';
    static char collectedPaperRollChar = 'x';
    static char dotChar = '.';

    record Grid(String input, int height, int width) {

        public Stream<Position> positionsWithChar(char value, BiPredicate<Grid, Position> predicate) {
            return positionsWithChar(value).filter(pos -> predicate.test(this, pos));
        }

        public Stream<Position> positionsWithChar(char value) {
            return IntStream.range(0, height)
                    .mapToObj(
                            y -> IntStream.range(0, width)
                                    .filter(x -> input.charAt(y * width + x) == value)
                                    .mapToObj(x -> new Position(y, x))
                    ).flatMap(Function.identity());

        }

        public char charAt(Position position) {
            return input.charAt(position.y * width + position.x);
        }

        public Grid update(Stream<Position> position, char dotChar) {
            String updated = position.reduce(
                    input,
                    (acc, pos) -> {
                        int index = pos.y * width + pos.x;
                        return acc.substring(0, index) + dotChar + acc.substring(index + 1);
                    },
                    (s1, s2) -> s2

            );
            return new Grid(updated, height, width);


        }

        public Stream<Position> positionsWithCharAndPredicateRepeatedUntilNoChange(char paperRollChar, BiPredicate<Grid, Position> predicate) {
            Grid g = this;
            Stream<Position> totalStream = Stream.empty();
            var currentList = g.positionsWithChar(paperRollChar, predicate).toList();

            while (!currentList.isEmpty()) {
                g = g.update(currentList.stream(), collectedPaperRollChar);
                totalStream = Stream.concat(totalStream, currentList.stream());
                currentList = g.positionsWithChar(paperRollChar, predicate).toList();
            }
            return totalStream;
        }

    }

    record Position(int y, int x) {
        Stream<Position> getAdjacentPositions(Grid grid) {
            return IntStream.rangeClosed(y - 1, y + 1)
                    .boxed()
                    .flatMap(y -> IntStream.rangeClosed(x - 1, x + 1)
                            .mapToObj(x -> new Position(y, x))
                    )
                    .filter(pos -> !pos.equals(this))
                    .filter(pos -> pos.y >= 0 && pos.y < grid.height)
                    .filter(pos -> pos.x >= 0 && pos.x < grid.width);
        }
    }


    @Override
    public String name() {
        return "Day 4: Printing Department";
    }

    BiPredicate<Grid, Position> predicate = (g, pos) -> pos.getAdjacentPositions(g)
            .filter(adjacent -> g.charAt(adjacent) == paperRollChar)
            .count() < 4;

    @Override
    public Long partOne() {
        var input = InputReader.readLines("DayFourInput.txt");
        var grid = new Grid(String.join("", input), input.size(), input.getFirst().length());
        return grid.positionsWithChar(paperRollChar, predicate).count();
    }

    @Override
    public Long partTwo() {
        var input = InputReader.readLines("DayFourInput.txt");
        var grid = new Grid(String.join("", input), input.size(), input.getFirst().length());
        return grid.positionsWithCharAndPredicateRepeatedUntilNoChange(paperRollChar, predicate).count();
    }
}
