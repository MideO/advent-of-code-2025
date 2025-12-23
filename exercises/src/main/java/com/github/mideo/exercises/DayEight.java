package com.github.mideo.exercises;

import com.github.mideo.InputReader;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@DailyExercise
public class DayEight implements Exercise<Integer, BigInteger> {
    record Tryptic(int x, int y, int z) {
        static Tryptic fromString(String input) {
            var arr = input.split(",");
            return new Tryptic(
                    Integer.parseInt(arr[0]),
                    Integer.parseInt(arr[1]),
                    Integer.parseInt(arr[2])
            );
        }
    }

    static double euclideanDistance(Tryptic a, Tryptic b) {
        return Math.sqrt(
                Math.pow(a.x - b.x, 2) +
                        Math.pow(a.y - b.y, 2) +
                        Math.pow(a.z - b.z, 2)
        );
    }

    record TrypticDistance(Tryptic a, Tryptic b, double dist) {
    }

    List<List<Tryptic>> buildCircuits(List<String> input, int edgesToConnect) {
        List<Tryptic> points = input.stream()
                .map(Tryptic::fromString)
                .toList();

        List<TrypticDistance> trypticDistances = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                var p = points.get(i);
                var q = points.get(j);
                trypticDistances.add(new TrypticDistance(p, q, euclideanDistance(p, q)));
            }
        }
        trypticDistances.sort(Comparator.comparingDouble(p -> p.dist));
        List<List<Tryptic>> circuits = new ArrayList<>();
        for (var p : points) {
            circuits.add(new ArrayList<>(List.of(p)));
        }
        Function<Tryptic, List<Tryptic>> findCircuit = (t) ->
                circuits.stream().filter(c -> c.contains(t)).findFirst().orElseThrow();
        for (int i = 0; i < edgesToConnect; i++) {
            var pair = trypticDistances.get(i);

            List<Tryptic> c1 = findCircuit.apply(pair.a);
            List<Tryptic> c2 = findCircuit.apply(pair.b);
            if (c1 != c2) {
                c2.addAll(c1);
                circuits.remove(c1);
            }

        }

        return circuits;
    }

    TrypticDistance findFullConnectionPair(List<String> input) {
        List<Tryptic> points = input.stream()
                .map(Tryptic::fromString)
                .toList();
        List<TrypticDistance> trypticDistances = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                var p = points.get(i);
                var q = points.get(j);
                trypticDistances.add(new TrypticDistance(p, q, euclideanDistance(p, q)));
            }
        }

        trypticDistances.sort(Comparator.comparingDouble(p -> p.dist));

        List<List<Tryptic>> circuits = new ArrayList<>();
        for (var p : points) {
            circuits.add(new ArrayList<>(List.of(p)));
        }

        Function<Tryptic, List<Tryptic>> findCircuit = (t) ->
                circuits.stream().filter(c -> c.contains(t)).findFirst().orElseThrow();

        TrypticDistance connection = null;

        for (TrypticDistance trypticDistance : trypticDistances) {
            if (circuits.size() == 1) break;

            var c1 = findCircuit.apply(trypticDistance.a);
            var c2 = findCircuit.apply(trypticDistance.b);

            if (c1 != c2) {
                c2.addAll(c1);
                circuits.remove(c1);
                connection = trypticDistance;
            }
        }

        return connection;
    }


    @Override
    public String name() {
        return "Day 8: Playground";
    }

    @Override
    public Integer partOne() {
        var input = InputReader.readLines("DayEightInput.txt");
        var circuits = new DayEight().buildCircuits(input, 1000);
        return circuits.stream().map(List::size)
                .sorted((a, b) -> b - a)
                .distinct()
                .limit(3)
                .reduce((a, b) -> a * b)
                .orElse(0);
    }

    @Override
    public BigInteger partTwo() {

        var input = InputReader.readLines("DayEightInput.txt");
        TrypticDistance last = findFullConnectionPair(input);
        return BigInteger.valueOf(last.a.x())
                .multiply(BigInteger.valueOf(last.b.x()));

    }
}
