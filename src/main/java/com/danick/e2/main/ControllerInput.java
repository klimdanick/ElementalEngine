package com.danick.e2.main;

public enum ControllerInput {
	LjoyX(true),
	LjoyY(true),
	RjoyX(true),
	RjoyY(true),
	LjoyZ(false),
	RjoyZ(false),
	Dpad(true),
	DpadN(false),
	DpadE(false),
	DpadS(false),
	DpadW(false),
	Start(false),
	Select(false),
	A(false),
	B(false),
	C(false),
	D(false),
	L1(false),
	L2(true),
	R1(false),
	R2(true),
	Null(false);
	
	boolean isAnalog;
	
	ControllerInput(boolean a) {
		isAnalog = a;
	}
	
	public static ControllerInput getInput(String s) {
		ControllerInput i = A;
		switch (s) {
			case "X Axis":
				return LjoyX;
			case "Y Axis":
				return LjoyY;
			case "Z Axis":
				return RjoyX;
			case "Z Rotation":
				return RjoyY;
			case "Button 10":
				return LjoyZ;
			case "Button 11":
				return RjoyZ;
			case "Button 4":
				return L1;
			case "Button 5":
				return R1;
			case "X Rotation":
				return L2;
			case "Y Rotation":
				return R2;
			case "Hat Switch":
				return Dpad;
			case "Button 0":
				return A;
			case "Button 1":
				return B;
			case "Button 2":
				return C;
			case "Button 3":
				return D;
			case "Button 9":
				return Start;
			case "Button 8":
				return Select;
			default:
				return Null;
		}
	}
}
