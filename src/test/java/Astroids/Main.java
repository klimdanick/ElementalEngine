package Astroids;

import java.awt.Color;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Graphic;

public class Main extends AbstractGame {
	
	public Player p;

	@Override
	public void init(Graphic r) {
		for (int i = 0; i < 10; i++) {	
			gameContainer.addObject(new Astroid((int) (Math.random()*20+5)));
		}
		p = new Player();
		gameContainer.addObject(p);
	}

	int timer0 = 0;
	@Override
	public void update(long dt) {
		if (timer0++ > 100) {
			gameContainer.addObject(new Astroid((int) (Math.random()*20+5)));
			timer0 = 0;
		}
	}

	@Override
	public void render(Graphic r) {
		r.clear();
	}

	public static void main(String[] args) {
		float f = 3;
		gameContainer = new GameContainer(new Main(), (int)(1920/f), (int)(1040/f), f, "Astroid");
		gameContainer.start();
	}

}
