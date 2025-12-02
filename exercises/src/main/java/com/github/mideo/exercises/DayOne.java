package com.github.mideo.exercises;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.IntBinaryOperator;

@DailyExercise
public class DayOne implements Exercise<Integer, Integer> {
  static class CircularDail {
    private CircularDail() {
    }

    private static final int SIZE = 100;
    private static final Map<Character, IntBinaryOperator> directions = Map.of(
        'L', (pos, val) -> Math.floorMod(pos - val, SIZE),
        'R', (pos, val) -> Math.floorMod(pos + val, SIZE)
    );

    static int rotate(int currentPosition, String rotation) {
      var direction = rotation.charAt(0);
      var value = Integer.parseInt(rotation.substring(1));
      return directions.get(direction).applyAsInt(currentPosition, value);
    }

    static int rotate(int currentPosition, List<String> rotations) {
      int pos = currentPosition;
      int count = 0;
      for (String rotation : rotations) {
        pos = rotate(pos, rotation);
        if (pos == 0) count++;
      }
      return count;
    }
  }

  @Override
  public String name() {
    return "Day 1: Secret Entrance ";
  }


  @Override
  public Integer partOne() {
    return 0;
  }

  @Override
  public Integer partTwo() {
    return 0;
  }
}