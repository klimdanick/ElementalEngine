package test;

public class Message {
	byte id;
	byte type;
	byte[] data;
	byte part = 1;
	int index = 0;
	
	public Message(byte type, byte[] data) {
		this.id = (byte) (Math.random()*Byte.MAX_VALUE);
		this.type = type;
		this.data = data;
	}
}
