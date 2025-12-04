package com.github.mideo.exercises;


import com.github.mideo.InputReader;

import java.util.List;

@DailyExercise
public class DayThree implements Exercise<Integer, Integer> {
  record BatteryBank(String joltages) {
    // Bruteforce, eek!
    int maxJoltage() {
      int max = 0;
      for (int i = 0; i < joltages.length(); i++) {
        for (int j = i + 1; j < joltages.length(); j++) {
          int left = Character.getNumericValue(joltages.charAt(i));
          int right = Character.getNumericValue(joltages.charAt(j));
          int num = left * 10 + right;

          if (num > max) max = num;
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
  public Integer partTwo() {
    return 0;
  }
}
