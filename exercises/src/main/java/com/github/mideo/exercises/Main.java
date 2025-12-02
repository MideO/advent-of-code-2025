package com.github.mideo.exercises;

import java.util.logging.Logger;

public class Main {
  static Logger logger = Logger.getLogger(Main.class.getName());
  public static void main(String[] args) {
    DailyExerciseRegistry.getExercises().forEach(
        exercise -> {
         logger.info(exercise.getClass().getSimpleName());
         logger.info("Part 1: "+ exercise.partOne());
         logger.info("Part 2: "+ exercise.partTwo());
        }
    );
  }
}
