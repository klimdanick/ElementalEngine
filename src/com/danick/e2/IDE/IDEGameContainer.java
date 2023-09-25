package com.danick.e2.IDE;

import javax.swing.JFrame;

import com.danick.e2.IDE.Designer.Designer;
import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.main.Input;
import com.danick.e2.renderer.Window;
import com.danick.e2.renderer.Graphic;

public class IDEGameContainer extends GameContainer{

	public IDEGameContainer() {
		super(new GamePreview(), 800, 800, 1f, "Elemental Engine");
		this.window.frame.setVisible(false);
		this.window = new IDEWindow(this);

		input = new Input(this);
		AspectRatio = (float)height / (float)width;
		changeScene(game);
		//System.out.println(AspectRatio);
		window.frame.setVisible(true);
		window.scale(1);
		start();
	}

	
	public static String DefaultCode = "package NewProject;\r\n"
			+ "import com.danick.e2.main.AbstractGame;\r\n"
			+ "import com.danick.e2.main.GameContainer;\r\n"
			+ "import com.danick.e2.renderer.renderer;\r\n\r\n\r\n"
			+ "public class Main extends AbstractGame {\r\n"
			+ "	\r\n"
			+ "	public static void main(String args[]) {\r\n"
			+ "		GameContainer gc = new GameContainer(new Main());\r\n"
			+ "		gc.start();\r\n"
			+ "	}\r\n"
			+ "\r\n"
			+ "	@Override\r\n"
			+ "	public void init(GameContainer gc, renderer r) {\r\n"
			+ "	}\r\n"
			+ "\r\n"
			+ "	@Override\r\n"
			+ "	public void update(GameContainer gc, long dt) {\r\n"
			+ "	}\r\n"
			+ "\r\n"
			+ "	@Override\r\n"
			+ "	public void render(GameContainer gc, renderer r) {\r\n"
			+ "		r.clear();\r\n"
			+ "	}\r\n"
			+ "\r\n"
			+ "}";
	
}
