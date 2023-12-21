package test;

import java.net.InetAddress;
import java.util.HashMap;

public class MultiplayerGameServer {

	static Client server;
	
	static HashMap<Byte, Integer> clientPorts = new HashMap<>();
	static HashMap<Byte, InetAddress> clientIPs = new HashMap<>();
	
	public static void main(String[] args) {
		server = Client.startServer(1234);
		server.addReceiveEvent(new messageReceiveEvent() {
			public void onMessage(Message msg) {
				clientPorts.putIfAbsent(msg.data[0], msg.sendPort);
				clientIPs.putIfAbsent(msg.data[0], msg.sendAdress);
			}
		}, (byte) 0);
		
		server.addReceiveEvent(new messageReceiveEvent() {
			public void onMessage(Message msg) {
				for (Byte id : clientPorts.keySet()) {
					Integer port = clientPorts.get(id);
					InetAddress ip = clientIPs.get(id);
					if (port != msg.sendPort) server.sendMessage(1, msg.data, ip, port);
				}
			}
		}, (byte) 1);
	}

}
