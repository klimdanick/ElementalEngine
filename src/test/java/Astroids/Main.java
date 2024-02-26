package Astroids;

import java.awt.Color;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Graphic;

public class Main extends AbstractGame {
	
	public static GameContainer gc;
	public Player p;

	@Override
	public void init(GameContainer gamecon, Graphic r) {
		gc = gamecon;
		for (int i = 0; i < 10; i++) {	
			gc.addObject(new Astroid((int) (Math.random()*20+5)));
		}
		p = new Player();
		gc.addObject(p);
	}

	int timer0 = 0;
	@Override
	public void update(GameContainer gc, long dt) {
		if (timer0++ > 100) {
			gc.addObject(new Astroid((int) (Math.random()*20+5)));
			timer0 = 0;
		}
	}

	@Override
	public void render(GameContainer gc, Graphic r) {
		r.clear();
	}

	public static void main(String[] args) {
		float f = 3;
		gc = new GameContainer(new Main(), (int)(1920/f), (int)(1040/f), f, "Astroid");
		gc.start();
	}

}
