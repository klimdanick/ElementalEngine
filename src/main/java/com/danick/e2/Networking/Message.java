package com.danick.e2.Networking;


import java.net.InetAddress;

public class Message {
	
	public static byte HEADER_SIZE = 4;
	public static int MAX_BUFFER_SIZE = 20;
	public static byte FOOTER_SIZE = 4;
	
	public byte id;
	public byte type;
	public byte[] data;
	public byte part = 1;
	public int index = 0;
	public InetAddress destAdress = null;
	public int destPort;
	public InetAddress sendAdress = null;
	public int sendPort;
	
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
