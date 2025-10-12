package nl.klimdanick.E2.Core.Rendering;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.nio.file.*;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;

public class Shader {
    private int programId;

    public Shader(String vertexPath, String fragmentPath) {
        try {
            String vertexCode = Files.readString(Path.of(vertexPath));
            String fragmentCode = Files.readString(Path.of(fragmentPath));

            int vertex = glCreateShader(GL_VERTEX_SHADER);
            glShaderSource(vertex, vertexCode);
            glCompileShader(vertex);
            checkCompileErrors(vertex, "VERTEX");

            int fragment = glCreateShader(GL_FRAGMENT_SHADER);
            glShaderSource(fragment, fragmentCode);
            glCompileShader(fragment);
            checkCompileErrors(fragment, "FRAGMENT");

            programId = glCreateProgram();
            glAttachShader(programId, vertex);
            glAttachShader(programId, fragment);
            glLinkProgram(programId);
            checkLinkErrors(programId);

            glDeleteShader(vertex);
            glDeleteShader(fragment);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load shader files", e);
        }
    }

    private void checkCompileErrors(int shader, String type) {
        if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE)
            throw new RuntimeException(type + " compile error:\n" + glGetShaderInfoLog(shader));
    }

    private void checkLinkErrors(int program) {
        if (glGetProgrami(program, GL_LINK_STATUS) == GL_FALSE)
            throw new RuntimeException("Program link error:\n" + glGetProgramInfoLog(program));
    }

    public void use() {
        glUseProgram(programId);
    }

    public void destroy() {
        glDeleteProgram(programId);
    }
    
    public int getProgramId() {
    	return this.programId;
    }
	
	public void setUniform1i(String name, int value) {
        int loc = glGetUniformLocation(programId, name);
        if (loc != -1) glUniform1i(loc, value);
    }

    public void setUniform1f(String name, float value) {
        int loc = glGetUniformLocation(programId, name);
        if (loc != -1) glUniform1f(loc, value);
    }

    public void setUniform2f(String name, float x, float y) {
        int loc = glGetUniformLocation(programId, name);
        if (loc != -1) glUniform2f(loc, x, y);
    }

    public void setUniform4f(String name, float x, float y, float z, float w) {
        int loc = glGetUniformLocation(programId, name);
        if (loc != -1) glUniform4f(loc, x, y, z, w);
    }

    public void setUniformMat4(String name, Matrix4f matrix) {
        int loc = glGetUniformLocation(programId, name);
        if (loc == -1) return;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            matrix.get(fb);
            glUniformMatrix4fv(loc, false, fb);
        }
    }

}
