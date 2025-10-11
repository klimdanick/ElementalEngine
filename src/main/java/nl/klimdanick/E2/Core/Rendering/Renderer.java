package nl.klimdanick.E2.Core.Rendering;

import static org.lwjgl.opengl.GL20.*;


import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import nl.klimdanick.E2.Core.GameLoop;

public class Renderer {
	
	private Shader shader;
    private Quad quad;
    private Matrix4f projection;
    public int screenWidth, screenHeight;
    public boolean renderingScreen = true;

    public Renderer(int screenWidth, int screenHeight) {
    	this.screenHeight = screenHeight;
    	this.screenWidth = screenWidth;
    	
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        shader = new Shader("src/main/resources/shaders/vertex.glsl",
                "src/main/resources/shaders/fragment.glsl");
        
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

    public void clear(float r, float g, float b) {
        glClearColor(r, g, b, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
    
    public void drawTexture(Texture texture, float x, float y, float width, float height) {
    	
    	if (!GameLoop.renderer.renderingScreen) height = -height;
    	
        shader.use();
        glActiveTexture(GL_TEXTURE0);
        texture.bind();
        quad.bind();

        shader.setUniformMat4("uProjection", projection);
        shader.setUniform2f("uPosition", x, y);
        shader.setUniform2f("uScale", width, height);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        quad.unbind();
        texture.unbind();
    }
    
    // TODO expand this class with draw() methods

    public void destroy() {
        shader.destroy();
        quad.destroy();
    }
}