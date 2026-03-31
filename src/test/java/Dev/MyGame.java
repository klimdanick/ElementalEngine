package Dev;

import nl.klimdanick.E2.Core.E2;
import nl.klimdanick.E2.Core.Game;
import nl.klimdanick.E2.CoreRendering.SpriteBatch;
import nl.klimdanick.E2.CoreRendering.Texture;

public class MyGame extends Game {

	private SpriteBatch batch;
	private Texture texture;

    @Override
    public void init() {
        batch = new SpriteBatch();
        texture = new Texture("E2logo.png");
    }

    @Override
    public void update(float dt) {
        // nothing yet
    }

    @Override
    public void render() {
    	batch.begin();

        batch.draw(texture, -0.5f, -0.5f, 0.5f, 0.5f);
        batch.draw(texture, 0.2f, 0.2f, 0.3f, 0.3f);

        batch.end();
    }
    
    public static void main(String[] args) {
    	new E2(new MyGame()).run();
    }
}