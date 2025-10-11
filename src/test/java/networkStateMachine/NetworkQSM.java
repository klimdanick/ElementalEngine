package networkStateMachine;

import com.danick.e2.Networking.Message;
import com.danick.e2.QSM.AbstractQSM;
import com.danick.e2.QSM.AbstractQSMState;
import com.danick.e2.QSM.QueuedStateMachine;

public class NetworkQSM extends AbstractQSM {

	@Override
	public void update() {}
	
	public void update(Message msg) {
		if (queue.size() <= 0) queue.add(defaultState);
		AbstractQSMState aState = queue.get(0);
		if (!(aState instanceof NetworkState)) remove(aState);
		NetworkState state = (NetworkState) aState;
		state.qsm = this;
		if (state.run(msg)) remove(aState);
	}
}
