package com.github.mideo.exercises;

@DailyExercise
public class DummyTask implements Exercise<Integer, Integer> {

  @Override
  public Integer partOne() {
    return 1;
  }

  @Override
  public Integer partTwo() {
    return 2;
  }
}