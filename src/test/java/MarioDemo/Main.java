package MarioDemo;

import java.awt.event.KeyEvent;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.Graphic;

public class Main extends AbstractGame {
	Player p;
	@Override
	public void init(GameContainer gc, Graphic r) {
		for (GameObject o : gc.getObjects()) gc.removeObject(o);
		p = new Player(1, 15);
		gc.addObject(p);
		
		gc.addObject(new Wall(-1, 17, 54, 4));
		gc.addObject(new Wall(55, 17, 15, 4));
		gc.addObject(new Wall(73, 17, 66, 4));
		gc.addObject(new Wall(141, 17, 50, 4));
		gc.addObject(new Pipe(15, 15, 2));
		gc.addObject(new Block(12, 13));
		gc.addObject(new QBlock(11, 13, new Mushroom(11, 12)));
		gc.addObject(new Block(10, 13));
		gc.addObject(new QBlock(9, 13));
		gc.addObject(new Block(8, 13));
		gc.addObject(new QBlock(5, 13));
		gc.addObject(new QBlock(10, 9));
		gc.addObject(new Pipe(24, 14, 3));
		gc.addObject(new Pipe(31, 13, 4));
		gc.addObject(new Pipe(41, 13, 4));
		gc.addObject(new Block(62, 13));
		gc.addObject(new QBlock(63, 13));
		gc.addObject(new Block(64, 13));
		for (int x = 65; x < 73; x++) gc.addObject(new Block(x, 9));
		for (int x = 76; x < 80; x++) gc.addObject(new Block(x, 9));
		gc.addObject(new QBlock(80, 9));
		gc.addObject(new Block(80, 13));
		gc.addObject(new Block(86, 13));
		gc.addObject(new Block(87, 13));
		gc.addObject(new QBlock(92, 13));
		gc.addObject(new QBlock(95, 13));
		gc.addObject(new QBlock(98, 13));
		gc.addObject(new QBlock(95, 9));
		gc.addObject(new Block(104, 13));
		gc.addObject(new Block(107, 9));
		gc.addObject(new Block(108, 9));
		gc.addObject(new Block(109, 9));
		gc.addObject(new Block(114, 9));
		gc.addObject(new QBlock(115, 9));
		gc.addObject(new QBlock(116, 9));
		gc.addObject(new Block(117, 9));
		gc.addObject(new Block(115, 13));
		gc.addObject(new Block(116, 13));
		for (int y = 0; y < 4; y++) for (int x = y; x < 4; x++) gc.addObject(new Block(120+x, 16-y));
		for (int y = 0; y < 4; y++) for (int x = 0; x < 4-y; x++) gc.addObject(new Block(126+x, 16-y));
		for (int y = 0; y < 4; y++) for (int x = y; x < 4; x++) gc.addObject(new Block(134+x, 16-y));
		for (int y = 0; y < 4; y++) gc.addObject(new Block(138, 16-y));
		for (int y = 0; y < 4; y++) for (int x = 0; x < 4-y; x++) gc.addObject(new Block(141+x, 16-y));
		gc.addObject(new Pipe(149, 15, 2));
		gc.addObject(new Block(154, 13));
		gc.addObject(new Block(155, 13));
		gc.addObject(new QBlock(156, 13));
		gc.addObject(new Block(157, 13));
		gc.addObject(new Pipe(165, 15, 2));
		for (int y = 0; y < 8; y++) for (int x = y; x < 8; x++) gc.addObject(new Block(167+x, 16-y));
		for (int y = 0; y < 8; y++) gc.addObject(new Block(175, 16-y));
		gc.addObject(new Block(184, 16));
	}

	@Override
	public void update(GameContainer gc, long dt) {
		if (p.x > gc.width/2 && r.transX + gc.width/2 < p.x-1) {
			r.translate(1, 0);
		} else if (p.x > gc.width/2 && r.transX + gc.width/2 > p.x +1) {
			r.translate(-1, 0);
		} else if (p.x <= gc.width/2 && r.transX > 0) {
			r.translate(-1, 0);
		}
		if (gc.input.isKeyDown(KeyEvent.VK_R)) init(gc, gc.game.r);
		if (p.y > gc.height*2) init(gc, gc.game.r);
	}

	@Override
	public void render(GameContainer gc, Graphic r) {
		r.clear();
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new Main(),400, 210, 3, "SuperMarioBros");
		gc.start();
	}
}
