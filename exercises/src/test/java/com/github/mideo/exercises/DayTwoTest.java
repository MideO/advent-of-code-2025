package com.github.mideo.exercises;


import com.github.mideo.InputReader;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static com.github.mideo.exercises.DayTwo.*;
import static org.junit.jupiter.api.Assertions.*;

class DayTwoTest {

    @Test
    void getRangeFromNumbersRange() {
        var expected = LongStream.rangeClosed(11L, 22L);
        var actual = stream("11-22");
        assertIterableEquals(expected.boxed().toList(), actual.boxed().toList());
    }

    @Test
    void idIsNotSequenceRepeated() {
        assertFalse(new Id("1").isSequenceRepeated(2));
        assertFalse(new Id("19").isSequenceRepeated(2));
        assertFalse(new Id("9191896883").isSequenceRepeated(2));
        assertFalse(new Id("9199").isSequenceRepeated(2));
    }

    @Test
    void idIsSequenceRepeated() {
        assertTrue(new Id("11").isSequenceRepeated(2));
        assertTrue(new Id("1010").isSequenceRepeated(2));
        assertTrue(new Id("1188511885").isSequenceRepeated(2));
        assertTrue(new Id("222222").isSequenceRepeated(2));
        assertTrue(new Id("38593859").isSequenceRepeated(2));

    }

    @Test
    void idIsNotIsSequenceRepeatedAtLeast() {
        assertFalse(new Id("1").isSequenceRepeatedAtLeast(2));
        assertFalse(new Id("19").isSequenceRepeatedAtLeast(2));
        assertFalse(new Id("9191896883").isSequenceRepeatedAtLeast(2));
        assertFalse(new Id("9199").isSequenceRepeatedAtLeast(2));
    }

    @Test
    void idIsSequenceRepeatedAtLeastByGivenNumber() {
        assertTrue(new Id("11").isSequenceRepeatedAtLeast(2));
        assertTrue(new Id("999").isSequenceRepeatedAtLeast(2));
        assertTrue(new Id("1188511885").isSequenceRepeatedAtLeast(2));
        assertTrue(new Id("222222").isSequenceRepeatedAtLeast(2));
        assertTrue(new Id("38593859").isSequenceRepeatedAtLeast(2));
        assertTrue(new Id("824824824").isSequenceRepeatedAtLeast(2));
        assertTrue(new Id("2121212121").isSequenceRepeatedAtLeast(2));
    }

    @Test
    void returnsInvalidIdsWithRepeatedSequenceOfTwo(){
        var input = """
                11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
                1698522-1698528,446443-446449,38593856-38593862,565653-565659,
                824824821-824824827,2121212118-2121212124
                """.replaceAll("\\s", "");

        assertIterableEquals(
                List.of(
                        new Id("11"),
                        new Id("22"),
                        new Id("99"),
                        new Id("1010"),
                        new Id("1188511885"),
                        new Id("222222"),
                        new Id("446446"),
                        new Id("38593859")
                        ),
                new Ids(input.split(",")).getIdsWithRepeatedSequence(2).toList()
        );
    }

    @Test
    void returnsInvalidIdsWithRepeatedSequenceOfAtleastTwo(){
        var input = """
                11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
                1698522-1698528,446443-446449,38593856-38593862,565653-565659,
                824824821-824824827,2121212118-2121212124
                """.replaceAll("\\s", "");

        assertIterableEquals(
                List.of(
                        new Id("11"),
                        new Id("22"),
                        new Id("99"),
                        new Id("111"),
                        new Id("999"),
                        new Id("1010"),
                        new Id("1188511885"),
                        new Id("222222"),
                        new Id("446446"),
                        new Id("38593859"),
                        new Id("565656"),
                        new Id("824824824"),
                        new Id("2121212121")
                ),
                new Ids(input.split(",")).getIdsWithRepeatedSequenceOfAtLeast(2).toList()
        );
    }


    @Test
    void name() {
        assertEquals("Day 2: Gift Shop", new DayTwo().name());
    }

    @Test
    void partOne() {
        assertEquals(5398419778L, new DayTwo().partOne());
    }

    @Test
    void partTwo() {
        assertEquals(15704845910L, new DayTwo().partTwo());
    }
}