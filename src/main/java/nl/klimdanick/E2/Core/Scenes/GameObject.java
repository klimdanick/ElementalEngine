package nl.klimdanick.E2.Core.Scenes;

import nl.klimdanick.E2.Core.Rendering.DrawingMode;
import nl.klimdanick.E2.Core.Rendering.Renderer;
import nl.klimdanick.E2.Core.Rendering.Texture;
import nl.klimdanick.E2.Core.Scenes.Hitboxes.Hitbox;

public abstract class GameObject {
	
	public float x, y, w, h;
	public float rotation;
	Texture sprite;
	public Hitbox hitbox;
	public Scene scene;
	
	public GameObject(float x, float y, float rotation, Texture sprite) {
		this.x = x;
		this.y = y;
		this.w = sprite.getWidth();
		this.h = sprite.getHeight();
		this.rotation = rotation;
		this.sprite = sprite;
		update_(0);
	}
	
	public GameObject(float x, float y, float w, float h, float rotation) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.sprite = new Texture((int)w, (int)h);
		this.rotation = rotation;
	}
	
	public void update_(double dt) {
		update(dt);
		hitbox.update();
	}
	
	public void render_(Renderer r) {
		r.drawMode = DrawingMode.FILL;
		sprite.begin();
		render(r);
		sprite.end();
		r.drawTexture(sprite, x, y, sprite.getWidth(), sprite.getHeight(), rotation);
	}
	
	public void update(double dt) {}
	public void render(Renderer r) {}
}
