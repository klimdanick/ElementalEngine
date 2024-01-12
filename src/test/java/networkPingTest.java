import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.danick.e2.Networking.Message;
import com.danick.e2.Networking.NetworkPort;
import com.danick.e2.Networking.messageReceiveEvent;

class networkingTests {

	@Test
	void clientSetupTest() {
		NetworkPort client;
		try {
			client = new NetworkPort("localhost", 1234);
			client.start();
			client.close();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	void serverSetupTest() {
		NetworkPort server;
		try {
			server = new NetworkPort(1234);
			server.start();
			server.close();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	boolean resc = false;
	@Test
	void pingTest() {
		resc = false;
		NetworkPort client;
		NetworkPort server;
		try {
			client = new NetworkPort("localhost", 1234);
			client.start();
			server = new NetworkPort(1234);
			server.start();
			
			server.addReceiveEvent(new messageReceiveEvent() {
				public void onMessage(Message msg) {
					server.sendMessage(1, "pong".getBytes(), msg.sendAdress, msg.sendPort);
				}
			}, (byte)1);
			
			client.addReceiveEvent(new messageReceiveEvent() {
				public void onMessage(Message msg) {	
					resc = true;
				}
			}, (byte)1);
			
			client.sendMessage(1, "ping".getBytes());
			long t0 = System.currentTimeMillis();
			while(!resc && System.currentTimeMillis() - t0 < 2000);
			if (!resc) fail("Server did not respond in time!");
			server.close();
			client.close();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	void largeMessageTest() {
		resc = false;
		NetworkPort client;
		NetworkPort server;
		try {
			client = new NetworkPort("localhost", 1234);
			client.start();
			server = new NetworkPort(1234);
			server.start();
			
			server.addReceiveEvent(new messageReceiveEvent() {
				public void onMessage(Message msg) {
					System.out.println(msg.data.length);
					if (msg.data.length != 1500) fail("expected 1532 bytes but got" + msg.data.length);
					resc = true;
				}
			}, (byte)1);
			
			byte[] data = new byte[1500];
			for (int i = 0; i < data.length; i++) {
				data[i] = (byte)(Math.random()*Byte.MAX_VALUE);
			}
			
			client.sendMessage(1, data);
			long t0 = System.currentTimeMillis();
			while(!resc && System.currentTimeMillis() - t0 < 1000);
			if (!resc) fail("Server did not resceve in time!");
			server.close();
			client.close();
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}