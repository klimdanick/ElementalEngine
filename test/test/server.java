package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;

public class server {
	
	public static HashMap<Byte, Message> msgs = new HashMap<>();
	
	public static void main(String[] args) throws IOException {
		// Step 1 : Create a socket to listen at port 1234
		DatagramSocket ds = new DatagramSocket(1234);
		byte[] receive = new byte[65535];

		DatagramPacket DpReceive = null;
		while (true) {

			// Step 2 : create a DatgramPacket to receive the data.
			DpReceive = new DatagramPacket(receive, receive.length);

			// Step 3 : revieve the data in byte buffer.
			ds.receive(DpReceive);
			
			Message m = data(receive);
			if (m.part == receive[3]) System.out.println("Client: " + m);

			// Exit the server if the client sends "bye"
			if (data(receive).toString().equals("bye")) {
				System.out.println("Client sent bye.....EXITING");
				break;
			}

			// Clear the buffer after every message.
			receive = new byte[65535];
		}
	}

	public static Message data(byte[] a) {
		if (a == null)
			return null;
		Message m = msgs.get(a[0]);
		if (m == null) {
			//System.out.println("NEW MESSAGE!");
			m = new Message(a[0]);
			msgs.put(a[0], m);
			m.part = a[1];
			m.type = a[2];
			m.data = new byte[a[3]*Message.MAX_BUFFER_SIZE];
			//System.out.println(m);
		}
		m.part = a[1];
		for (int i = 0; i < Message.MAX_BUFFER_SIZE && i < a.length; i++) {
			m.data[i+((m.part-1) * Message.MAX_BUFFER_SIZE)] = a[i+Message.HEADER_SIZE];
		}
		if (m.part == a[3]) msgs.put(a[0], null);
		return m;
		
	}
}
