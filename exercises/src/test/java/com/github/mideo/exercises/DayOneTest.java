package com.github.mideo.exercises;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.github.mideo.exercises.DayOne.CircularDail;

class DayOneTest {

  @Test
  void leftOperator() {
    assertEquals(0, CircularDail.rotate(19, "L19"));
  }

  @Test
  void leftOperatorBeyondZero() {
    assertEquals(95, CircularDail.rotate(5, "L10"));
  }

  @Test
  void rightOperator() {
    assertEquals(19, CircularDail.rotate(11, "R8"));
  }


  @Test
  void rightOperatorBeyondZero() {
    assertEquals(5, CircularDail.rotate(99, "R6"));
  }

  @Test
  void rotateInstructions() {
    var instructions = List.of(
        "L68",
        "L30",
        "R48",
        "L5",
        "R60",
        "L55",
        "L1",
        "L99",
        "R14",
        "L82"
    );
    assertEquals(3, CircularDail.countOccurrenceOfZeroLocationAfterRotation(50, instructions));
  }

  @Test
  void name() {
    assertEquals("Day 1: Secret Entrance ", new DayOne().name());
  }

  @Test
  void partOne() {
      assertEquals(1150,new DayOne().partOne());
  }

  @Test
  void partTwo() {
  }
}