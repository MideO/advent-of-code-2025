package com.github.mideo.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.github.mideo.exercises.DaySeven.Manifold;
import static com.github.mideo.exercises.DaySeven.PositionInManifold;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DaySevenTest {
    String testData = ".......S.......\n" +
            "...............\n" +
            ".......^.......\n" +
            "...............\n" +
            "......^.^......\n" +
            "...............\n" +
            ".....^.^.^.....\n" +
            "...............\n" +
            "....^.^...^....\n" +
            "...............\n" +
            "...^.^...^.^...\n" +
            "...............\n" +
            "..^...^.....^..\n" +
            "...............\n" +
            ".^.^.^.^.^...^.\n" +
            "...............";

    @Test
    void nextPositionInManifold() {
        var position = new PositionInManifold(0, 0);
        assertEquals(new PositionInManifold(1, 0), position.next());
    }

    @ParameterizedTest
    @CsvSource({
            "0,0,'0:-1,0:1'",
    })
    void splitPositionInManifold(int y, int x, String expectedStr) {
        var position = new PositionInManifold(y, x);
        var expected = Arrays.stream(expectedStr.split(","))
                .map(s -> s.split(":"))
                .map(coords -> new PositionInManifold(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])))
                .toList();
        List<PositionInManifold> actual = position.split();
        assertThat(actual).containsExactlyElementsIn(expected);

    }

    @ParameterizedTest
    @CsvSource({
            "0, 7, 'S'",
            "2, 7, '^'",
            "4, 6, '^'",
            "14, 1, '^'",
    })
    void valueAtManifold(int y, int x, char expected) {
        var manifold = new Manifold(testData);
        assertEquals(expected, manifold.valueAt(new PositionInManifold(y, x)));
    }

    @ParameterizedTest
    @CsvSource({
            "2, 1",
            "4, 3",
            "6, 6",
            "8, 9",
            "10,13",
            "12,16",
            "14,21",
            "15,21"
    })
    void numberOfTachyonBeamSplits(int moveCount, int expected) {
        var manifold = new Manifold(testData);
        var startPosition = new PositionInManifold(
                testData.indexOf(DaySeven.START_CHAR) / manifold.width(),
                testData.indexOf(DaySeven.START_CHAR) % manifold.width()
        );

        assertEquals(expected, manifold.numberOfTachyonBeamSplits(startPosition, moveCount, new HashSet<>()));

    }

    @Test
    void numberOfTachyonBeamDimensions() {
        var manifold = new Manifold(testData);
        var startPosition = new PositionInManifold(
                testData.indexOf(DaySeven.START_CHAR) / manifold.width(),
                testData.indexOf(DaySeven.START_CHAR) % manifold.width()
        );

//        assertEquals(40, manifold.numberOfTachyonBeamDimensions(startPosition, manifold.height()));

    }


    @Test
    void name() {
        assertEquals("Day 7: Laboratories", new DaySeven().name());
    }

    @Test
    void partOne() {
        assertEquals(1628,  new DaySeven().partOne());

    }

    @Test
    void partTwo() {
    }
}