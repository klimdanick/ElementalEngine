package com.danick.e2.renderer;

import java.awt.Color;
import java.awt.color.ColorSpace;

public class E2Color extends Color {
	
	public static E2Color CURIOS_BLUE = new E2Color(0xFF299ad0);
	public static E2Color TURMERIC_YELLOW = new E2Color(0xFFd0b747);
	public static E2Color AQUA_GREEN = new E2Color(0xFF05d993);
	public static E2Color DEBIAN_RED = new E2Color(0xFFd70e48);
	public static E2Color CINDER_BLACK = new E2Color(0xFF081017);
	
	// E2Color[] primary = {new E2Color(0xAAd0b747), new E2Color(0xAA299ad0), new E2Color(0xAA05d993), new E2Color(0xAAd70e48)};

	public E2Color(int rgb) {
		super(rgb, true);
	}
	
	public E2Color(int red, int green, int blue, int alpha) {
		super(red, green, blue, alpha);
		
	}

	public E2Color changeAlpha(int Alpha) {
		return new E2Color(this.getRed(), this.getGreen(), this.getBlue(), Alpha);
	}

}
