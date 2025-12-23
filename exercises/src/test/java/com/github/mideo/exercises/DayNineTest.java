package com.github.mideo.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DayNineTest {
    final String testData = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3""";

    final String testData2 = """
            1,0
            3,0
            3,6
            16,6
            16,0
            18,0
            18,9
            13,9
            13,7
            6,7
            6,9
            1,9""";

    final String testData3 = """
            1,1
            8,1
            8,3
            3,3
            3,4
            8,4
            8,9
            18,9
            18,11
            5,11
            5,9
            4,9
            4,11
            1,11
            1,7
            6,7
            6,6
            1,6""";

    final String testData4 = """
            1,5
            3,5
            3,8
            7,8
            7,5
            9,5
            9,10
            11,10
            11,3
            6,3
            6,7
            4,7
            4,1
            13,1
            13,12
            1,12""";

    @Test
    void name() {
        assertEquals("Day 9: Movie Theater", new DayNine().name());
    }

    @ParameterizedTest
    @CsvSource({
            "2,5,9,7,24",
            "7,1,11,7,35",
            "7,3,2,3,6",
            "2,5,11,1,50"
    })
    void surfaceArea(int x1, int y1, int x2, int y2, long expected) {
        assertEquals(expected, DayNine.surfaceArea(new DayNine.TilePosition(x1, y1), new DayNine.TilePosition(x2, y2)));
    }

    @Test
    void maxSurfaceArea() {
        List<DayNine.TilePosition> positions = getTilePositions(testData);
        assertEquals(50L, DayNine.maxSurfaceArea(positions).area());
    }

    @Test
    void maxSurfaceAreaWithBoundary() {
        assertEquals(24L, DayNine.maxSurfaceAreaWithBoundary(getTilePositions(testData), buildBoundary(testData)).area());
        assertEquals(30L, DayNine.maxSurfaceAreaWithBoundary(getTilePositions(testData2), buildBoundary(testData2)).area());
        assertEquals(88L, DayNine.maxSurfaceAreaWithBoundary(getTilePositions(testData3), buildBoundary(testData3)).area());
    }

    @Test
    void partOne() {
        assertEquals(4786902990L, new DayNine().partOne());
    }

    @Test
    void partTwo() {
        assertEquals(1571016172L, new DayNine().partTwo());
    }

    private Set<DayNine.TilePosition> buildBoundary(String input) {
        return DayNine.buildBoundary(getTilePositions(input));
    }

    private static List<DayNine.TilePosition> getTilePositions(String input) {
        return input.lines()
                .map(line -> line.split(","))
                .map(arr -> new DayNine.TilePosition(Integer.parseInt(arr[0]), Integer.parseInt(arr[1])))
                .toList();
    }

}