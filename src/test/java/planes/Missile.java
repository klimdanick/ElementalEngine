package planes;

import nl.klimdanick.E2.Core.Rendering.E2Color;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Scenes.GameObject;
import nl.klimdanick.E2.Core.Scenes.Hitboxes.Hitbox;

public class Missile extends GameObject {

	public static final float[][] shape = {{-1f, -1f}, {-1f, 1f}, {1f, 1f}, {1f, -1f}};
	
	public Missile(float x, float y, float rotation) {
		super(x, y, 10, 10, rotation);
		this.hitbox = new Hitbox(shape, w/2, this);
	}
	
	@Override
	public void render(Renderer r) {
		r.clear(E2Color.STRAWBERRY_MAGENTA);
	}

}
