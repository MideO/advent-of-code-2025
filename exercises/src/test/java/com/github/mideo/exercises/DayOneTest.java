package com.github.mideo.exercises;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.mideo.exercises.DayOne.CircularDail;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
  void countOccurrenceOfZeroLocationAfterRotation() {
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
    assertEquals(3, CircularDail.countOccurrenceOfZeroLocationAfterRotations(instructions));
  }

  @Test
  void rightCompleteDailCycles() {
    var result = CircularDail.completeDailCycles(50, "R1000");
    assertEquals(50, result.getKey());
    assertEquals(10, result.getValue());
  }

  @Test
  void rightIncompleteDailCycles() {
    var result = CircularDail.completeDailCycles(50, "R10");
    assertEquals(60, result.getKey());
    assertEquals(0, result.getValue());
  }

  @Test
  void leftIncompleteDailCycles() {
    var result = CircularDail.completeDailCycles(20, "L10");
    assertEquals(10, result.getKey());
    assertEquals(0, result.getValue());
  }

  @Test
  void leftCompleteDailCycles() {
    var result = CircularDail.completeDailCycles(20, "L120");
    assertEquals(0, result.getKey());
    assertEquals(2, result.getValue());
  }
  @Test
  void countOccurrenceOfZeroLocationAfterRotations() {
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
    assertEquals(6, CircularDail.countZeroLocationInRotations(instructions));
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
    assertEquals(6738,new DayOne().partTwo());
  }
}