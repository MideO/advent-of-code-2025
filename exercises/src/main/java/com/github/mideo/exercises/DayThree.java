package com.github.mideo.exercises;


import com.github.mideo.InputReader;

import java.util.ArrayDeque;
import java.util.List;
import java.util.stream.Collectors;

@DailyExercise
public class DayThree implements Exercise<Integer, Long> {
    record BatteryBank(String joltages) {
        private long queueToLong(ArrayDeque<Integer> queue) {
            return Long.parseLong(queue.stream().map(Object::toString).collect(Collectors.joining()));
        }

        long maxJoltage(int numberOfDigits) {

            var queue = new ArrayDeque<Integer>();
            int startIndex = 0;
            for (int i = 0; i < numberOfDigits; i++) {
                int maxLookAhead = joltages.length() - (numberOfDigits - i);

                int maxDigit = -1;
                int maxDigitIndex = startIndex;

                for (int j = startIndex; j <= maxLookAhead; j++) {
                    int currentDigit = Character.getNumericValue(joltages.charAt(j));
                    if (currentDigit > maxDigit) {
                        maxDigit = currentDigit;
                        maxDigitIndex = j;
                    }
                }

                queue.add(maxDigit);
                startIndex = maxDigitIndex + 1;
            }

            return queueToLong(queue);
        }


        int maxJoltage() {
            int max = 0;
            int maxDigitSoFar = -1;
            for (int i = 0; i < joltages.length(); i++) {
                int digit = Character.getNumericValue(joltages.charAt(i));

                if (maxDigitSoFar != -1) {
                    int num = maxDigitSoFar * 10 + digit;
                    if (num > max) max = num;
                }

                if (digit > maxDigitSoFar) {
                    maxDigitSoFar = digit;
                }
            }

            return max;
        }
    }

    static int maxSum(List<BatteryBank> batteryBanks) {
        return batteryBanks.stream()
                .mapToInt(BatteryBank::maxJoltage)
                .sum();

    }
    static long maxSum(List<BatteryBank> batteryBanks, int numberOfDigits) {
        return batteryBanks.stream()
                .mapToLong(bank -> bank.maxJoltage(numberOfDigits))
                .sum();

    }


    @Override
    public String name() {
        return "Day 3: Lobby";
    }

    @Override
    public Integer partOne() {
        var banks = InputReader
                .readLines("DayThreeInput.txt")
                .stream()
                .map(BatteryBank::new)
                .toList();

        return maxSum(banks);
    }

    @Override
    public Long partTwo() {
        var banks = InputReader
                .readLines("DayThreeInput.txt")
                .stream()
                .map(BatteryBank::new)
                .toList();

        return maxSum(banks, 12);
    }
}
