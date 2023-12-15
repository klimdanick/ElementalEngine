package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

public class server {
	
	
	public server(int port) throws IOException {
		// Step 1 : Create a socket to listen at port 1234
		
		
	}
	
	public static void main(String[] args) throws IOException {
		Thread t = new Thread() {
			public void run() {
				try {
					server s = new server(1234);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
}
