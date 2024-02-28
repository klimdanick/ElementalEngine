package Astroids;

import java.io.IOException;

import com.danick.e2.Networking.Message;
import com.danick.e2.Networking.NetworkPort;
import com.danick.e2.Networking.messageReceiveEvent;

public class Server {

	public static void main(String[] args) throws IOException {
		NetworkPort server = new NetworkPort(2030);
		Astroid a = new Astroid(10);
		server.addReceiveEvent(new messageReceiveEvent() {
			public void onMessage(Message msg) {
				//System.out.println("recieved: " + msg.data);
				byte[] bytes = a.serialize();
				System.out.print("sending: ");
				for (byte b : bytes) System.out.print(b + " ");
				System.out.println();
				server.sendMessage(0, bytes, msg.sendAdress, msg.sendPort);
				a.update(null);
			}
		}, (byte)0);
		server.start();
	}

}
