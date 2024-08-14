package networkStateMachine;

import java.io.IOException;

import com.danick.e2.Networking.Message;
import com.danick.e2.Networking.NetworkPort;
import com.danick.e2.Networking.messageReceiveEvent;
import com.danick.e2.QSM.AbstractQSMState;
import com.danick.e2.QSM.QueuedStateMachine;

public class server {
	public static NetworkPort server;
	public static void main(String[] args) throws IOException {
		server = new NetworkPort(2030);
		NetworkQSM qsm = new NetworkQSM();
		qsm.start(new NetworkState() {
			public void init() {}

			public boolean run(Message msg) {
				if (msg.data[0] == NetCommands.CON) {
					System.out.println("<- CON");
					System.out.println("-> ACK");
					byte[] message = new byte[] {NetCommands.ACK};
					server.sendMessage(Byte.MAX_VALUE, message, msg.sendAdress, msg.sendPort);
					return true;
				} else return false;
			}
		});
		server.addReceiveEvent(new messageReceiveEvent() {
			public void onMessage(Message msg) {
				qsm.update(msg);
			}
		}, Byte.MAX_VALUE);
		server.start();
	}
	public static class dataState extends NetworkState {
		public boolean run(Message msg) {
			if (msg.data[0] == NetCommands.DATA) {
				System.out.print("<- DATA | ");
				for (int i = 1; i < msg.data.length; i++) System.out.print(msg.data[i]+" ");
				System.out.println();
				System.out.println("-> ACK");
				byte[] message = new byte[] {NetCommands.ACK};
				server.sendMessage(Byte.MAX_VALUE, message, msg.sendAdress, msg.sendPort);
				return true;
			} else return false;
		}

		public void init() {}
	}
}
