package Astroids;

import java.io.IOException;

import com.danick.e2.Networking.Message;
import com.danick.e2.Networking.NetworkPort;
import com.danick.e2.Networking.messageReceiveEvent;

public class Client {

	public static void main(String[] args) throws IOException {
		NetworkPort client = new NetworkPort("localhost", 2030);
		client.addReceiveEvent(new messageReceiveEvent() {
			public void onMessage(Message msg) {
				System.out.print("recieved: ");
				for (byte b : msg.data) System.out.print(b + " ");
				System.out.println();
				client.sendMessage(0, "ping".getBytes());
			}
		}, (byte)0);
		client.sendMessage(0, "ping".getBytes());
		client.start();
	}
}
