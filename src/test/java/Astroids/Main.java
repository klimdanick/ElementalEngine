package Astroids;

import java.awt.Color;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Graphic;

public class Main extends AbstractGame {
	

	@Override
	public void init(GameContainer gc, Graphic r) {
		for (int i = 0; i < 3; i++) {	
			gc.addObject(new Astroid((int) (Math.random()*gc.width), (int) (Math.random()*gc.height), (int) (Math.random()*20+5)));
		}
	}

	@Override
	public void update(GameContainer gc, long dt) {
		
	}

	@Override
	public void render(GameContainer gc, Graphic r) {
		r.clear();
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new Main(), 300, 300, 2f, "Astroid");
		gc.start();
	}

}
