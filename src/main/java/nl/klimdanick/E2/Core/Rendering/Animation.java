package nl.klimdanick.E2.Core.Rendering;

public class Animation {

    private TextureRegion[] frames;
    private float frameTime;

    private int currentFrame = 0;
    private float timer = 0;

    public Animation(float frameTime, TextureRegion... frames) {
        this.frameTime = frameTime;
        this.frames = frames;
    }

    public void update(float dt) {
        timer += dt;

        if (timer >= frameTime) {
            timer = 0;
            currentFrame = (currentFrame + 1) % frames.length;
        }
    }

    public TextureRegion getFrame() {
        return frames[currentFrame];
    }
}