package nl.klimdanick.E2.Core.Rendering;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.awt.Color;
import java.awt.Font;

import org.joml.Matrix4f;

import nl.klimdanick.E2.Core.GameLoop;

public class Renderer {
	
	private Shader textureShader, shapeShader;
    private Quad quad;
    private Matrix4f projection;
    public int screenWidth, screenHeight;
    public boolean renderingScreen = true;

    public Renderer(int screenWidth, int screenHeight) {
    	this.screenHeight = screenHeight;
    	this.screenWidth = screenWidth;
    	
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        textureShader = new Shader("src/main/resources/shaders/texture_vertex.glsl",
                "src/main/resources/shaders/texture_fragment.glsl");
        
        shapeShader = new Shader("src/main/resources/shaders/shape_vertex.glsl",
        		"src/main/resources/shaders/shape_fragment.glsl");
        
        quad = new Quad();
        
        // Orthographic projection: 0,0 is bottom-left; width,height is top-right
        projection = new Matrix4f().ortho2D(0, this.screenWidth, 0, this.screenHeight);
    }
    
    public void setProjection(Matrix4f projection) {
        this.projection = projection;
    }
    
    public Matrix4f getProjection() {
        return projection;
    }

    public void clear(E2Color c) {
        glClearColor(c.r, c.g, c.b, c.a);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    
    public void drawTexture(Texture texture, float x, float y, float width, float height) {
    	
    	if (!GameLoop.renderer.renderingScreen) height = -height;
    	
    	textureShader.use();
        glActiveTexture(GL_TEXTURE0);
        texture.bind();
        quad.bind();

        textureShader.setUniformMat4("uProjection", projection);
        textureShader.setUniform2f("uPosition", x, y);
        textureShader.setUniform2f("uScale", width, height);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        quad.unbind();
        texture.unbind();
    }
    
    public void drawText(String s, Font font, float x, float y, E2Color c) {
    	TextTexture text = new TextTexture(s, font, new Color(c.r, c.g, c.b, c.a));
    	drawTexture(text, x, y, (float)text.getWidth(), (float)text.getHeight());
    }
    
    public void drawRect(float x, float y, float width, float height, E2Color c) {
    	shapeShader.use();
    	quad.bind();
    	
    	setShapeUniforms(x, y, width, c, 0);
        shapeShader.setUniform2f("uScale", width, height);
        
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        quad.unbind();
    }
    
    public void drawCircle(float cx, float cy, float radius, int segments, E2Color c) {
        shapeShader.use();
        setShapeUniforms(cx, cy, 1, c, 0);

        // Generate vertices
        float[] vertices = new float[(segments + 2) * 2];
        vertices[0] = 0;
        vertices[1] = 0;

        for (int i = 0; i <= segments; i++) {
            double angle = 2.0 * Math.PI * i / segments;
            vertices[(i + 1) * 2]     = (float) Math.cos(angle) * radius;
            vertices[(i + 1) * 2 + 1] = (float) Math.sin(angle) * radius;
        }

        // Upload vertices to GPU
        int[] vao_vbo = uploadVerts(vertices);
        
        // Draw as a triangle fan
        glDrawArrays(GL_TRIANGLE_FAN, 0, segments + 2);

        // Cleanup
        glDeleteBuffers(vao_vbo[1]);
        glDeleteVertexArrays(vao_vbo[0]);
    }
    
    public void drawShape(float cx, float cy, float[][] verts, E2Color c, float scale, float rotation) {
        shapeShader.use();
        setShapeUniforms(cx, cy, scale, c, rotation);
        
        // map verts
        float[] vertices = new float[(verts.length) * 2];

        for (int i = 0; i < verts.length; i++) {
            vertices[i * 2]     = verts[i][0];
            vertices[i * 2 + 1] = verts[i][1];
        }

        int[] vao_vbo = uploadVerts(vertices);

        // Draw as a triangle fan
        glDrawArrays(GL_TRIANGLE_FAN, 0, verts.length);

        // Cleanup
        glDeleteBuffers(vao_vbo[1]);
        glDeleteVertexArrays(vao_vbo[0]);
    }
    
    public void drawLine(float ax, float ay, float bx, float by, E2Color c) {
        shapeShader.use();
        shapeShader.setUniformMat4("uProjection", projection);
        shapeShader.setUniform2f("uPosition", 0, 0);
        shapeShader.setUniform2f("uScale", 1, 1);
        shapeShader.setUniform4f("uColor", c.r, c.g, c.b, c.a);
        shapeShader.setUniform1f("uRotation", 0);

        // map verts
        float[] vertices = new float[4];

        vertices[0] = ax;
        vertices[1] = ay;
        vertices[2] = bx;
        vertices[3] = by;

        int[] vao_vbo = uploadVerts(vertices);

        // Draw as a line
        glDrawArrays(GL_LINES, 0, 2);

        // Cleanup
        glDeleteBuffers(vao_vbo[1]);
        glDeleteVertexArrays(vao_vbo[0]);
    }
    
    // Upload vertices to GPU
    private int[] uploadVerts(float[] verts) {
        int vao = glGenVertexArrays();
        int vbo = glGenBuffers();
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, verts, GL_DYNAMIC_DRAW);

        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);
        return new int[] {vao, vbo};
    }
    
    private void setShapeUniforms(float cx, float cy, float scale, E2Color c, float rotation) {
    	shapeShader.setUniformMat4("uProjection", projection);
        shapeShader.setUniform2f("uPosition", cx, cy);
        shapeShader.setUniform2f("uScale", scale, scale);
        shapeShader.setUniform4f("uColor", c.r, c.g, c.b, c.a);
        shapeShader.setUniform1f("uRotation", rotation);
    }

    
    // TODO expand this class with draw() methods

    public void destroy() {
    	textureShader.destroy();
    	shapeShader.destroy();
        quad.destroy();
    }
}