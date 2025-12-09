package com.github.mideo.exercises;

import com.github.mideo.InputReader;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@DailyExercise
public class DayFive implements Exercise<Integer, Long> {

    record InventorySystem(List<Range> freshRanges, List<String> items) {
        int freshItems() {
            return (int) items.stream()
                    .map(Long::parseLong)
                    .filter(item -> freshRanges.stream().anyMatch(range -> range.inRange(item)))
                    .count();
        }

        Long countUniqueEntriesInFreshRange() {
            List<Range> sorted = freshRanges.stream()
                    .sorted(Comparator.comparingLong(a -> a.start))
                    .toList();

            return mergeOverlappingRanges(sorted).stream()
                    .map(range -> range.end - range.start + 1)
                    .reduce(0L, Long::sum);
        }

        private List<Range> mergeOverlappingRanges(List<Range> sorted) {
            List<Range> merged = new ArrayList<>();
            for (int i = 1; i < sorted.size(); i++) {
                if (merged.isEmpty()) {
                    merged.add(sorted.getFirst());
                }
                var current = merged.getLast();
                var next = sorted.get(i);
                if (current.end >= next.start) {
                    merged.set(merged.size() - 1, new Range(current.start, Math.max(current.end, next.end)));
                } else {
                    merged.add(next);
                }
            }
            return merged;
        }

    }

    record Range(long start, long end) {
        boolean inRange(long value) {
            return value >= start && value <= end;
        }
    }


    static List<Range> getFreshRange(List<String> input) {
        return input.stream()
                .map(s -> s.split("-"))
                .map(arr -> new Range(Long.parseLong(arr[0]), Long.parseLong(arr[1])))
                .toList();

    }

    @Override
    public String name() {
        return "Day 5: Cafeteria";
    }

    @Override
    public Integer partOne() {
        var input = InputReader.readLines("DayFiveInput.txt");
        int spaceDelimiterIndex = input.indexOf("");
        var ranges = input.subList(0, spaceDelimiterIndex);
        var items = input.subList(spaceDelimiterIndex + 1, input.size());
        var inventorySystem = new InventorySystem(getFreshRange(ranges), items);
        return inventorySystem.freshItems();

    }

    @Override
    public Long partTwo() {
        var input = InputReader.readLines("DayFiveInput.txt");
        int spaceDelimiterIndex = input.indexOf("");
        var ranges = input.subList(0, spaceDelimiterIndex);
        var items = input.subList(spaceDelimiterIndex + 1, input.size());
        var inventorySystem = new InventorySystem(getFreshRange(ranges), items);
        return inventorySystem.countUniqueEntriesInFreshRange();
    }
}
