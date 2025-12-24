package com.github.mideo.exercises;

import com.github.mideo.exercises.DayTen.IndicatorLights;
import com.github.mideo.exercises.DayTen.Light;
import com.github.mideo.exercises.DayTen.LightState;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DayTenTest {

    @Test
    void toggleLight() {
        var light = new Light();
        assertEquals(LightState.OFF, light.state());
        var toggled = light.toggle();
        assertEquals(LightState.ON, toggled.state());
        assertEquals(LightState.OFF, toggled.toggle().state());
    }
    @Test
    void toggleLightAtIndex() {
        var lights = new IndicatorLights(List.of(new Light(), new Light(), new Light()));
        assertEquals(LightState.ON, lights.toggle(1).lights().get(1).state());
    }

    @Test
    void name() {
        assertEquals("Day 10: Factory", new DayTen().name());
    }

    @Test
    void partOne() {
    }

    @Test
    void partTwo() {
    }
}