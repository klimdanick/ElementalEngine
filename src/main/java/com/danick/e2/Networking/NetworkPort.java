package com.danick.e2.Networking;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class NetworkPort {
	DatagramSocket DS;
	InetAddress adress;
	int sendPort = 0;
	int port;
	byte buf[] = new byte[900];
	Thread senThread;
	Thread recThread;
	
	public static HashMap<Byte, Message> recMsgs = new HashMap<>();
	Queue<Message> senMsgs = new LinkedList<>(); 
	
	HashMap<Byte, messageReceiveEvent> MREs = new HashMap<>();
	
	public NetworkPort(String ip, int port) throws IOException {
		this(0);
		this.sendPort = port;
		this.adress = InetAddress.getByName(ip);
	}
	public NetworkPort(int port) throws IOException {
		if (port == 0) port = (int)(Math.random()*(65535-49152)) + 49152;
		this.port = port;
		DS = new DatagramSocket(port);
		this.senThread = new Thread() {
			public void run() {
				while(!DS.isClosed()) {
					Message msg;
					while ((msg = senMsgs.peek()) == null)
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					
					byte[] buffer = new byte[Message.MAX_BUFFER_SIZE+Message.HEADER_SIZE];
					buffer[0] = msg.id;
					buffer[1] = msg.part;
					buffer[2] = msg.type;
					buffer[3] = (byte) Math.ceil((double)msg.data.length / (double)(buffer.length-Message.HEADER_SIZE));
					//System.out.println(msg.data.length + " | " + (buffer.length-Message.HEADER_SIZE));
					//System.out.println(buffer[3]);
					int startPos = msg.index;
					for (; msg.index-startPos < buffer.length-Message.HEADER_SIZE && msg.index < msg.data.length; msg.index++) {
						buffer[msg.index+Message.HEADER_SIZE-startPos] = msg.data[msg.index];
					}
					
					
					DatagramPacket DpSend;
					if (msg.destAdress != null) DpSend = new DatagramPacket(buffer, buffer.length, msg.destAdress, msg.destPort); 
					else DpSend = new DatagramPacket(buffer, buffer.length, adress, sendPort);

					try {
						DS.send(DpSend);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if (msg.index >= msg.data.length) {
						//System.out.println("sending " + buffer[3] + " parts");
						//System.out.println("SEND to port "+DpSend.getPort()+": " + new String(msg.data));
						senMsgs.poll();
						msg = null;
					}
					else {
						msg.part++;
					}
				}
			}
		};
		
		
		this.recThread = new Thread() {
			public void run() {
				byte[] receive = new byte[65535];

				DatagramPacket DpReceive = null;
				while(!DS.isClosed()) {
					//System.out.println("test");
					// Step 2 : create a DatgramPacket to receive the data.
					DpReceive = new DatagramPacket(receive, receive.length);

					// Step 3 : revieve the data in byte buffer.
					try {
						DS.receive(DpReceive);
						Message m = data(receive);
						m.sendAdress = DpReceive.getAddress();
						m.sendPort = DpReceive.getPort();
						//System.out.println("part "+m.part+"/"+receive[3]);
						if (m.part == receive[3]) {
							//System.out.println("RESC from port "+m.sendPort+": " + m);
							if (MREs.containsKey(m.type)) MREs.get(m.type).onMessage(m);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

					// Clear the buffer after every message.
					receive = new byte[65535];
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
	
	public Message sendMessage(int type, byte[] data, InetAddress adress, int port) {
		Message m = new Message((byte)type, data);
		m.destAdress = adress;
		m.destPort = port;
		senMsgs.add(m);
		return m;
	}
	
	public static NetworkPort startClient(String ip, int senPort) {
		NetworkPort c = null;
		while (c == null) try {
			c = new NetworkPort(ip, senPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		c.start();
		System.out.println("Started client on port: " + c.port + "\n connected to: " + c.sendPort);
		return c;
	}
	
	public static NetworkPort startServer(int recPort) {
		NetworkPort c = null;
		while (c == null) try {
			c = new NetworkPort(recPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		c.start();
		System.out.println("Started server on port: " + c.port + "\n connected to: " + c.sendPort);
		return c;
	}
	
	public static Message data(byte[] a) {
		if (a == null)
			return null;
		//System.out.println(new String(a));
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

	public void addReceiveEvent(messageReceiveEvent mre, byte type) {
		MREs.put(type, mre);
	}
	
	public void close() {
		DS.close();
	}
}
