package test;

import java.util.ArrayList;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Graphic;
import com.danick.e2.renderer.Graphic3D.Model3D;
import com.danick.e2.renderer.Renderer3D;

public class test3D extends AbstractGame{
	Renderer3D r3d;
	ArrayList<Model3D> objs = new ArrayList<>();
	
	public void init(GameContainer gc, Graphic r) {
		r3d = new Renderer3D(gc);
		for (int i = 0; i < 5; i++) {
			Model3D m = Model3D.cube(100); 
			objs.add(m);
			m.TranslateVerts(Math.random()*800-400, Math.random()*800-400, Math.random()*400);
		}
	}

	public void update(GameContainer gc, long dt) {
		
	}
	
	public void render(GameContainer gc, Graphic r) {
		r3d.clear();
		for (Model3D m : objs) {
			m.RotateOrigin(0, 0, 0.01);
			r3d.drawModel(m);
		}
	}
	
	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new test3D(), 600, 600, 1, "3D!!!");
		gc.start();
	}

}
