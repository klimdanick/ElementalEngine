package nl.klimdanick.E2.Core;

public abstract class Game {

    public abstract void init();

    public abstract void update(float dt);

    public abstract void render();

    public void cleanup() {}
}
