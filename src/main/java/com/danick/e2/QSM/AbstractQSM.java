package com.danick.e2.QSM;

import java.util.ArrayList;

import com.danick.e2.main.GameContainer;

public abstract class AbstractQSM {
	public GameContainer gc;
	public AbstractQSMState defaultState;
	public ArrayList<AbstractQSMState> queue = new ArrayList<>();
	
	public void start(GameContainer gc, AbstractQSMState defaultState) {
		this.gc = gc;
		this.defaultState = defaultState;
		gc.game.QSMs.add(this);
	}
	
	public abstract void update();
	
	public void stop() {
		gc.game.QSMs.remove(this);
	}
	
	public void add(AbstractQSMState state, int index) {
		state.qsm = this;
		state.gc = gc;
		state.init();
		queue.add(index, state);
	}
	
	public void add(AbstractQSMState state) {
		state.qsm = this;
		state.gc = gc;
		state.init();
		queue.add(state);
	}
	
	public void remove(int index) {
		queue.remove(index);
	}
	
	public void remove(AbstractQSMState state) {
		queue.remove(state);
	}
	
	public void remove() {
		queue.remove(0);
	}
}
