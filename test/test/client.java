package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class client {
	DatagramSocket ds;
	InetAddress adress;
	byte buf[] = new byte[900];
	Thread senThread;
	Thread recThread;
	
	Message msg = null; 
	
	public client(String ip, int port) throws IOException {
		ds = new DatagramSocket();
		InetAddress adress = InetAddress.getByName(ip);
		this.adress = adress;
		this.senThread = new Thread() {
			public void run() {
				while(true) {
					while (msg == null)
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					
					byte[] buffer = new byte[Message.MAX_BUFFER_SIZE+Message.HEADER_SIZE];
					buffer[0] = msg.id;
					buffer[1] = msg.part;
					buffer[2] = msg.type;
					buffer[3] = (byte) (msg.data.length / (buffer.length-4)+1);
					int startPos = msg.index;
					for (; msg.index-startPos < buffer.length-Message.HEADER_SIZE && msg.index < msg.data.length; msg.index++) {
						buffer[msg.index+Message.HEADER_SIZE-startPos] = msg.data[msg.index];
					}
					
					
					DatagramPacket DpSend = new DatagramPacket(buffer, buffer.length, adress, port); 

					try {
						ds.send(DpSend);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if (msg.index >= msg.data.length) msg = null;
					else msg.part++;
				}
			}
		};
		senThread.start();
		
		this.recThread = new Thread() {
			public void run() {
				while(true) {
					
				}
			}
		};
		recThread.start();
	}
	
	
	public static void main(String[] args) throws IOException {
		Thread t = new Thread() {
			public void run() {
				Scanner sc = new Scanner(System.in); 
				client c = null;
				while (c == null) try {
					c = new client("localhost", 1234);
				} catch (IOException e) {
					e.printStackTrace();
				}
				while(true) {
					while (c.msg != null) try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("msg send!");
					String s = sc.nextLine();
					Message m = new Message((byte)1, s.getBytes());
					c.msg = m;
					System.out.println("msg sending");
				}
			}
		};
		t.start();
	}
}
