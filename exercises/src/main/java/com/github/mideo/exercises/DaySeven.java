package com.github.mideo.exercises;

import com.github.mideo.InputReader;

import java.math.BigInteger;
import java.util.*;

@DailyExercise
public class DaySeven implements Exercise<Integer, Long> {
  static char START_CHAR = 'S';
  static char TACHYON_SPLITTER = '^';

  record PositionInManifold(int y, int x) {
    PositionInManifold next() {
      return new PositionInManifold(y + 1, x);
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
      this(sanitizeInput(input), sanitizeInputWidth(input), (input.length() / sanitizeInputWidth(input)) - 1);
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

    public long numberOfTachyonBeamDimensions(PositionInManifold start) {
      long[][] timelinesValuesAtPosition = new long[height][width];
      long total = 0L;
      for (int y = 0; y < height; y++)
        Arrays.fill(timelinesValuesAtPosition[y], 0L);

      timelinesValuesAtPosition[start.y()][start.x()] = 1L;

      for (int y = start.y(); y < height - 1; y++) {
        for (int x = 0; x < width; x++) {
          var ways = timelinesValuesAtPosition[y][x];
          if (ways > 0L) {
            var next = new PositionInManifold(y, x).next();
            char c = valueAt(next);

            if (c == TACHYON_SPLITTER) {
              timelinesValuesAtPosition[y + 1][x - 1] += ways;
              timelinesValuesAtPosition[y + 1][x + 1] += ways;
            } else {
              timelinesValuesAtPosition[y + 1][x] += ways;
            }
          }

        }
      }


      for (int x = 0; x < width; x++)
        total += timelinesValuesAtPosition[height - 1][x];

      return total;
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
  public Long partTwo() {
    var input = InputReader.readInput("DaySevenInput.txt").orElseThrow();
    var manifold = new Manifold(input);
    var startPosition = new PositionInManifold(
        input.indexOf(DaySeven.START_CHAR) / manifold.width(),
        input.indexOf(DaySeven.START_CHAR) % manifold.width()
    );
    return manifold.numberOfTachyonBeamDimensions(startPosition);
  }
}
