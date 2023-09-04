package com.danick.e2.math;

import com.danick.e2.renderer.Graphic3D.Point3D;

public class OrthographicProjection implements Projection{

	@Override
	public Point3D project(Point3D point) {
		return point;
	}

}
