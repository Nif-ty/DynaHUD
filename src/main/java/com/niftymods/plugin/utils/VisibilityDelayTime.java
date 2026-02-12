package com.niftymods.plugin.utils;

public class VisibilityDelayTime {

    private float showDelayTime;
    private float hideDelayTime;

    public void incrementShowTime(float deltaTime) {
        this.showDelayTime += deltaTime;
    }

    public void incrementHideTime(float deltaTime) {
        this.hideDelayTime += deltaTime;
    }

    public void resetShowTime() {
        showDelayTime = 0.0f;
    }

    public void resetHideTime() {
        hideDelayTime = 0.0f;
    }

    public float getShowTime() {
        return showDelayTime;
    }

    public float getHideTime() {
        return hideDelayTime;
    }

}
