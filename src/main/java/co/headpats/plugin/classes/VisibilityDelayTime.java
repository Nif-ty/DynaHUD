package co.headpats.plugin.classes;

public class VisibilityDelayTime {

    private float showDelayTime;
    private float hideDelayTime;

    public void incrementShowDelayTime(float deltaTime) {
        this.showDelayTime += deltaTime;
    }

    public void incrementHideDelayTime(float deltaTime) {
        this.hideDelayTime += deltaTime;
    }

    public void resetShowDelayTime() {
        showDelayTime = 0.0f;
    }

    public void resetHideDelayTime() {
        hideDelayTime = 0.0f;
    }

    public float getShowDelayTime() {
        return showDelayTime;
    }

    public float getHideDelayTime() {
        return hideDelayTime;
    }

}
