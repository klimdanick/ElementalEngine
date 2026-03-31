package nl.klimdanick.E2.Core;

public class E2 {

    private Window window;
    private Game game;

    public E2(Game game) {
        this.game = game;
    }

    public void run() {
        window = new Window(800, 600, "My Engine");
        window.create();

        game.init();

        float lastTime = window.getTime();

        while (!window.shouldClose()) {

            float currentTime = window.getTime();
            float dt = currentTime - lastTime;
            lastTime = currentTime;

            game.update(dt);

            window.clear();
            game.render();

            window.update();
        }

        game.cleanup();
        window.destroy();
    }
}