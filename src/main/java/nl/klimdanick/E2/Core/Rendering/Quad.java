package nl.klimdanick.E2.Core.Rendering;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

// reusable quad mesh - one instance is enough for all textured draws.
public class Quad {
	
	// VBO = the actual vertex data in GPU memory
	public float[] VERTICES = {
	        // x, y,   u, v
	        -0.5f,  0.5f,  0f, 0f,   // top-left
	         0.5f,  0.5f,  1f, 0f,   // top-right
	         0.5f, -0.5f,  1f, 1f,   // bottom-right
	        -0.5f, -0.5f,  0f, 1f    // bottom-left
	};
	private int vertexBufferObject;
	
	// VAO = the configuration describing how to use the VBO (and EBO)
	// you can just “bind” this VAO later to reuse the entire setup instantly.
	private int vertexArrayObject;
	
	// EBO = index list telling OpenGL which vertices form triangles
	private static final int[] INDICES = {0, 1, 2, 2, 3, 0};
    private int elementBufferObject;
    
    
    public Quad() {
    	vertexArrayObject = glGenVertexArrays();
        glBindVertexArray(vertexArrayObject);

        vertexBufferObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(VERTICES.length);
        
        // put() writes data -> the internal cursor ends at the end.
        // flip() sets the limit to the current position and resets position to 0 -> so OpenGL reads from the start of the buffer correctly.
        buffer.put(VERTICES).flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        elementBufferObject = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBufferObject);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, INDICES, GL_STATIC_DRAW);

        int stride = 4 * Float.BYTES;
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, stride, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }
    
    public Quad(float[] verts) {
    	this.VERTICES = verts;
    	vertexArrayObject = glGenVertexArrays();
        glBindVertexArray(vertexArrayObject);

        vertexBufferObject = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBufferObject);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(VERTICES.length);
        
        // put() writes data -> the internal cursor ends at the end.
        // flip() sets the limit to the current position and resets position to 0 -> so OpenGL reads from the start of the buffer correctly.
        buffer.put(VERTICES).flip();
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        elementBufferObject = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, elementBufferObject);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, INDICES, GL_STATIC_DRAW);

        int stride = 4 * Float.BYTES;
        glVertexAttribPointer(0, 2, GL_FLOAT, false, stride, 0);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, stride, 2 * Float.BYTES);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void bind() {
        glBindVertexArray(vertexArrayObject);
    }

    public void unbind() {
        glBindVertexArray(0);
    }

    public void destroy() {
        glDeleteVertexArrays(vertexArrayObject);
        glDeleteBuffers(vertexBufferObject);
        glDeleteBuffers(elementBufferObject);
    }
}
