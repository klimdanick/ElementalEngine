package com.danick.e2.IDE;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.UIDefaults;
import javax.swing.UIManager;

import com.danick.e2.IDE.IDEGameContainer;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;

public class IDE {
	
	public static String version = "0.0.3";
	
	public static IDEGameContainer gc;
	
	public static void main(String[] args) {
		FlatMacDarkLaf.setup();
		gc = new IDEGameContainer();
	}
}
