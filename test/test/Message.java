package test;

import java.net.InetAddress;

public class Message {
	
	public static byte HEADER_SIZE = 4;
	public static int MAX_BUFFER_SIZE = 20;
	public static byte FOOTER_SIZE = 4;
	
	byte id;
	byte type;
	byte[] data;
	byte part = 1;
	int index = 0;
	InetAddress destAdress = null;
	int destPort;
	InetAddress sendAdress = null;
	int sendPort;
	
	public Message(byte type, byte[] data) {
		this.id = (byte) (Math.random()*Byte.MAX_VALUE);
		this.type = type;
		this.data = data;
	}
	
	public Message(byte id) {
		this.id = id;
	}
	
	public String toString() {
		String s = "";/*"id: ";
		s+=id;
		s+="\ntype ";
		s+=type;
		s+="\npart ";
		s+=part;
		s+="\nsize ";
		s+=data.length;
		s+="\ndata ";*/
		for (int i = 0; i < data.length; i++) s+=(char)data[i];
		return s;
	}
}
