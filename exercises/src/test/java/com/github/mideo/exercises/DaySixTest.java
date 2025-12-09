package com.github.mideo.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DaySixTest {

    @Test
    void mathOperatorFromChar() {
        assertEquals(DaySix.MathOperator.ADD, DaySix.MathOperator.fromChar('+'));
        assertEquals(DaySix.MathOperator.MULTIPLY, DaySix.MathOperator.fromChar('*'));
    }

    @Test
    void mathOperatorAdd() {
        assertEquals(3L, DaySix.MathOperator.ADD.apply(1L, 2L));
    }

    @Test
    void mathOperatorMultiply() {
        assertEquals(6L, DaySix.MathOperator.MULTIPLY.apply(2L, 3L));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 33210",
            "2, 490",
            "3, 4243455",
            "4, 401"
    })
    void evaluateSingleLine(int verticalLineNumber, long expected) {
        var input = """
                123 328  51 64
                 45 64  387 23
                  6 98  215 314
                *   +   *   +
                """;

        List<String> split = Arrays.asList(input.replaceAll(" +", " ").split("\n"));

        List<DaySix.MathOperator> operators = Arrays.stream(split.getLast().trim().split(" "))
                .map(c -> c.charAt(0))
                .map(DaySix.MathOperator::fromChar)
                .toList();
        List<List<Integer>> numbers = split.subList(0, split.size() - 1)
                .stream()
                .map(line -> Arrays.stream(line.trim().split(" ")).map(Integer::parseInt).toList())
                .toList();


        var worksheet = new DaySix.WorkSheet(numbers, operators);
        assertEquals(expected, worksheet.evaluateVerticalSingleLine(verticalLineNumber));
    }

    @Test
    void evaluateVertical() {
        var input = "123 328  51 64 \n 45 64  387 23 \n  6 98  215 314\n*   +   *   +";

        List<String> split = Arrays.asList(input.replaceAll(" +", " ").split("\n", -1));

        List<DaySix.MathOperator> operators = Arrays.stream(split.getLast().trim().split(" "))
                .map(c -> c.charAt(0))
                .map(DaySix.MathOperator::fromChar)
                .toList();
        List<List<Integer>> numbers = split.subList(0, split.size() - 1)
                .stream()
                .map(line -> Arrays.stream(line.trim().split(" ")).map(Integer::parseInt).toList())
                .toList();


        var worksheet = new DaySix.WorkSheet(numbers, operators);
        assertEquals(4277556L, worksheet.evaluateVertical(operators.size()));
    }

    @Test
    void evaluateWithCephalopodMath() {
        var input = "123 328  51 64 \n 45 64  387 23 \n  6 98  215 314\n*   +   *   +";

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

        var worksheet = new DaySix.WorkSheet(numbers, operators);
        assertEquals(1058, worksheet.evaluateHorizontalSingleLine(1));
        assertEquals(3253600, worksheet.evaluateHorizontalSingleLine(2));
        assertEquals(625, worksheet.evaluateHorizontalSingleLine(3));
        assertEquals(8544, worksheet.evaluateHorizontalSingleLine(4));
        assertEquals(3263827L, worksheet.evaluateHorizontal(operators.size()));
    }

    @Test
    void name() {
        assertEquals("Day 6: Trash Compactor", new DaySix().name());
    }

    @Test
    void partOne() {
        assertEquals(4722948564882L, new DaySix().partOne());
    }

    @Test
    void partTwo() {
        assertEquals(9581313737063L, new DaySix().partTwo());
    }
}