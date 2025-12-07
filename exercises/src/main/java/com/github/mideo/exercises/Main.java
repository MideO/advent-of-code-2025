package com.github.mideo.exercises;

import java.util.logging.Logger;

public class Main {
  static Logger logger = Logger.getLogger(Main.class.getName());
  public static void main(String[] args) {
    DailyExerciseRegistry.getExercises().forEach(
        exercise -> {
            logger.info(
                    String.format(
                            "%s\n %s\n %s\n",
                    exercise.getClass().getSimpleName(),
                    "Part 1: "+ exercise.partOne(),
                    "Part 2: "+ exercise.partTwo()
                    )
            );

        }
    );
  }
}
