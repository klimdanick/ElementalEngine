package networkStateMachine;

import java.io.IOException;

import com.danick.e2.Networking.Message;
import com.danick.e2.Networking.NetworkPort;
import com.danick.e2.Networking.messageReceiveEvent;
import com.danick.e2.QSM.QueuedStateMachine;

public class client {
	static NetworkPort client;
	static NetworkQSM qsm = new NetworkQSM();
	public static void main(String[] args) throws IOException {
		client = new NetworkPort("localhost", 2030);
		qsm.start(new NetworkState() {
			public void init() {}

			public boolean run(Message msg) {
				System.out.println("-> CON");
				client.sendMessage(Byte.MAX_VALUE, new byte[] {NetCommands.CON});
				qsm.add(new dataState());
				return true;
			}
		});
		client.addReceiveEvent(new messageReceiveEvent() {
			public void onMessage(Message msg) {
				qsm.update(msg);
			}
		}, Byte.MAX_VALUE);
		client.sendMessage(Byte.MAX_VALUE, new byte[] {NetCommands.CON});
		client.start();
	}
	public static class dataState extends NetworkState {
		public boolean run(Message msg) {
			if (msg.data[0] == NetCommands.ACK) {
				System.out.println("<- ACK");
				System.out.println("-> DATA");
				byte[] message = new byte[] {NetCommands.DATA, 'H', 'E', 'L', 'L', 'O'};
				client.sendMessage(Byte.MAX_VALUE, message);
				return true;
			} else return false;
		}

		public void init() {}
	}
}
