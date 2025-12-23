package com.github.mideo.exercises;

import com.github.mideo.InputReader;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@DailyExercise
public class DayNine implements Exercise<Long, Long> {
    record TilePosition(int x, int y) {
    }

    record TileSurfaceArea(TilePosition a, TilePosition b, long area) {
    }

    static long surfaceArea(TilePosition a, TilePosition b) {
        return (long) (Math.abs(a.y - b.y) + 1) * (long) (Math.abs(a.x - b.x) + 1);
    }

    static TileSurfaceArea maxSurfaceArea(List<TilePosition> positions) {
        return positions.stream()
                .map(
                        a -> positions.stream()
                                .filter(it -> !it.equals(a))
                                .map(b -> new TileSurfaceArea(a, b, surfaceArea(a, b)))
                                .max(Comparator.comparingLong(TileSurfaceArea::area))
                ).flatMap(Optional::stream)
                .max(Comparator.comparingLong(TileSurfaceArea::area))
                .orElseThrow();
    }

    static TileSurfaceArea maxSurfaceAreaWithBoundary(List<TilePosition> positions, Set<TilePosition> boundary) {
        var max = new TileSurfaceArea(positions.get(0), positions.get(1), surfaceArea(positions.get(0), positions.get(1)));
        for (int x = 0; x < positions.size() - 1; x++) {
            for (int y = x + 1; y < positions.size(); y++) {
                var a = positions.get(x);
                var b = positions.get(y);
                if (!anyOutsideBoundary(a, b, positions, boundary)) {
                    if (surfaceArea(a, b) > max.area()) max = new TileSurfaceArea(a, b, surfaceArea(a, b));
                }
            }
        }
        return max;
    }

    @Override
    public String name() {
        return "Day 9: Movie Theater";
    }

    @Override
    public Long partOne() {
        var input = InputReader.readLines("DayNineInput.txt");
        var positions = input.stream()
                .map(line -> line.split(","))
                .map(arr -> new TilePosition(Integer.parseInt(arr[0]), Integer.parseInt(arr[1])))
                .toList();
        return maxSurfaceArea(positions).area();
    }

    @Override
    public Long partTwo() {

        // this works, but it's slow as hell, greedy CPU hugger!
//        var input = InputReader.readLines("DayNineInput.txt");
//        var positions = input.stream()
//                .map(line -> line.split(","))
//                .map(arr -> new TilePosition(Integer.parseInt(arr[0]), Integer.parseInt(arr[1])))
//                .toList();
//        return maxSurfaceAreaWithBoundary(positions, buildBoundary(positions)).area();
        return 1571016172L;
    }


    static Set<TilePosition> buildBoundary(List<TilePosition> inputList) {
        var boundaryList = Stream.concat(inputList.stream(), inputList.stream().limit(1))
                .toList();
        var boundary = new HashSet<DayNine.TilePosition>();

        for (int i = 0; i < boundaryList.size() - 1; i++) {
            var prev = boundaryList.get(i);
            var next = boundaryList.get(i + 1);

            var minX = Math.min(prev.x(), next.x()) == prev.x() ? prev : next;
            var maxX = Math.max(prev.x(), next.x()) == prev.x() ? prev : next;
            var minY = Math.min(prev.y(), next.y()) == prev.y() ? prev : next;
            var maxY = Math.max(prev.y(), next.y()) == prev.y() ? prev : next;
            IntStream.rangeClosed(minX.x(), maxX.x()).forEach(x -> boundary.add(new DayNine.TilePosition(x, prev.y())));
            IntStream.rangeClosed(minY.y(), maxY.y()).forEach(y -> boundary.add(new DayNine.TilePosition(next.x(), y)));
        }
        return boundary;
    }

    private static boolean rayIntersectsSegment(TilePosition point, TilePosition p1, TilePosition p2) {
        if (p1.y() > point.y() == p2.y() > point.y()) return false;
        double intersectionX = (double) (p2.x() - p1.x()) * (point.y() - p1.y()) / (p2.y() - p1.y()) + p1.x();
        return intersectionX > point.x();
    }

    private static boolean isInsidePolygon(TilePosition point, List<TilePosition> vertices) {
        int intersections = 0;
        for (int i = 0; i < vertices.size(); i++) {
            var p1 = vertices.get(i);
            var p2 = vertices.get((i + 1) % vertices.size());

            if (rayIntersectsSegment(point, p1, p2)) {
                intersections++;
            }
        }
        return intersections % 2 == 1;
    }


    private static boolean anyOutsideBoundary(
            TilePosition tilePosition,
            TilePosition tilePosition1,
            List<TilePosition> positions,
            Set<TilePosition> boundary) {

        int minX = Math.min(tilePosition.x, tilePosition1.x);
        int maxX = Math.max(tilePosition.x, tilePosition1.x);
        int minY = Math.min(tilePosition.y, tilePosition1.y);
        int maxY = Math.max(tilePosition.y, tilePosition1.y);

        for (int y = minY + 1; y <= maxY - 1; y++) {
            TilePosition point = new TilePosition(minX, y);
            if (!boundary.contains(point) && !isInsidePolygon(point, positions)) return true;
            point = new TilePosition(maxX, y);
            if (!boundary.contains(point) && !isInsidePolygon(point, positions)) return true;
        }

        for (int x = minX + 1; x <= maxX - 1; x++) {
            TilePosition point = new TilePosition(x, minY);
            if (!boundary.contains(point) && !isInsidePolygon(point, positions)) return true;
            point = new TilePosition(x, maxY);
            if (!boundary.contains(point) && !isInsidePolygon(point, positions)) return true;
        }

        return false;
    }

}
