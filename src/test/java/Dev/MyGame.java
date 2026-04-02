package Dev;

import org.lwjgl.glfw.GLFW;

import nl.klimdanick.E2.Core.E2;
import nl.klimdanick.E2.Core.Game;
import nl.klimdanick.E2.Core.Input.Input;

public class MyGame extends Game {
	public static E2 engine;
	private World world;

    @Override
    public void init() {
        world = new World(11, 11);
        renderer.camera.position.x = -240;
        
        Input.bind("up", GLFW.GLFW_KEY_W);
        Input.bind("left", GLFW.GLFW_KEY_A);
        Input.bind("down", GLFW.GLFW_KEY_S);
        Input.bind("right", GLFW.GLFW_KEY_D);
        
        Input.bind("next", GLFW.GLFW_KEY_MINUS);
        Input.bind("prev", GLFW.GLFW_KEY_EQUAL);
        
        Input.bind("copy", GLFW.GLFW_KEY_C);
        Input.bind("paste", GLFW.GLFW_KEY_V);
    }

    @Override
    public void update(float dt) {
    	if (dt < 0.02)
    		System.out.println((1/dt));
//    	else
//    		System.err.append((1/dt)+"\n");
    	
    	
    	if (Input.pressed("left")) world.selected[0]--;
    	if (Input.pressed("right")) world.selected[0]++;
    	if (Input.pressed("up")) world.selected[1]--;
    	if (Input.pressed("down")) world.selected[1]++;
    }

    @Override
    public void render() {
    	world.render(renderer);
    }
    
    public static void main(String[] args) {
    	engine = new E2(new MyGame(), 480, 270);
    	engine.run();
    }
}