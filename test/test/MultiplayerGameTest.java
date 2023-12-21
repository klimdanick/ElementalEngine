package test;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.nio.ByteBuffer;

import com.danick.e2.main.AbstractGame;
import com.danick.e2.main.GameContainer;
import com.danick.e2.renderer.Graphic;

public class MultiplayerGameTest extends AbstractGame {
	
	Client client;
	int x, y;
	byte id = (byte) (Math.random()*Byte.MAX_VALUE);
	@Override
	public void init(GameContainer gc, Graphic r) {
		client = Client.startClient("localhost", 1234);
		client.addReceiveEvent(new messageReceiveEvent() {
			public void onMessage(Message msg) {
				
				int x = (msg.data[1] << 8)+msg.data[2];
				int y = (msg.data[3] << 8)+msg.data[4];
				r.drawCircle(x, y, 1, 30, Color.green, true, 10);
			}
		}, (byte) 1);
		client.sendMessage(0, new byte[] {id});
	}

	@Override
	public void update(GameContainer gc, long dt) {
		if (gc.input.isKey(KeyEvent.VK_W)) y--;
		if (gc.input.isKey(KeyEvent.VK_S)) y++;
		if (gc.input.isKey(KeyEvent.VK_A)) x--;
		if (gc.input.isKey(KeyEvent.VK_D)) x++;
		byte[] X = ByteBuffer.allocate(4).putInt(x).array();
		byte[] Y = ByteBuffer.allocate(4).putInt(y).array();
		client.sendMessage(1, new byte[] {id, (byte)(x>>>8), (byte)x, (byte)(y>>>8), (byte)y});
	}

	@Override
	public void render(GameContainer gc, Graphic r) {
		r.clear();
		r.drawCircle(x, y, 0, 30, Color.red, true, 10);
	}

	public static void main(String[] args) {
		GameContainer gc = new GameContainer(new MultiplayerGameTest(), 600, 600, 1, "multiplayer");
		gc.start();
	}

}
