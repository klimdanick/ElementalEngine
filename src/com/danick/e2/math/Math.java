package com.danick.e2.math;

import com.danick.e2.renderer.Graphic3D.Point3D;

public class Math {
	public static double distance(double x1, double y1, double x2, double y2) {
		return java.lang.Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
	}

	public static double sqrt(double d) {
		return java.lang.Math.sqrt(d);
	} 
	
	public static class MatriMath {
		public static double[][] multiplyMatrix(double[][] A, double[][] B) {
		    int mA = A.length;
		    int nA = A[0].length;
		    int mB = B.length;
		    int nB = B[0].length;
		    
		    if (nA != mB) throw new IllegalArgumentException("Cannot multiply matrices: incorrect dimensions");
		    
		    double[][] C = new double[mA][nB];
		    for (int i = 0; i < mA; i++) for (int j = 0; j < nB; j++) for (int k = 0; k < nA; k++)
		                C[i][j] += A[i][k] * B[k][j];
		    return C;
		}
		
		public static double[][] multiplyMatrix(Point3D Vec, double[][] B) {
			double[][] A = {{Vec.x, Vec.y, Vec.z, Vec.H}};
		    int mA = A.length;
		    int nA = A[0].length;
		    int mB = B.length;
		    int nB = B[0].length;
		    
		    if (nA != mB) throw new IllegalArgumentException("Cannot multiply matrices: incorrect dimensions");
		    
		    double[][] C = new double[mA][nB];
		    for (int i = 0; i < mA; i++) for (int j = 0; j < nB; j++) for (int k = 0; k < nA; k++)
		    	C[i][j] += A[i][k] * B[k][j];
		    return C;
		}
	}
}