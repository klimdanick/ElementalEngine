package com.danick.e2.QSM;

public class QueuedStateMachine extends AbstractQSM{

	@Override
	public void update() {
		if (queue.size() <= 0) queue.add(defaultState);
		AbstractQSMState state = queue.get(0);
		if (state.run()) remove(state);
	}

	
}
