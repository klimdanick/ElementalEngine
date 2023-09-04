package com.danick.e2.math;

import com.danick.e2.renderer.Graphic3D.Point3D;

public class PerspectiveProjection implements Projection{

	public double FOV = 100;
	
	@Override
	public Point3D project(Point3D point2) {
		Point3D point = new Point3D(point2.x, point2.y, point2.z/FOV+2);
		//double[] e = {0, 0, 1};
		double[][] m = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
		double[][] f = Math.MatriMath.multiplyMatrix(point, m);
		Point3D b;
		if (point.z > 0) b = new Point3D(point.x/point.z, point.y/point.z,0);
		else b = new Point3D(point.x, point.y, 0);
		return b;
	}

}
