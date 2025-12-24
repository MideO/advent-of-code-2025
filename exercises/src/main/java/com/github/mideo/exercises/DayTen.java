package com.github.mideo.exercises;

import java.util.ArrayList;
import java.util.List;

@DailyExercise
public class DayTen implements Exercise<Integer, Integer> {
    @Override
    public String name() {
        return "Day 10: Factory";
    }

    @Override
    public Integer partOne() {
        return 0;
    }

    @Override
    public Integer partTwo() {
        return 0;
    }


    enum LightState {
        ON, OFF
    }

    record Light(LightState state) {
        Light() {
            this(LightState.OFF);
        }

        Light toggle() {
            return new Light(state == LightState.ON ? LightState.OFF : LightState.ON);
        }
    }


    record IndicatorLights(List<Light> lights) {
        IndicatorLights toggle(int index) {
            var list = new ArrayList<Light>();
            for (int i = 0; i < lights.size(); i++) {
                list.add(i == index ? lights.get(i).toggle() : lights.get(i));
            }

            return new IndicatorLights(list);
        }
    }
}
