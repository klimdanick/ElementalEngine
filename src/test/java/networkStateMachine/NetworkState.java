package networkStateMachine;

import com.danick.e2.Networking.Message;
import com.danick.e2.QSM.AbstractQSMState;

public abstract class NetworkState extends AbstractQSMState {

	public abstract boolean run(Message msg);
	public boolean run() {return false;}
}
