package com.danick.engine.gfx.gfx3d;

import java.awt.Color;

import com.danick.engine.gfx.Image;

public class Object3D {
	Image model;
	int x, y, z;
	boolean[][][] voxMap;
	int[][][] colorMap;

	public Object3D(Image model, int x, int y, int z) {
		this.model = model;
		this.x = x;
		this.y = y;
		this.z = z;

		for (int i = 0; i < model.getP().length; i++) {
			int X = (i % model.getW()) + x;
			int Y = (int) (Math.floor(Math.floor(i / model.getW()) % model.getW()) + y);
			int Z = (int) (Math.floor(i / Math.pow((model.getW()), 2) + z));

			Color c = new Color(model.getP()[i]);

			if (model.getP()[i] != 0xffff00ff)
				voxMap[X][Y][Z] = true;
			colorMap[X][Y][Z] = model.getP()[i];
		}
	}

	public boolean[][][] loadVoxMap(boolean[][][] voxelMap) {

		for (int X = 0; X < voxMap.length; X++) {
			for (int Y = 0; Y < voxMap[0].length; Y++) {
				for (int Z = 0; Z < voxMap[0][0].length; Z++) {
					voxelMap[X + x][Y + y][Z + z] = voxMap[X][Y][Z];
				}
			}
		}

		return voxelMap;
	}

	public int[][][] loadcolorMap(int[][][] ColorMap) {

		for (int X = 0; X < colorMap.length; X++) {
			for (int Y = 0; Y < colorMap[0].length; Y++) {
				for (int Z = 0; Z < colorMap[0][0].length; Z++) {
					ColorMap[X + x][Y + y][Z + z] = colorMap[X][Y][Z];
				}
			}
		}

		return ColorMap;
	}
}
