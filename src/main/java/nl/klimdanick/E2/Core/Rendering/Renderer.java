package nl.klimdanick.E2.Core.Rendering;

import static org.lwjgl.opengl.GL30.*;

import java.awt.Color;
import java.awt.Font;

import org.joml.Matrix4f;
import org.joml.Vector2f;

import nl.klimdanick.E2.Core.GameLoop;

public class Renderer {
	
	private Shader textureShader, shapeShader;
    private Matrix4f projection;
    public Camera activeCam, renderCam;
    public int screenWidth, screenHeight;
    public boolean renderingScreen = true;
    public DrawingMode drawMode = DrawingMode.FILL;

    public Renderer(int screenWidth, int screenHeight) {
    	this.screenHeight = screenHeight;
    	this.screenWidth = screenWidth;
    	activeCam = new Camera();
    	renderCam = new Camera();
    	
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        textureShader = new Shader("src/main/resources/shaders/texture_vertex.glsl",
                "src/main/resources/shaders/texture_fragment.glsl");
        
        shapeShader = new Shader("src/main/resources/shaders/shape_vertex.glsl",
        		"src/main/resources/shaders/shape_fragment.glsl");
        
        // Orthographic projection: 0,0 is bottom-left; width,height is top-right
        projection = new Matrix4f().ortho2D(0, this.screenWidth, 0, this.screenHeight);
        
        System.out.println(projection);
    }
    
    public void blendNormal() {
    	glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }
    
    public void blendAdditive() {
    	glBlendFunc(GL_SRC_ALPHA, GL_ONE);
    }
    
    public void blendMultiply() {
    	glBlendFunc(GL_DST_COLOR, GL_ZERO);
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
    
    public void drawTexture(Texture texture, float x, float y, float width, float height, float rotation) {
    	if (!GameLoop.renderer.renderingScreen) height = -height;
    	
    	textureShader.use();
        glActiveTexture(GL_TEXTURE0);
        texture.bind();
        texture.quad.bind();

        textureShader.setUniformMat4("uProjection", projection);
        textureShader.setUniform2f("uPosition", x, y);
        textureShader.setUniform2f("uScale", width, height);
        textureShader.setUniform4f("uTint", texture.tint.r, texture.tint.g, texture.tint.b, texture.tint.a);
        textureShader.setUniform1f("uRotation", rotation);
        if (renderingScreen) {
        	setCameraUniforms(new Camera());
        } else setCameraUniforms();

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);

        texture.quad.unbind();
        texture.unbind();
    }
    
    public void drawText(String s, Font font, float x, float y, E2Color c) {
    	TextTexture text = new TextTexture(s, font, new Color(c.r, c.g, c.b, c.a));
    	drawTexture(text, x, y, (float)text.getWidth(), (float)text.getHeight(), 0);
    }
    
    public void drawRect(float x, float y, float width, float height, E2Color c) {
    	Quad quad = new Quad();
    	shapeShader.use();
    	quad.bind();
    	
    	setShapeUniforms(x, y, width, c, 0);
        shapeShader.setUniform2f("uScale", width, height);
        
        if (drawMode == DrawingMode.OUTLINE) glDrawElements(GL_LINE_LOOP, 6, GL_UNSIGNED_INT, 0);
        else if (drawMode == DrawingMode.FILL) glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
        quad.unbind();
        quad.destroy();
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
        if (drawMode == DrawingMode.OUTLINE) glDrawArrays(GL_LINE_LOOP, 1, vertices.length/2-1);
        else if (drawMode == DrawingMode.FILL) glDrawArrays(GL_TRIANGLE_FAN, 0, vertices.length/2);

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
        if (drawMode == DrawingMode.OUTLINE) glDrawArrays(GL_LINE_LOOP, 0, verts.length);
        else if (drawMode == DrawingMode.FILL) glDrawArrays(GL_TRIANGLE_FAN, 0, verts.length);

        // Cleanup
        glDeleteBuffers(vao_vbo[1]);
        glDeleteVertexArrays(vao_vbo[0]);
    }
    
    public void drawLine(float ax, float ay, float bx, float by, E2Color c) {
        shapeShader.use();
        setShapeUniforms(0, 0, 1, c, 0);

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
        setCameraUniforms();
    }
    private void setCameraUniforms() {
    	setCameraUniforms(renderCam);
    }
    private void setCameraUniforms(Camera c) {
    	Vector2f translation = new Vector2f(-screenWidth/2, -screenHeight/2).add(c.translation);
    	if (c != activeCam) translation = new Vector2f(0, 0);
    	
    	shapeShader.setUniform1f("uCamRotation", c.rotation);
    	shapeShader.setUniformMat3("uCamProjection", c.projection);
    	shapeShader.setUniform2f("uCamScale", c.scale.x, c.scale.y);
    	shapeShader.setUniform2f("uCamTranslation", translation.x, translation.y);
    	
    	textureShader.setUniform1f("uCamRotation", c.rotation);
    	textureShader.setUniformMat3("uCamProjection", c.projection);
    	textureShader.setUniform2f("uCamScale", c.scale.x, c.scale.y);
    	textureShader.setUniform2f("uCamTranslation", translation.x, translation.y);
    }

    
    // TODO expand this class with draw() methods

    public void destroy() {
    	textureShader.destroy();
    	shapeShader.destroy();
    }
}