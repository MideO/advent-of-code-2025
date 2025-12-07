package com.github.mideo.exercises;

import com.github.mideo.exercises.DayFour.Grid;
import com.github.mideo.exercises.DayFour.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

import static com.github.mideo.exercises.DayFour.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class DayFourTest {
    private List<Position> parsePositions(String positionsStr) {
        return Arrays.stream(positionsStr.split(","))
                .map(s -> s.split(":"))
                .map(coords -> new Position(Integer.parseInt(coords[0]), Integer.parseInt(coords[1])))
                .toList();
    }

    String input = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
            """.replaceAll("\\s", "");

    @ParameterizedTest
    @CsvSource({
            "0,0,'0:1,1:0,1:1'",
            "1,1,'0:0,0:1,0:2,1:0,1:2,2:0,2:1,2:2'",
            "9,9,'8:8,8:9,9:8'",
            "0,9,'0:8,1:8,1:9'",
            "9,0,'8:0,8:1,9:1'"
    })
    void positionAdjacentPositions(int y, int x, String expectedStr) {
        var grid = new Grid(input, 10, 10);
        var position = new Position(y, x);
        var adjacentPositions = position.getAdjacentPositions(grid);
        List<Position> expected = parsePositions(expectedStr);

        assertIterableEquals(expected, adjacentPositions.toList());
    }

    @Test
    void charAt() {
        var grid = new Grid(input, 10, 10);
        assertEquals(paperRollChar, grid.charAt(new Position(0, 2)));
        assertEquals(dotChar, grid.charAt(new Position(0, 1)));
    }

    @Test
    void positionsWithChar() {
        var grid = new Grid(input, 10, 10);
        List<Position> paperRollChars = grid.positionsWithChar(paperRollChar).toList();
        List<Position> dotChars = grid.positionsWithChar(dotChar).toList();

        assertEquals(100, paperRollChars.size() + dotChars.size());
        assertEquals(71, paperRollChars.size());
        assertEquals(29, dotChars.size());

        paperRollChars.forEach(position -> assertEquals(paperRollChar, grid.charAt(position)));
        dotChars.forEach(position -> assertEquals(dotChar, grid.charAt(position)));
    }

    @Test
    void positionsWithCharAndPredicate() {
        var grid = new Grid(input, 10, 10);
        BiPredicate<Grid, Position> predicate = (g, pos) -> pos.getAdjacentPositions(g)
                .filter(adjacent -> g.charAt(adjacent) == paperRollChar)
                .count() < 4;
        assertEquals(13, grid.positionsWithChar(paperRollChar, predicate).count());
    }

    @Test
    void updateGrid() {
        var grid = new Grid(input, 10, 10);
        List<Position> paperRollChars = grid.positionsWithChar(paperRollChar).toList();
        List<Position> dotChars = grid.positionsWithChar(dotChar).toList();

        assertEquals(100, paperRollChars.size() + dotChars.size());
        assertEquals(71, paperRollChars.size());
        assertEquals(29, dotChars.size());

        BiPredicate<Grid, Position> predicate = (g, pos) -> pos.getAdjacentPositions(g)
                .filter(adjacent -> g.charAt(adjacent) == paperRollChar)
                .count() < 4;
        var iterationOne = grid.update(grid.positionsWithChar(paperRollChar, predicate), collectedPaperRollChar);
        assertEquals(71 - 13, iterationOne.positionsWithChar(paperRollChar).count());
        assertEquals(13, iterationOne.positionsWithChar(collectedPaperRollChar).count());
        assertEquals(29, iterationOne.positionsWithChar(dotChar).count());


        var iterationTwo = iterationOne.update(iterationOne.positionsWithChar(paperRollChar, predicate), collectedPaperRollChar);
        assertEquals(25, iterationTwo.positionsWithChar(collectedPaperRollChar).count());
        assertEquals(71 - 25, iterationTwo.positionsWithChar(paperRollChar).count());
        assertEquals(29, iterationTwo.positionsWithChar(dotChar).count());


        var iterationThree = iterationTwo.update(iterationTwo.positionsWithChar(paperRollChar, predicate), collectedPaperRollChar);
        assertEquals(32, iterationThree.positionsWithChar(collectedPaperRollChar).count());
        assertEquals(71 - 32, iterationThree.positionsWithChar(paperRollChar).count());
        assertEquals(29, iterationThree.positionsWithChar(dotChar).count());

    }

    @Test
    void positionsWithCharAndPredicateRepeatedUntilNoChange() {
        var grid = new Grid(input, 10, 10);
        BiPredicate<Grid, Position> predicate = (g, pos) -> pos.getAdjacentPositions(g)
                .filter(adjacent -> g.charAt(adjacent) == paperRollChar)
                .count() < 4;
        assertEquals(43, grid.positionsWithCharAndPredicateRepeatedUntilNoChange(paperRollChar, predicate).count());

    }


    @Test
    void name() {
        assertEquals("Day 4: Printing Department", new DayFour().name());
    }

    @Test
    void partOne() {
        assertEquals(1419L, new DayFour().partOne());
    }

    @Test
    void partTwo() {
        assertEquals(8739L, new DayFour().partTwo());
    }
}