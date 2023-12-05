package com.danick.e2.renderer;

import java.awt.Color;
import java.util.ArrayList;

import com.danick.e2.math.Math;
import com.danick.e2.math.PerspectiveProjection;
import com.danick.e2.math.Projection;

public class Graphic3D extends Graphic{
	/*
	public int pW, pH;
	int[] p;
	public int offX, offY;
	GameContainer gc;
	private font Font = font.STANDARD;
	*/
	
	public Projection projection;
	
	public Graphic3D(int width, int height) {
		super(width, height);
		projection = new PerspectiveProjection();
	}
	
	public Graphic3D(int width, int height, Projection p) {
		super(width, height);
		projection = p;
	}
	
	public void drawModel(Model3D model) {
		for (Face3D face : model.faces) {
		ArrayList<Point3D> verts = face.verts;
		double[][] Pr = com.danick.e2.math.Math.MatriMath.multiplyMatrix(verts.get(verts.size()-1), model.TransformationMatrix);
		Point3D prevPoint = projection.project(new Point3D((int)Pr[0][0], (int)Pr[0][1], (int)Pr[0][2]));
		  for (Point3D p : verts) {
			  
			  double[][] P = com.danick.e2.math.Math.MatriMath.multiplyMatrix(p, model.TransformationMatrix);
			  Point3D proj = projection.project(new Point3D((int)P[0][0], (int)P[0][1], (int)P[0][2]));
			  //this.drawCircle(proj.x+this.pW/2, proj.y+this.pH/2, 10, Color.red, true, 100000);
			  this.drawLine(proj.x+this.pW/2, proj.y+this.pH/2, prevPoint.x+this.pW/2, prevPoint.y+this.pH/2, Color.white);
			  prevPoint = proj;
		  }
		}
	}
	
	public static class Model3D {
		public ArrayList<Face3D> faces = new ArrayList<>();
		public double[][] TransformationMatrix = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
		
		public void TranslateOrigin(double x, double y, double z) {
			TransformationMatrix[3][0]+=x;
			TransformationMatrix[3][1]+=y;
			TransformationMatrix[3][2]+=z;
		}
		
		public void TranslateVerts(double x, double y, double z) {
			ArrayList<Point3D> allVerts = new ArrayList<>();
			for (Face3D face : this.faces) for (Point3D p : face.verts)
				if (!allVerts.contains(p)) allVerts.add(p);
			
			for (Point3D p : allVerts) {
				p.x += x;
				p.y += y;
				p.z += z;
			}
		}
		
