package com.github.mideo.exercises;

import com.github.mideo.InputReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

@DailyExercise
public class DaySix implements Exercise<Long, Long> {
    enum MathOperator {
        ADD(Long::sum),
        MULTIPLY((a, b) -> a * b);
        private final BiFunction<Long, Long, Long> operatorFunction;

        MathOperator(BiFunction<Long, Long, Long> operatorFunction) {
            this.operatorFunction = operatorFunction;
        }

        Long apply(Long a, Long b) {
            return operatorFunction.apply(a, b);
        }

        static MathOperator fromChar(char c) {
            return switch (c) {
                case '+' -> ADD;
                case '*' -> MULTIPLY;
                default -> throw new IllegalArgumentException("Invalid operator");
            };
        }
    }

    record WorkSheet(List<List<Integer>> numbers, List<MathOperator> operators) {

        Long evaluateVerticalSingleLine(int verticalLineNumber) {
            var index = verticalLineNumber - 1;
            return numbers.stream()
                    .map(line -> line.get(index).longValue())
                    .reduce((acc, num) -> operators.get(index).apply(acc, num)).orElse(0L);

        }

        Long evaluateVertical(int width) {
            return IntStream.rangeClosed(1, width)
                    .mapToLong(this::evaluateVerticalSingleLine)
                    .sum();

        }

        Long evaluateHorizontalSingleLine(int lineNumber) {
            var index = lineNumber - 1;
            return numbers.get(index).stream().map(Long::valueOf)
                    .reduce((acc, num) -> operators.get(index).apply(acc, num)).orElse(0L);

        }

        Long evaluateHorizontal(int width) {
            return IntStream.rangeClosed(1, width)
                    .mapToLong(
                            this::evaluateHorizontalSingleLine)
                    .sum();

        }
    }

    @Override
    public String name() {
        return "Day 6: Trash Compactor";
    }

    @Override
    public Long partOne() {
        var input = InputReader.readInput("DaySixInput.txt").orElseThrow();
        List<String> split = Arrays.asList(input.replaceAll(" +", " ").split("\n"));

        List<DaySix.MathOperator> operators = Arrays.stream(split.getLast().trim().split(" "))
                .map(c -> c.charAt(0))
                .map(DaySix.MathOperator::fromChar)
                .toList();
        List<List<Integer>> numbers = split.subList(0, split.size() - 1)
                .stream()
                .map(line -> Arrays.stream(line.trim().split(" ")).map(Integer::parseInt).toList())
                .toList();
        return new WorkSheet(numbers, operators).evaluateVertical(operators.size());

    }

    @Override
    public Long partTwo() {
        var input = InputReader.readInput("DaySixInput.txt").orElseThrow();
        List<String> split = Arrays.asList(input.split("\n", -1));
        List<DaySix.MathOperator> operators = Arrays.stream(split.getLast().trim().split(" "))
                .filter(it -> !it.isEmpty())
                .map(c -> c.charAt(0))
                .map(DaySix.MathOperator::fromChar)
                .toList().reversed();

        var nums = split.subList(0, split.size() - 1);

        List<List<Integer>> numbers = new ArrayList<>();
        numbers.add(new ArrayList<>());
        for (int x = nums.getFirst().length() - 1; x >= 0; x--) {
            char[] temp = new char[nums.size()];
            for (int y = 0; y < nums.size(); y++) {
                temp[y] = nums.get(y).charAt(x);
            }
            var s = new String(temp);
            if (s.trim().isEmpty()) {
                numbers.add(new ArrayList<>());
            } else {
                numbers.getLast().add(Integer.parseInt(s.trim()));
            }

        }

        return new WorkSheet(numbers, operators).evaluateHorizontal(operators.size());
    }
}
