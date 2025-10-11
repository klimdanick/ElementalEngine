package com.danick.e2.QSM;

import com.danick.e2.main.GameContainer;

public class SleepState extends AbstractQSMState{
	
	long t = -1;
	long Dt;
	
	public SleepState(long Dt) {
		this.Dt = Dt;
	}
	
	@Override
	public boolean run() {
		if (t == -1) {
			System.out.println("Going to sleep!");
			t = System.currentTimeMillis(); 
		}
		boolean wake = System.currentTimeMillis() - t >= Dt;
		if (wake) System.out.println("Woke!");
		return wake;
	}

	@Override
	public void init() {
	}

}