package com.github.mideo.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;
import java.util.List;

import static com.github.mideo.exercises.DayEight.*;
import static org.junit.jupiter.api.Assertions.*;

class DayEightTest {
    @Test
    void fromString() {
        assertEquals(new Tryptic(1, 2, 3), Tryptic.fromString("1,2,3"));
    }
    String testData ="162,817,812\n" +
            "57,618,57\n" +
            "906,360,560\n" +
            "592,479,940\n" +
            "352,342,300\n" +
            "466,668,158\n" +
            "542,29,236\n" +
            "431,825,988\n" +
            "739,650,466\n" +
            "52,470,668\n" +
            "216,146,977\n" +
            "819,987,18\n" +
            "117,168,530\n" +
            "805,96,715\n" +
            "346,949,466\n" +
            "970,615,88\n" +
            "941,993,340\n" +
            "862,61,35\n" +
            "984,92,344\n" +
            "425,690,689";

    @ParameterizedTest
    @CsvSource({
            "162,817,812,425,690,689,316.90219311326956",
            "425,690,689,162,817,812,316.90219311326956",
            "162,817,812,431,825,988,321.560258738545",
            "431,825,988,162,817,812,321.560258738545",
            "906,360,560,805,96,715,322.36935338211043",
            "162,817,812,805,96,715,970.9268767523124",
            "162,817,812,906,360,560,908.7843528582565",
    })
    void euclideanDistance(int x1, int y1, int z1, int x2, int y2, int z2, Double expected) {
        assertEquals(expected, DayEight.euclideanDistance(new Tryptic(x1, y1, z1), new Tryptic(x2, y2, z2)));
    }

    @Test
    void  buildCircuits() {
        var input = List.of(testData.split("\n"));
        var circuits = new DayEight().buildCircuits(input, 10);
        var total = circuits.stream().map(List::size).distinct().reduce((a,b) -> a *b);
        assertEquals(11, circuits.size());
        assertEquals(40, total.orElseThrow());
    }

    @Test
    void  buildCircuitsWithAll() {
        var input = List.of(testData.split("\n"));
        var last = new DayEight().findFullConnectionPair(input);

        assertEquals(BigInteger.valueOf(25272), BigInteger.valueOf(last.a().x())
                .multiply(BigInteger.valueOf(last.b().x())));

    }


    @Test
    void name() {
        assertEquals("Day 8: Playground", new DayEight().name());
    }

    @Test
    void partOne() {
        assertEquals(117000, new DayEight().partOne());
    }

    @Test
    void partTwo() {
        assertEquals(BigInteger.valueOf(8368033065L), new DayEight().partTwo());
    }
}