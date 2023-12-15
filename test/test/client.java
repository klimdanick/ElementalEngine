package test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class client {
	DatagramSocket recDS;
	DatagramSocket senDS;
	InetAddress adress;
	byte buf[] = new byte[900];
	Thread senThread;
	Thread recThread;
	
	public static HashMap<Byte, Message> recMsgs = new HashMap<>();
	Queue<Message> senMsgs = new LinkedList<>(); 
	public client(String ip, int recPort, int senPort) throws IOException {
		this(recPort, senPort);
		this.adress = InetAddress.getByName(ip);
	}
	public client(int recPort, int senPort) throws IOException {
		senDS = new DatagramSocket();
		this.senThread = new Thread() {
			public void run() {
				while(true) {
					Message msg;
					while ((msg = senMsgs.poll()) == null)
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
					
					
					DatagramPacket DpSend = new DatagramPacket(buffer, buffer.length, adress, senPort); 

					try {
						senDS.send(DpSend);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if (msg.index >= msg.data.length) {
						System.out.println("SEND: " + new String(msg.data));
						msg = null;
					}
					else msg.part++;
				}
			}
		};
		
		
		this.recThread = new Thread() {
			public void run() {
				try {
					recDS = new DatagramSocket(recPort);
					byte[] receive = new byte[65535];
	
					DatagramPacket DpReceive = null;
					while(true) {
	
						// Step 2 : create a DatgramPacket to receive the data.
						DpReceive = new DatagramPacket(receive, receive.length);
	
						// Step 3 : revieve the data in byte buffer.
						try {
							recDS.receive(DpReceive);
							Message m = data(receive);
							if (m.part == receive[3]) System.out.println("RESC: " + m);
						} catch (IOException e) {
							e.printStackTrace();
						}
	
						// Clear the buffer after every message.
						receive = new byte[65535];
					}
				} catch (SocketException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public void start() {
		senThread.start();
		recThread.start();
	}
	
	public Message sendMessage(int type, byte[] data) {
		Message m = new Message((byte)type, data);
		senMsgs.add(m);
		return m;
	}
	
	public static client startClient(String ip, int senPort, int recPort) {
		client c = null;
		while (c == null) try {
			c = new client(ip, senPort, recPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		c.start();
		return c;
	}
	
	public static client startServer(int senPort, int recPort) {
		client c = null;
		while (c == null) try {
			c = new client(senPort, recPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		c.start();
		return c;
	}
	
	public static Message data(byte[] a) {
		if (a == null)
			return null;
		Message m = recMsgs.get(a[0]);
		if (m == null) {
			//System.out.println("NEW MESSAGE!");
			m = new Message(a[0]);
			recMsgs.put(a[0], m);
			m.part = a[1];
			m.type = a[2];
			m.data = new byte[a[3]*Message.MAX_BUFFER_SIZE];
			//System.out.println(m);
		}
		m.part = a[1];
		for (int i = 0; i < Message.MAX_BUFFER_SIZE && i < a.length; i++) {
			m.data[i+((m.part-1) * Message.MAX_BUFFER_SIZE)] = a[i+Message.HEADER_SIZE];
		}
		if (m.part == a[3]) recMsgs.put(a[0], null);
		return m;
		
	}
	
	public static void main(String[] args) throws IOException {
		Thread t = new Thread() {
			public void run() {
				Scanner sc = new Scanner(System.in); 
				client c = client.startClient("localhost", 1233, 1234);
				while(true) {
					String s = sc.nextLine();
					c.sendMessage((byte)1, s.getBytes());
				}
			}
		};
		t.start();
	}
}
