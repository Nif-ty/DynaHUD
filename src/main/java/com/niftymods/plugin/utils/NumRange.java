package com.niftymods.plugin.utils;

public class NumRange {

    public static float validate(float value, float min, float max) {
        if (value < min) value = min;
        if (value > max) value = max;
        return value;
    }

}
