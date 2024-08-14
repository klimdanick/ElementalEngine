package com.danick.e2.IDE.Designer;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.math.OrthographicProjection;
import com.danick.e2.math.PerspectiveProjection;
import com.danick.e2.renderer.Graphic;
import com.danick.e2.renderer.Graphic3D;
import com.danick.e2.renderer.Graphic3D.Face3D;
import com.danick.e2.renderer.Graphic3D.Model3D;
import com.danick.e2.renderer.Graphic3D.Point3D;

public class Designer extends AbstractGame{
	
	public static GameContainer gc;
	
	public static Graphic3D[] VP3D = new Graphic3D[4];
	
	public static void main(String[] args) {
		
		gc = new GameContainer(new Designer(), 400, 400, 0.75f, "ElementalDesigner");
		gc.start();
	}
	
	public Graphic3D.Model3D cube, pyramid, cube2, pyramid2;

	@Override
	public void init(Graphic r) {
		for (int i = 0; i < 4; i++) VP3D[i] = new Graphic3D(200, 200);
		cube = Model3D.cube(100);
		cube2 = Model3D.cube(100);
		//cube.Rotate(0, 0, 0);
		pyramid = Model3D.Pyramid(100);
		pyramid2 = Model3D.Pyramid(100);
		cube2.TranslateVerts(0, 100, 0);
		pyramid2.TranslateVerts(0, -100, 0);
		pyramid2.RotateVerts(Math.PI, 0, 0);
	}
	
	@Override
	public void render(Graphic r) {
		//VP3D[0].projection = new PerspectiveProjection();
		VP3D[0].bgColor = new Color(0x0066aa);
		VP3D[2].bgColor = VP3D[1].bgColor;
		VP3D[3].bgColor = VP3D[0].bgColor;
		for (int i = 0; i < 4; i++) VP3D[i].background();
		double rX = 0, rY = 0, rZ = 0;
		if (gc.input.isKey(KeyEvent.VK_D)) rY = 0.01;
		if (gc.input.isKey(KeyEvent.VK_A)) rY = -0.01;
		if (gc.input.isKey(KeyEvent.VK_W)) rX = 0.01;
		if (gc.input.isKey(KeyEvent.VK_S)) rX = -0.01;
		if (gc.input.isKey(KeyEvent.VK_Q)) rZ = 0.01;
		if (gc.input.isKey(KeyEvent.VK_E)) rZ = -0.01;
		if (gc.input.isKey(KeyEvent.VK_P)) VP3D[0].projection = new PerspectiveProjection();
		if (gc.input.isKey(KeyEvent.VK_O)) VP3D[0].projection = new OrthographicProjection();
		for (int i = 0; i < 4; i++) {
			if (VP3D[i].projection instanceof PerspectiveProjection) {
				PerspectiveProjection p = (PerspectiveProjection) VP3D[i].projection;
				if (gc.input.isKey(KeyEvent.VK_R)) p.FOV += 1;
				if (gc.input.isKey(KeyEvent.VK_F)) p.FOV += -1;
			}
		}
		cube.RotateVerts(rX, rY, rZ);
		pyramid.RotateVerts(rX, rY, rZ);
		cube2.RotateVerts(rX, rY, rZ);
		pyramid2.RotateVerts(rX, rY, rZ);
		
		VP3D[0].drawModel(cube);
		VP3D[1].drawModel(pyramid);
		VP3D[0].drawModel(cube2);
		VP3D[1].drawModel(pyramid2);
		for (int i = 0; i < 4; i++) r.drawGraphic(VP3D[i], (i%2)*gc.width/2, Math.floor(i/2)*gc.height/2, 0);
		r.drawLine(0, gc.height/2, gc.width, gc.height/2, 0, Color.black);
		r.drawLine(gc.width/2, 0, gc.width/2, gc.height, 0, Color.black);
	}

	@Override
	public void update(long dt) {
		
	}
}
