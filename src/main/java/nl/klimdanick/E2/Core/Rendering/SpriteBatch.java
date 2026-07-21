package nl.klimdanick.E2.Core.Rendering;

import org.lwjgl.opengl.*;

import nl.klimdanick.E2.Utils.E2Color;

public class SpriteBatch extends Batch {

	private static final int MAX_SPRITES = 10000;
	private static final int VERTEX_SIZE = 8; // 2 pos + 2 uv + 4 color
	private static final int VERTICES_PER_SPRITE = 6;

	private float[] vertices = new float[MAX_SPRITES * VERTICES_PER_SPRITE * VERTEX_SIZE];
	private int index = 0;

	private int vao, vbo;
	private Shader shader;
	private Texture currentTexture;

	private Camera2D camera;

	public SpriteBatch(Camera2D camera) {
		this.camera = camera;
		this.shader = createDefaultShader();
		init();
	}

	public SpriteBatch(Camera2D camera, Shader shader) {
		this.camera = camera;
		this.shader = shader;
		init();
	}

	protected void init() {
		vao = GL30.glGenVertexArrays();
		vbo = GL15.glGenBuffers();

		GL30.glBindVertexArray(vao);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL15.GL_DYNAMIC_DRAW);

		// position
		GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 0);
		GL20.glEnableVertexAttribArray(0);

		// UV
		GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 2 * Float.BYTES);
		GL20.glEnableVertexAttribArray(1);

		// COLOR
		GL20.glVertexAttribPointer(2, 4, GL11.GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 4 * Float.BYTES);
		GL20.glEnableVertexAttribArray(2);
	}

	public void begin() {
		index = 0;
	}

	public void draw(Texture tex, float x, float y, float w, float h) {
		draw(tex, x, y, w, h, 0, 0, 0, 1, 1, E2Color.WHITE);
	}
	
	public void draw(Texture tex, float x, float y, float w, float h, float rotation) {
	    draw(tex, x, y, w, h, w/2f, h/2f, rotation, 1, 1, E2Color.WHITE);
	}
	
	public void draw(Texture texture, float x, float y, float w, float h, float originX, float originY, float rotation,
			float scaleX, float scaleY, E2Color color) {
		float u0 = 0f, v0 = 0f;
		float u1 = 1f, v1 = 1f;
		
		draw(texture, x, y, w, h, originX, originY, rotation, scaleX, scaleY, color, u0, v0, u1, v1);
	}

	public void draw(Texture texture, float x, float y, float w, float h, float originX, float originY, float rotation,
			float scaleX, float scaleY, E2Color color, float u0, float v0, float u1, float v1) {

		if (currentTexture != texture) {
			flush();
			currentTexture = texture;
		}

		// Convert to radians
		float rad = (float) Math.toRadians(rotation);
		float cos = (float) Math.cos(rad);
		float sin = (float) Math.sin(rad);

		// Apply scale
		float sx = w * scaleX;
		float sy = h * scaleY;

		// Local corners relative to origin
		float x0 = -originX;
		float y0 = -originY;

		float x1 = sx - originX;
		float y1 = sy - originY;

		// Define 4 corners
		float[] xs = { x0, x0, x1, x1 };
		float[] ys = { y1, y0, y0, y1 };

		float[] worldX = new float[4];
		float[] worldY = new float[4];

		for (int i = 0; i < 4; i++) {
			float lx = xs[i];
			float ly = ys[i];

			// Rotate
			float rx = lx * cos - ly * sin;
			float ry = lx * sin + ly * cos;

			// Translate to world
			worldX[i] = rx + x;
			worldY[i] = ry + y;
			
			worldX[i] = (float)Math.floor(worldX[i]);
			worldY[i] = (float)Math.floor(worldY[i]);
		}

		// Two triangles
		addVertex(worldX[0], worldY[0], u0, v0, color);
		addVertex(worldX[1], worldY[1], u0, v1, color);
		addVertex(worldX[2], worldY[2], u1, v1, color);

		addVertex(worldX[0], worldY[0], u0, v0, color);
		addVertex(worldX[2], worldY[2], u1, v1, color);
		addVertex(worldX[3], worldY[3], u1, v0, color);
		
		if (vertices.length - index < VERTEX_SIZE * VERTICES_PER_SPRITE) flush();
	}

	public void draw(TextureRegion region, float x, float y, float w, float h) {
		draw(region, x, y, w, h, 0, 0, 0, 1, 1, E2Color.WHITE);
	}
	
	public void draw(TextureRegion region, float x, float y, float w, float h, E2Color c) {
		draw(region, x, y, w, h, 0, 0, 0, 1, 1, c);
	}
	
	public void draw(TextureRegion region, float x, float y, float w, float h, float rotation) {
	    draw(region, x, y, w, h, w/2f, h/2f, rotation, 1, 1, E2Color.WHITE);
	}

	public void draw(TextureRegion region, float x, float y, float w, float h, float originX, float originY, float rotation,
			float scaleX, float scaleY, E2Color color) {
		
		float u0 = region.u0;
		float v0 = region.v0;
		float u1 = region.u1;
		float v1 = region.v1;
		
		Texture texture = region.texture;
		
		draw(texture, x, y, w, h, originX, originY, rotation, scaleX, scaleY, color, u0, v0, u1, v1);
	}

	private void addVertex(float x, float y, float u, float v, E2Color c) {
		vertices[index++] = x;
		vertices[index++] = y;
//		vertices[index++] = this.priority;
		vertices[index++] = u;
		vertices[index++] = v;

		vertices[index++] = c.r;
		vertices[index++] = c.g;
		vertices[index++] = c.b;
		vertices[index++] = c.a;
	}

	public void end() {
		flush();
	}

	protected void flush() {
//		System.out.println(priority);
		if (index == 0)
			return;

		shader.bind();
		shader.setMatrix4("uMatrix", camera.getMatrix());
		GL30.glBindVertexArray(vao);

		if (currentTexture != null) {
			currentTexture.bind();
		}

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, vertices);

		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, index / VERTEX_SIZE);

		index = 0;
		
	}

	private Shader createDefaultShader() {
		String vs = """
				       #version 330 core
				layout (location = 0) in vec3 aPos;
				layout (location = 1) in vec2 aUV;
				layout (location = 2) in vec4 aColor;

				uniform mat4 uMatrix;

				out vec2 vUV;
				out vec4 vColor;

				void main() {
				    vUV = aUV;
				    vColor = aColor;
				    gl_Position = uMatrix * vec4(aPos, 1.0);
				}
				     """;

		String fs = """
				         #version 330 core
				in vec2 vUV;
				in vec4 vColor;

				out vec4 FragColor;

				uniform sampler2D tex;

				void main() {
				    FragColor = texture(tex, vUV) * vColor;
				}
				     """;

		return new Shader(vs, fs);
	}
}