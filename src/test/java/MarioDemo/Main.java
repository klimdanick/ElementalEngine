package MarioDemo;
//
import java.awt.Color;
import java.awt.event.KeyEvent;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.objects.GameObject;
import com.danick.e2.renderer.Graphic;

public class Main extends AbstractGame {
	public static Player p;
	@Override
	public void init(Graphic r) {
		r.bgColor = new Color(0xff6888f8);
		for (GameObject o : gameContainer.getObjects()) gameContainer.removeObject(o);
		p = new Player(1, 15, 1);
		gameContainer.addObject(p);
		
		gameContainer.addObject(new Wall(-1, 17, 54, 4));
		gameContainer.addObject(new Wall(55, 17, 15, 4));
		gameContainer.addObject(new Wall(73, 17, 66, 4));
		gameContainer.addObject(new Wall(141, 17, 50, 4));
		gameContainer.addObject(new Pipe(15, 15, 2));
		gameContainer.addObject(new Block(12, 13));
		gameContainer.addObject(new QBlock(11, 13));
		gameContainer.addObject(new Block(10, 13));
		gameContainer.addObject(new QBlock(9, 13, new Mushroom(9, 12)));
		gameContainer.addObject(new Block(8, 13));
		gameContainer.addObject(new QBlock(5, 13));
		gameContainer.addObject(new QBlock(10, 9));
		gameContainer.addObject(new Pipe(24, 14, 3));
		gameContainer.addObject(new Pipe(31, 13, 4));
		gameContainer.addObject(new Pipe(41, 13, 4));
		gameContainer.addObject(new QBlock(48, 12, new OneUp(48, 11)));
		gameContainer.addObject(new Block(62, 13));
		gameContainer.addObject(new QBlock(63, 13, new Mushroom(63, 12)));
		gameContainer.addObject(new Block(64, 13));
		for (int x = 65; x < 73; x++) gameContainer.addObject(new Block(x, 9));
		for (int x = 76; x < 80; x++) gameContainer.addObject(new Block(x, 9));
		gameContainer.addObject(new QBlock(80, 9));
		gameContainer.addObject(new Block(80, 13));
		gameContainer.addObject(new Block(86, 13));
		gameContainer.addObject(new Block(87, 13));
		gameContainer.addObject(new QBlock(92, 13));
		gameContainer.addObject(new QBlock(95, 13));
		gameContainer.addObject(new QBlock(98, 13));
		gameContainer.addObject(new QBlock(95, 9, new Mushroom(95, 8)));
		gameContainer.addObject(new Block(104, 13));
		gameContainer.addObject(new Block(107, 9));
		gameContainer.addObject(new Block(108, 9));
		gameContainer.addObject(new Block(109, 9));
		gameContainer.addObject(new Block(114, 9));
		gameContainer.addObject(new QBlock(115, 9));
		gameContainer.addObject(new QBlock(116, 9));
		gameContainer.addObject(new Block(117, 9));
		gameContainer.addObject(new Block(115, 13));
		gameContainer.addObject(new Block(116, 13));
		for (int y = 0; y < 4; y++) for (int x = y; x < 4; x++) gameContainer.addObject(new Block(120+x, 16-y));
		for (int y = 0; y < 4; y++) for (int x = 0; x < 4-y; x++) gameContainer.addObject(new Block(126+x, 16-y));
		for (int y = 0; y < 4; y++) for (int x = y; x < 4; x++) gameContainer.addObject(new Block(134+x, 16-y));
		for (int y = 0; y < 4; y++) gameContainer.addObject(new Block(138, 16-y));
		for (int y = 0; y < 4; y++) for (int x = 0; x < 4-y; x++) gameContainer.addObject(new Block(141+x, 16-y));
		gameContainer.addObject(new Pipe(149, 15, 2));
		gameContainer.addObject(new Block(154, 13));
		gameContainer.addObject(new Block(155, 13));
		gameContainer.addObject(new QBlock(156, 13));
		gameContainer.addObject(new Block(157, 13));
		gameContainer.addObject(new Pipe(165, 15, 2));
		for (int y = 0; y < 8; y++) for (int x = y; x < 8; x++) gameContainer.addObject(new Block(167+x, 16-y));
		for (int y = 0; y < 8; y++) gameContainer.addObject(new Block(175, 16-y));
		gameContainer.addObject(new Block(184, 16));
		
		
		gameContainer.addObject(new Goomba(11, 15));
		gameContainer.addObject(new Goomba(17, 15));
		gameContainer.addObject(new Goomba(35, 15));
		gameContainer.addObject(new Goomba(37, 15));
		gameContainer.addObject(new Goomba(65, 7));
		gameContainer.addObject(new Goomba(67, 7));
	}

	@Override
	public void update(long dt) {
		if (p.x > gameContainer.width/2 && r.transX + gameContainer.width/2 < p.x-1) {
			r.translate(1, 0);
		} else if (p.x > gameContainer.width/2 && r.transX + gameContainer.width/2 > p.x +1) {
			r.translate(-1, 0);
		} else if (p.x <= gameContainer.width/2 && r.transX > 0) {
			r.translate(-1, 0);
		}
		if (gameContainer.input.isKeyDown(KeyEvent.VK_R)) init(gameContainer.game.r);
		if (p.y > gameContainer.height*2) init(gameContainer.game.r);
	}

	@Override
	public void render(Graphic r) {
		r.clear();
	}

	public static void main(String[] args) {
		GameContainer gameContainer = new GameContainer(new Main(),400, 210, 3, "SuperMarioBros");
		gameContainer.start();
	}
}
