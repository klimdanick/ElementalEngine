package Examples.Mario;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_V;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.awt.Font;

import com.codedisaster.steamworks.SteamException;

import nl.klimdanick.E2.Core.GameLoop;
import nl.klimdanick.E2.Core.Rendering.Animation;
import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Rendering.Texture;
import nl.klimdanick.E2.Core.Rendering.TextureSubRegion;

public class Main extends GameLoop {
	
	Texture tex, tex2;
	Animation tex3;
	int x = 40, y = 40;
	
	float[][] shape = new float[][] {{0, -1}, {-0.5f, 0.2f}, {-0.5f, 1}, {0, 0.4f}, {0.5f, 1}, {0.5f, 0.2f}}; 
	
    @Override
    protected void init() {
    	tex = new Texture("/E2logo.png");
    	tex2 = new Texture(40, 40);
    	tex3 = new Animation("/sprite_sheet.png", 21, 23, 3, 1, 10);
    	
    	
    	System.out.println("Game initialized");
    }

    @Override
    protected void update(double dt) {
        if (input.isKeyDown(GLFW_KEY_ESCAPE)) window.close();
        if (input.isKeyPressed(GLFW_KEY_V)) window.toggleVSync();
        if (input.isKeyDown(GLFW_KEY_W)) y--;
        if (input.isKeyDown(GLFW_KEY_S)) y++;
        if (input.isKeyDown(GLFW_KEY_A)) x--;
        if (input.isKeyDown(GLFW_KEY_D)) x++;
    }

    @Override
    protected void render() {
    	tex3.play();
    	
        renderer.clear(E2Color.CINDER_BLACK);
        renderer.drawTexture(tex2, x, y, 40, 40);
        renderer.drawTexture(tex3, 100, 150, 21, 23);
        
        renderer.drawMode = DrawingMode.OUTLINE;
        
        tex2.begin();
        renderer.clear(new E2Color(0f, 0f, 0f, 0f));
    	renderer.drawCircle(20, 20, 20, 50, E2Color.AQUA_GREEN);
//        renderer.drawTexture(tex, 20, 20, 40, 40);
        tex2.end();
        
        
        renderer.drawShape(100, 100, shape, E2Color.DEBIAN_RED, 20, (float)x/100.0f);
//        renderer.drawShape(100, 100, shape, E2Color.CINDER_BLACK, 17, (float)x/100.0f);
        
        renderer.drawLine(100, 100, 200, 150, E2Color.CURIOS_BLUE);
    }

    @Override
    protected void cleanup() {
        System.out.println("Game cleaned up");
    }

    public static void main(String[] args) throws SteamException {         
        new Main().start("Elemental Engine Test", 640, 360, 1);
    }
}
