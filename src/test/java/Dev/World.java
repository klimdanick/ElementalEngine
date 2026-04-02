package Dev;

import nl.klimdanick.E2.Core.Input.Input;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Rendering.SpriteSheet;
import nl.klimdanick.E2.Core.Rendering.Texture;
import nl.klimdanick.E2.Utils.E2Color;

public class World {
	int[][] blocks;
	int w, h;
	private SpriteSheet tileMap;
	public int[] selected = new int[] {0, 0};
	private int clipboard = 0;
	
	public World(int w, int h) {
		blocks = new int[w][h];
		this.w = w;
		this.h = h;
		Texture tex = new Texture("src/test/resources/spritesheet.png");
        tileMap = new SpriteSheet(tex, 11, 11);
	}
	
	public void render(Renderer r) {
		
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++) {
				int[] loc =  project(x, y, 32, 16);
				int[] uv = getUV(blocks[x][y]);
				
				if (selected[0] == x && selected[1] == y) {
					if (Input.pressed("next")) blocks[x][y]++;
					if (Input.pressed("prev")) blocks[x][y]--;
					if (Input.pressed("copy")) clipboard = blocks[x][y];
					if (Input.pressed("paste")) blocks[x][y] = clipboard;
					r.sprite.draw(tileMap.get(uv[0], uv[1]), loc[0], loc[1], 32, 32, E2Color.DEBIAN_RED);
				} else {
					r.sprite.draw(tileMap.get(uv[0], uv[1]), loc[0], loc[1], 32, 32);
				}
			}
	}
	
	private int[] project(int x, int y, int tileWidth, int tileHeight) {
		int screenX = (int) ((x - y) * (tileWidth / 2f));
		int screenY = (int) ((x + y) * (tileHeight / 2f));
		return new int[] {screenX, screenY};
	}
	
	private int[] getUV(int id) {
		return new int[] {id%11, Math.floorDiv(id, 11)};
	}
}
