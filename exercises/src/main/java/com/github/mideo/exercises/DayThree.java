package com.github.mideo.exercises;


import com.github.mideo.InputReader;

import java.util.List;

@DailyExercise
public class DayThree implements Exercise<Integer, Long> {
  record BatteryBank(String joltages) {

//    long maxJoltage(int numberOfDigits) {return 0L;}


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
    return 0L;
  }
}
