package com.danick.e2.main;

import java.util.HashMap;

import com.danick.e2.renderer.Graphic;

import net.java.games.input.Component;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class Controller {
	public static final HashMap<ControllerInput, Double> analog = new HashMap<>();
	public static final HashMap<ControllerInput, Boolean> digital = new HashMap<>();
	public static final HashMap<ControllerInput, Boolean> digitalDown = new HashMap<>();
	
	public static final HashMap<ControllerInput, Graphic> inputIcons = new HashMap<>();
	
	private net.java.games.input.Controller c;
	
	public Controller() {
		
		for (ControllerInput in : ControllerInput.values()) {
			analog.put(in, 0.0);
			digital.put(in, false);
			digitalDown.put(in, true);
		}
		
		inputIcons.put(ControllerInput.DpadN, Graphic.fromImage("dpad_north.png"));
		inputIcons.put(ControllerInput.DpadE, Graphic.fromImage("dpad_east.png"));
		inputIcons.put(ControllerInput.DpadS, Graphic.fromImage("dpad_south.png"));
		inputIcons.put(ControllerInput.DpadW, Graphic.fromImage("dpad_west.png"));
		inputIcons.put(ControllerInput.Dpad, Graphic.fromImage("dpad.png"));
		
		inputIcons.put(ControllerInput.A, Graphic.fromImage("A.png"));
		inputIcons.put(ControllerInput.B, Graphic.fromImage("B.png"));
		inputIcons.put(ControllerInput.C, Graphic.fromImage("C.png"));
		inputIcons.put(ControllerInput.D, Graphic.fromImage("D.png"));
	}
	
	public Controller(net.java.games.input.Controller c) {
		this.c = c;
		
		for (ControllerInput in : ControllerInput.values()) {
			analog.put(in, 0.0);
			digital.put(in, false);
			digitalDown.put(in, true);
		}
		
		inputIcons.put(ControllerInput.DpadN, Graphic.fromImage("dpad_north.png"));
		inputIcons.put(ControllerInput.DpadE, Graphic.fromImage("dpad_east.png"));
		inputIcons.put(ControllerInput.DpadS, Graphic.fromImage("dpad_south.png"));
		inputIcons.put(ControllerInput.DpadW, Graphic.fromImage("dpad_west.png"));
		inputIcons.put(ControllerInput.Dpad, Graphic.fromImage("dpad.png"));
		
		inputIcons.put(ControllerInput.A, Graphic.fromImage("A.png"));
		inputIcons.put(ControllerInput.B, Graphic.fromImage("B.png"));
		inputIcons.put(ControllerInput.C, Graphic.fromImage("C.png"));
		inputIcons.put(ControllerInput.D, Graphic.fromImage("D.png"));
	}
	
	public void update() {
		if (this.c == null) return;
		Event event = new Event();
		c.poll();

	    EventQueue queue = c.getEventQueue();

	    while (queue.getNextEvent(event)) {
	    	Component comp = event.getComponent();
		    ControllerInput i = ControllerInput.getInput(comp.getName());
		    double v = comp.getPollData();
		    if (i.equals(ControllerInput.Dpad)) {
		    	digital.put(ControllerInput.DpadN, false);
		    	digital.put(ControllerInput.DpadE, false);
		    	digital.put(ControllerInput.DpadS, false);
		    	digital.put(ControllerInput.DpadW, false);
		    	if (v == 0.25) digital.put(ControllerInput.DpadN, true); else digitalDown.put(ControllerInput.DpadN, true);
		    	if (v == 0.5) digital.put(ControllerInput.DpadE, true); else digitalDown.put(ControllerInput.DpadE, true);
		    	if (v == 0.75) digital.put(ControllerInput.DpadS, true); else digitalDown.put(ControllerInput.DpadS, true);
		    	if (v == 1) digital.put(ControllerInput.DpadW, true); else digitalDown.put(ControllerInput.DpadW, true);
		    }
		    analog.put(i, v);
		    if (v > 0.5) {
		    	digital.put(i, true);
		    } else {
		    	digital.put(i, false);
		    	digitalDown.put(i, true);
		    }
		}
	}
	
	public boolean digitalDown(ControllerInput inp) {
		if (digitalDown.get(inp)) {
			if (digital.get(inp)) digitalDown.put(inp, false);
			return digital.get(inp);
		}
		else return false;
	}
}
