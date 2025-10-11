package com.danick.e2.math;

import com.danick.e2.renderer.Graphic3D.Point3D;

public interface Projection {
	public Point3D project(Point3D point);
}