		public void RotateVerts(double x, double y, double z) {
			double[][] rotationMatrix = {{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
			double[][] rotationX = {{1, 0, 0, 0}, {0, java.lang.Math.cos(x), -java.lang.Math.sin(x), 0}, {0, java.lang.Math.sin(x), java.lang.Math.cos(x), 0}, {0, 0, 0, 1}};
			rotationMatrix = Math.MatriMath.multiplyMatrix(rotationX, rotationMatrix);
			double[][] rotationY = {{java.lang.Math.cos(y), 0, java.lang.Math.sin(y), 0}, {0, 1, 0, 0}, {-java.lang.Math.sin(y), 0, java.lang.Math.cos(y), 0}, {0, 0, 0, 1}};
			rotationMatrix = Math.MatriMath.multiplyMatrix(rotationY, rotationMatrix);
			double[][] rotationZ = {{java.lang.Math.cos(z), -java.lang.Math.sin(z), 0, 0}, {java.lang.Math.sin(z), java.lang.Math.cos(z), 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
			rotationMatrix = Math.MatriMath.multiplyMatrix(rotationZ, rotationMatrix);
			ArrayList<Point3D> allVerts = new ArrayList<>();
			for (Face3D face : this.faces) for (Point3D p : face.verts)
				if (!allVerts.contains(p)) allVerts.add(p);
			
			for (Point3D p : allVerts) {
				double[][] P = Math.MatriMath.multiplyMatrix(p, rotationMatrix);
				p.x = P[0][0];
				p.y = P[0][1];
				p.z = P[0][2];
			}
		}
		
		public void RotateOrigin(double x, double y, double z) {
			double[][] rotationX = {{1, 0, 0, 0}, {0, java.lang.Math.cos(x), -java.lang.Math.sin(x), 0}, {0, java.lang.Math.sin(x), java.lang.Math.cos(x), 0}, {0, 0, 0, 1}};
			TransformationMatrix = Math.MatriMath.multiplyMatrix(rotationX, TransformationMatrix);
			double[][] rotationY = {{java.lang.Math.cos(y), 0, java.lang.Math.sin(y), 0}, {0, 1, 0, 0}, {-java.lang.Math.sin(y), 0, java.lang.Math.cos(y), 0}, {0, 0, 0, 1}};
			TransformationMatrix = Math.MatriMath.multiplyMatrix(rotationY, TransformationMatrix);
			double[][] rotationZ = {{java.lang.Math.cos(z), -java.lang.Math.sin(z), 0, 0}, {java.lang.Math.sin(z), java.lang.Math.cos(z), 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
			TransformationMatrix = Math.MatriMath.multiplyMatrix(rotationZ, TransformationMatrix);
		}
		
		public static Model3D plane(int scale) {
			ArrayList<Point3D> verts = new ArrayList<>();
			for (int z = -1; z <= 1; z+=2) for (int y = -1; y <= 1; y+=2) for (int x = -1; x <= 1; x+=2) {
				verts.add(new Point3D(x*scale/2, y*scale/2, z*scale/2));
			}
			ArrayList<Face3D> faces = new ArrayList<>();
			ArrayList<Point3D> pointsTop = new ArrayList<>();
			pointsTop.add(verts.get(0));
			pointsTop.add(verts.get(1));
			pointsTop.add(verts.get(3));
			pointsTop.add(verts.get(2));
			faces.add(new Face3D(pointsTop));
			return new Model3D(faces);
		}
		
		public static Model3D hexagon(int scale) {
			ArrayList<Point3D> verts = new ArrayList<>();
			for (double a = 0; a <= 2*java.lang.Math.PI; a+=java.lang.Math.PI/3) {
				verts.add(new Point3D(java.lang.Math.cos(a)*scale/2, java.lang.Math.sin(a)*scale/2, 0));
			}
			ArrayList<Face3D> faces = new ArrayList<>();
			ArrayList<Point3D> pointsTop = new ArrayList<>();
			pointsTop.add(verts.get(0));
			pointsTop.add(verts.get(1));
			pointsTop.add(verts.get(2));
			pointsTop.add(verts.get(3));
			pointsTop.add(verts.get(4));
			pointsTop.add(verts.get(5));
			faces.add(new Face3D(pointsTop));
			return new Model3D(faces);
		}
		
		public static Model3D cube(int scale) {
			ArrayList<Point3D> verts = new ArrayList<>();
			for (int z = -1; z <= 1; z+=2) for (int y = -1; y <= 1; y+=2) for (int x = -1; x <= 1; x+=2) {
				verts.add(new Point3D(x*scale/2, y*scale/2, z*scale/2));
			}
			ArrayList<Face3D> faces = new ArrayList<>();
			ArrayList<Point3D> pointsTop = new ArrayList<>();
			pointsTop.add(verts.get(0));
			pointsTop.add(verts.get(1));
			pointsTop.add(verts.get(5));
			pointsTop.add(verts.get(4));
			faces.add(new Face3D(pointsTop));
			ArrayList<Point3D> pointsFront = new ArrayList<>();
			pointsFront.add(verts.get(1));
			pointsFront.add(verts.get(3));
			pointsFront.add(verts.get(7));
			pointsFront.add(verts.get(5));
			faces.add(new Face3D(pointsFront));
			ArrayList<Point3D> pointsBack = new ArrayList<>();
			pointsBack.add(verts.get(0));
			pointsBack.add(verts.get(1));
			pointsBack.add(verts.get(3));
			pointsBack.add(verts.get(2));
			faces.add(new Face3D(pointsBack));
			ArrayList<Point3D> pointsLeft = new ArrayList<>();
			pointsLeft.add(verts.get(0));
			pointsLeft.add(verts.get(2));
			pointsLeft.add(verts.get(6));
			pointsLeft.add(verts.get(4));
			faces.add(new Face3D(pointsLeft));
			ArrayList<Point3D> pointsRight = new ArrayList<>();
			pointsRight.add(verts.get(1));
			pointsRight.add(verts.get(3));
			pointsRight.add(verts.get(7));
			pointsRight.add(verts.get(5));
			faces.add(new Face3D(pointsRight));
			ArrayList<Point3D> pointsBottom = new ArrayList<>();
			pointsBottom.add(verts.get(2));
			pointsBottom.add(verts.get(3));
			pointsBottom.add(verts.get(7));
			pointsBottom.add(verts.get(6));
			faces.add(new Face3D(pointsBottom));
			return new Model3D(faces);
		}
		
		public static Model3D Pyramid(int scale) {
			ArrayList<Point3D> verts = new ArrayList<>();
			for (int z = -1; z <= 1; z+=2) for (int x = -1; x <= 1; x+=2) {
				verts.add(new Point3D(x*scale/2, scale/2, z*scale/2));
			}
			verts.add(new Point3D(0, -scale/2, 0));
			ArrayList<Face3D> faces = new ArrayList<>();
			ArrayList<Point3D> pointsTop = new ArrayList<>();
			pointsTop.add(verts.get(0));
			pointsTop.add(verts.get(1));
			pointsTop.add(verts.get(3));
			pointsTop.add(verts.get(2));
			faces.add(new Face3D(pointsTop));
			ArrayList<Point3D> pointsFront = new ArrayList<>();
			pointsFront.add(verts.get(0));
			pointsFront.add(verts.get(1));
			pointsFront.add(verts.get(4));
			faces.add(new Face3D(pointsFront));
			ArrayList<Point3D> pointsBack = new ArrayList<>();
			pointsBack.add(verts.get(1));
			pointsBack.add(verts.get(3));
			pointsBack.add(verts.get(4));
			faces.add(new Face3D(pointsBack));
			ArrayList<Point3D> pointsLeft = new ArrayList<>();
			pointsLeft.add(verts.get(2));
			pointsLeft.add(verts.get(3));
			pointsLeft.add(verts.get(4));
			faces.add(new Face3D(pointsLeft));
			ArrayList<Point3D> pointsRight = new ArrayList<>();
			pointsRight.add(verts.get(2));
			pointsRight.add(verts.get(0));
			pointsRight.add(verts.get(4));
			faces.add(new Face3D(pointsRight));
			return new Model3D(faces);
		}
		
		public Model3D(ArrayList<Face3D> faces) {
			this.faces = faces;
		}
	}
	
	public static class Face3D {
		public ArrayList<Point3D> verts = new ArrayList<>();
		public Color c = new Color(0xFF000000 + (int)(0xFFFFFF * java.lang.Math.random()));
		
		public static Face3D rect(int scale) {
			ArrayList<Point3D> verts = new ArrayList<>();
				verts.add(new Point3D((int)(-scale), (int)(-scale), 0));
				verts.add(new Point3D((int)(scale), (int)(-scale), 0));
				verts.add(new Point3D((int)(scale), (int)(scale), 0));
				verts.add(new Point3D((int)(-scale), (int)(scale), 0));
			return new Face3D(verts);
		}
		
		public Face3D(ArrayList<Point3D> verts) {
			this.verts = verts;
		}
	}
	
	public static class Point3D {
		public double x, y, z, H = 1;
		
		public Point3D(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		public Point3D(double[] pos) {
			this.x = pos[0];
			this.y = pos[1];
			this.z = pos[2];
		}
		
		public String toString() {
			return "{"+x+", "+y+", "+z+", "+H+"}";
		}
	}
	
	public void background() {
		for (int x = offX; x < pW+offX; x++) for (int y = offY; y < pH+offY; y++) {
			if ((int)java.lang.Math.abs(java.lang.Math.round(y/(float)(checkerPatternHeight)))%2 == (int)java.lang.Math.abs(java.lang.Math.round(x/(float)(checkerPatternWidth)))%2) setPixel(x, y, bgColor);
			else setPixel(x, y, bgColor.darker());
		} 
	}
	
	/*
	public void background() {
		for (int x = offX; x < pW+offX; x++) for (int y = offY; y < pH+offY; y++) {
			float[] hsb = Color.RGBtoHSB(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), null);
			setPixel(x, pH+offY-y-1, Color.getHSBColor(hsb[0], hsb[1], hsb[2]*y/pH+offY));
		} 
	}*/
}
