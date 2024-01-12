package com.danick.e2.QSM;

import com.danick.e2.main.GameContainer;

public abstract class AbstractQSMState {
	public AbstractQSM qsm;
	public GameContainer gc;
	public abstract boolean run();
	public abstract void init();
}
