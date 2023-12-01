package test;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.math.OrthographicProjection;
import com.danick.e2.renderer.Graphic;
import com.danick.e2.renderer.Graphic3D.Model3D;
import com.danick.e2.renderer.Renderer3D;

public class test extends AbstractGame{

	Renderer3D r3d;
	double x = 0;
	ArrayList<Model3D> cubes = new ArrayList<>();
	
	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new test(), 150, 100, 1, "test");
		gc.start();
	}

	public void init(GameContainer gc, Graphic r) {
		r3d=new Renderer3D(gc);
		gc.changeRenderer(r3d);
		int size = 10;
		for (int x = -10; x < 11; x++) for (int y = -10; y < 11; y++) {
			Model3D c = Model3D.plane(size);
			c.TranslateVerts(x*size, y*size, 0);
			c.RotateOrigin(0.95, 0, 0);
			cubes.add(c);
		}
	}
	
	public void update(GameContainer gc, long dt) {
		
	}
	
	public void render(GameContainer gc, Graphic r) {
		r3d.projection = new OrthographicProjection();
		r3d.clear();
		
		for (Model3D cube : cubes) r3d.drawModel(cube);
		
		if (gc.input.isKey(KeyEvent.VK_E)) for (Model3D cube : cubes) cube.RotateOrigin(0, 0, Math.PI/80);
		if (gc.input.isKey(KeyEvent.VK_Q)) for (Model3D cube : cubes) cube.RotateOrigin(0, 0, -Math.PI/80);
	}

}
