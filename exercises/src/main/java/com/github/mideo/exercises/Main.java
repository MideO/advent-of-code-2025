package com.github.mideo.exercises;

public class Main {
  public static void main(String[] args) {
    DailyExerciseRegistry.getExercises().stream().forEach(
        exercise -> {
          System.out.println(exercise.getClass().getSimpleName());
          System.out.println("Part 1: "+ exercise.partOne());
          System.out.println("Part 3: "+ exercise.partTwo());
        }
    );
  }
}
