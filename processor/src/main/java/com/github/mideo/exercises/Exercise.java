package com.github.mideo.exercises;

interface Exercise<T, K> {
  String name();
   T partOne();
   K partTwo();
}
