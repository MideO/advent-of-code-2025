package com.github.mideo.exercises;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DayFiveTest {

    @Test
    void name() {
        assertEquals("Day 5: Cafeteria", new DayFive().name());
    }

    @Test
    void inRange() {
        var range = new DayFive.Range(3L, 5L);
        assertEquals(true, range.inRange(3L));
        assertEquals(true, range.inRange(4L));
        assertEquals(true, range.inRange(5L));
        assertEquals(false, range.inRange(2L));
        assertEquals(false, range.inRange(6L));
    }



    @Test
    void expandRange(){
        var ranges = List.of(
                new DayFive.Range(3L, 5L),
                new DayFive.Range(10L, 14L),
                new DayFive.Range(16L, 20L),
                new DayFive.Range(12L, 18L)
        );
        assertEquals(14L, new DayFive.InventorySystem(ranges, List.of()).countUniqueEntriesInFreshRange());
    }
    @Test
    void getFreshRange() {
        var data = Arrays.asList("""
                3-5
                10-14
                16-20
                12-18
                
                1
                5
                8
                11
                17
                32
                """.split("\\R"));
        var input = data.subList(0, data.indexOf(""));
        assertIterableEquals(
                List.of(
                        new DayFive.Range(3L, 5L),
                        new DayFive.Range(10L, 14L),
                        new DayFive.Range(16L, 20L),
                        new DayFive.Range(12L, 18L)
                ), DayFive.getFreshRange(input)
        );


    }

    @Test
    void getFreshItems() {
        var data = Arrays.asList("""
                3-5
                10-14
                16-20
                12-18
                
                1
                5
                8
                11
                17
                32
                """.split("\\R"));
        int spaceDelimiterIndex = data.indexOf("");
        var ranges = data.subList(0, spaceDelimiterIndex);
        var items = data.subList(spaceDelimiterIndex + 1, data.size());
        assertEquals(3, new DayFive.InventorySystem(DayFive.getFreshRange(ranges), items).freshItems());
    }


    @Test
    void partOne() {
        assertEquals(661, new DayFive().partOne());

    }

    @Test
    void partTwo() {
        assertEquals(359526404143208L, new DayFive().partTwo());

    }
}