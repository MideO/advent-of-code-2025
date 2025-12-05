package com.github.mideo.exercises;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.mideo.exercises.DayThree.*;
import static org.junit.jupiter.api.Assertions.*;

class DayThreeTest {

  @Test
  void maxJoltageCalculated() {
    assertEquals(98, new BatteryBank("987654321111111").maxJoltage());
    assertEquals(89, new BatteryBank("811111111111119").maxJoltage());
    assertEquals(78, new BatteryBank("234234234234278").maxJoltage());
    assertEquals(92, new BatteryBank("818181911112111").maxJoltage());
  }

//    @Test
//    void maxJoltageCalculatedTweldDigits() {
//        assertEquals(987654321111L, new BatteryBank("987654321111111").maxJoltage(12));
//        assertEquals(811111111119L, new BatteryBank("811111111111119").maxJoltage(12));
//        assertEquals(434234234278L, new BatteryBank("234234234234278").maxJoltage(12));
//        assertEquals(888911112111L, new BatteryBank("818181911112111").maxJoltage(12));
//    }


    @Test
  void maxSumOfBatteryBank() {
    var banks = List.of(
        new BatteryBank("987654321111111"),
        new BatteryBank("811111111111119"),
        new BatteryBank("234234234234278"),
        new BatteryBank("818181911112111")
    );
    assertEquals(357, maxSum(banks));
  }

  @Test
  void name() {
    assertEquals("Day 3: Lobby", new DayThree().name());
  }

  @Test
  void partOne() {
    assertEquals(17155, new DayThree().partOne());
  }

  @Test
  void partTwo() {
  }
}