package Examples.Mario;

import static org.lwjgl.glfw.GLFW.*;

import com.codedisaster.steamworks.SteamAPI;
import com.codedisaster.steamworks.SteamException;

import nl.klimdanick.E2.Core.GameLoop;
import nl.klimdanick.E2.Core.SteamInterface;
import nl.klimdanick.E2.Core.Rendering.Texture;

public class Main extends GameLoop {
	
	Texture tex, tex2;
	int x = 40, y = 40;
	
    @Override
    protected void init() {
    	tex = new Texture("/E2logo.png");
    	tex2 = new Texture(40, 40);
    	
    	tex2.begin();
        renderer.clear(1f, 0f, 0f);
        renderer.drawTexture(tex, 20, 20, 40, 40);
        tex2.end();
        
    	System.out.println("Game initialized");
    }

    @Override
    protected void update() {
        if (input.isKeyDown(GLFW_KEY_ESCAPE)) window.close();
        if (input.isKeyDown(GLFW_KEY_W)) y--;
        if (input.isKeyDown(GLFW_KEY_S)) y++;
        if (input.isKeyDown(GLFW_KEY_A)) x--;
        if (input.isKeyDown(GLFW_KEY_D)) x++;
    }

    @Override
    protected void render() {
        renderer.clear(0.2f, 0.3f, 0.4f);
        renderer.drawTexture(tex2, x, y, 40, 40);
        renderer.drawTexture(tex, 200, 150, 40, 40);
    }

    @Override
    protected void cleanup() {
        System.out.println("Game cleaned up");
    }

    public static void main(String[] args) throws SteamException {         
        new Main().start("Elemental Engine Test", 640, 360, 1);
    }
}
