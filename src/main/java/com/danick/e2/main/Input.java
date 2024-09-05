package com.danick.e2.main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;



public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	
	private GameContainer gc;
	
	private final int NUM_KEYS = 512;
	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] keysLast = new boolean[NUM_KEYS];
	private boolean[] keysDown = new boolean[NUM_KEYS];
	private boolean[] keysUp = new boolean[NUM_KEYS];
	
	private final int NUM_BUTTONS = 10;
	private boolean[] buttons = new boolean[NUM_BUTTONS];
	private boolean[] buttonsLast = new boolean[NUM_BUTTONS];
	private boolean[] buttonsDown = new boolean[NUM_BUTTONS];
	private boolean[] buttonsUp = new boolean[NUM_BUTTONS];
	
	public ArrayList<Controller> controllers = new ArrayList<>();
	
	private int mouseX, mouseY;
	private int scroll;
	
	public char lastKey = '\0';
	
	public Input(GameContainer gc) {
		this.gc = gc;
		mouseX = 0;
		mouseY = 0;
		scroll = 0;
		
		gc.window.canvas.addKeyListener(this);
		gc.window.canvas.addMouseListener(this);
		gc.window.canvas.addMouseMotionListener(this);
		gc.window.canvas.addMouseWheelListener(this);
		
		net.java.games.input.Controller[] cs = ControllerEnvironment.getDefaultEnvironment().getControllers();
		for (int i = 0; i < cs.length; i++) {
			if (cs[i].getType().equals(Type.GAMEPAD)) controllers.add(new Controller(cs[i]));
		}
		controllers.add(new Controller());
	}
	
	public void update() {
		scroll = 0;
		
		for (Controller c : controllers) c.update();
	}
	
	public boolean isKey(int keyCode) {
		return keys[keyCode];
	}
	
	public boolean isKeyUp(int keyCode) {
		boolean r = keysUp[keyCode];
		keysUp[keyCode] = false;
		return r;
	}
	
	public boolean isKeyDown(int keyCode) {
		boolean r = keysDown[keyCode];
		keysDown[keyCode] = false;
		return r;
	}
	
	public boolean isButton(int Button) {
		return buttons[Button];
	}
	
	public boolean isButtonUp(int Button) {
		boolean r = buttonsUp[Button];
		buttonsUp[Button] = false;
		return r;
	}
	
	public boolean isButtonDown(int Button) {
		boolean r = buttonsDown[Button];
		buttonsDown[Button] = false;
		return r;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = (int)(e.getX()/(gc.window.frame.getWidth()/(double)gc.width));
		mouseY = (int)(e.getY()/(gc.window.frame.getWidth()/(double)gc.width));
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = (int)(e.getX()/(gc.window.frame.getWidth()/(double)gc.width));
		mouseY = (int)(e.getY()/(gc.window.frame.getWidth()/(double)gc.width));
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttonsDown[e.getButton()] = true;
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
		buttonsUp[e.getButton()] = true;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keysDown[e.getKeyCode()] = true;
		keys[e.getKeyCode()] = true;
		lastKey = e.getKeyChar();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		keysUp[e.getKeyCode()] = true;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getScroll() {
		return scroll;
	}
	
	public boolean isMouseInBox(double x1, double y1, double x2, double y2) {
		return (mouseX > x1 && mouseX < x2 && mouseY > y1 && mouseY < y2);
	}

	public boolean isAnyKeyPressed() {
		for (boolean k : keys) if (k) return true;
		return false;
	}
	
	public boolean isAnyKeyDown() {
		for (boolean k : keysDown) if (k) return true;
		return false;
	}
	
	public boolean isAnyKeyUp() {
		for (boolean k : keysUp) if (k) return true;
		return false;
	}
}
 