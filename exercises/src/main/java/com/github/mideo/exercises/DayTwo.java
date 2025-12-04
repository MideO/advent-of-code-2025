package com.github.mideo.exercises;

import com.github.mideo.InputReader;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@DailyExercise
public class DayTwo implements Exercise<Long, Long> {
    @Override
    public String name() {
        return "Day 2: Gift Shop";
    }

    static LongStream stream(String range) {
        var values = Arrays.stream(range.split("-")).mapToLong(Long::parseLong).toArray();
        return LongStream.rangeClosed(values[0], values[values.length - 1]);
    }

    record Id(String value) {

        boolean isSequenceRepeated(int times) {
            if (value.length() % times != 0) return false;
            int half = value.length() / times;
            var subsstring = value.substring(0, half);
            return subsstring.repeat(times).equals(value);

        }

        boolean isSequenceRepeatedAtLeast(int times) {
            if (value.length() < times) return false;
            int half = Math.ceilDiv(value.length(), times);
            return IntStream.rangeClosed(1, half)
                    .anyMatch(index -> {
                                var computed = value.substring(0, index)
                                        .repeat(value.length() / index);
                                return computed.equals(value);
                            }
                    );
        }
    }


    record Ids(String[] values) {

        Stream<Id> getIdsWithRepeatedSequence(int numberOfRepeatedSequence) {
            return Arrays.stream(values)
                    .flatMap(
                            range -> {
                                var numbers = stream(range);
                                return numbers.mapToObj(number -> new Id(String.valueOf(number)))
                                        .filter(id -> id.isSequenceRepeated(numberOfRepeatedSequence));
                            }
                    );
        }

        Stream<Id> getIdsWithRepeatedSequenceOfAtLeast(int numberOfRepeatedSequence) {
            return Arrays.stream(values)
                    .flatMap(
                            range -> {
                                var numbers = stream(range);
                                return numbers.mapToObj(number -> new Id(String.valueOf(number)))
                                        .filter(id -> id.isSequenceRepeatedAtLeast(numberOfRepeatedSequence));
                            }
                    );
        }
    }

    private Long sumValid(Function<Ids, Stream<Id>> evaluator) {

        return InputReader
                .readLines("DayTwoInput.txt")
                .stream()
                .map(input -> new Ids(input.split(",")))
                .flatMap(evaluator)
                .mapToLong(id -> Long.parseLong(id.value))
                .sum();
    }

    @Override
    public Long partOne() {
        return sumValid(ids -> ids.getIdsWithRepeatedSequence(2));

    }

    @Override
    public Long partTwo() {
        return sumValid(ids -> ids.getIdsWithRepeatedSequenceOfAtLeast(2));
    }
}
