package com.danick.engine.gfx.gfx3d;

import java.util.ArrayList;

import com.danick.engine.GameContainer;
import com.danick.engine.renderer;

public class Renderer3D extends renderer{
	private int[][] renderPixels;
	private int windowWidth;
	private int windowHeight;
	private int voxMapWidth, voxMapHeight, voxMapDepth;
	private boolean[][][] voxMap;
	private int[][][] colorMap;
	private int FOV = 90;
	private int res = 6;
	private int renderdistance;
	private ArrayList<Object3D> objects = new ArrayList<Object3D>();
	
	public Renderer3D(int windowWidth, int windowHeight, int resolution, int voxelWorldWidth, int voxelWorldHeight, int voxelWorldDepth, int renderDistance, int FOV, GameContainer gc) {
		super(gc);
		this.renderPixels = new int[windowWidth][windowHeight];
		this.windowHeight = windowHeight;
		this.windowWidth = windowWidth;
		this.res = resolution;
		this.voxMapWidth = voxelWorldWidth;
		this.voxMapHeight = voxelWorldHeight;
		this.voxMapDepth = voxelWorldDepth;
		this.renderdistance = renderDistance;
		this.FOV = FOV;
		this.voxMap = new boolean[voxMapWidth][voxMapHeight][voxMapDepth];
	}
	
	public void addObject(Object3D object) {
		objects.add(object);
	}
	
	public void update(renderer renderer) {
		
	}
}
