package com.niftymods.plugin.utils;

public class TickTimer {

    private enum TimerState {
        IDLE,
        RUNNING,
        STOPPED,
    }
    private TimerState timerState = TimerState.IDLE;
    private float defaultTime;
    private float time;

    public TickTimer(float defaultTime) {
        this.defaultTime = defaultTime;
        this.time = defaultTime;
    }

    public void start() {
        time = defaultTime;
        timerState = TimerState.RUNNING;
    }

    public void stop() {
        timerState = TimerState.STOPPED;
    }

    public void resume() {
        if (timerState == TimerState.STOPPED)
            timerState = TimerState.RUNNING;
    }

    public void process(float deltaTime) {
        if (timerState == TimerState.RUNNING) {
            time -= deltaTime;
            if (time <= 0) {
                timerState = TimerState.IDLE;
                time = defaultTime;
            }
        }
    }

    public boolean isIdle() {
        return timerState == TimerState.IDLE;
    }

    public boolean isRunning() {
        return timerState == TimerState.RUNNING;
    }

    public boolean isStopped() {
        return timerState == TimerState.STOPPED;
    }

    public void setDefaultTime(float defaultTime) {
        this.defaultTime = defaultTime;
        this.time = defaultTime;
    }

    public float getTime() {
        return time;
    }

}
