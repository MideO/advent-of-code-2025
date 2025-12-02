package com.github.mideo.exercises;

import com.github.mideo.InputReader;

import java.util.List;
import java.util.Map;

@DailyExercise
public class DayOne implements Exercise<Integer, Integer> {
  static class CircularDail {
    private CircularDail() {
    }

    private static final int START_POINT = 50;
    private static final int SIZE = 100;
    private static final Map<Character, Integer> directions = Map.of(
        'L', -1,
        'R', 1
    );

    static int rotate(int currentPosition, String rotation) {
      var direction = rotation.charAt(0);
      var value = Integer.parseInt(rotation.substring(1));
      int location = currentPosition + (value * directions.get(direction));
      return Math.floorMod(location, SIZE);
    }

    static Map.Entry<Integer, Integer> completeDailCycles(int currentPosition, String rotation) {
      char direction = rotation.charAt(0);
      int value = Integer.parseInt(rotation.substring(1));
      int step = directions.get(direction);

      int firstZeroClick = Math.floorMod(-currentPosition * step, SIZE);
      if (firstZeroClick == 0) firstZeroClick = SIZE;

      int crossings = 0;
      if (firstZeroClick <= value) {
        crossings = 1 + (value - firstZeroClick) / SIZE;
      }

      int finalPosition = Math.floorMod(currentPosition + step * value, SIZE);

      return Map.entry(finalPosition, crossings);

    }

    static int countOccurrenceOfZeroLocationAfterRotations(List<String> rotations) {
      int pos = START_POINT;
      int count = 0;
      for (String rotation : rotations) {
        pos = rotate(pos, rotation);
        if (pos == 0) count++;
      }
      return count;
    }

    public static int countZeroLocationInRotations(List<String> rotations) {
      int pos = START_POINT;
      int count = 0;
      for (String rotation : rotations) {
        var pair = completeDailCycles(pos, rotation);
        pos = pair.getKey();
        count += pair.getValue();
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
    var instructions = InputReader.readLines("DayOneInput.txt");
    return CircularDail.countOccurrenceOfZeroLocationAfterRotations(instructions);
  }

  @Override
  public Integer partTwo() {
    var instructions = InputReader.readLines("DayOneInput.txt");
    return CircularDail.countZeroLocationInRotations(instructions);
  }
}