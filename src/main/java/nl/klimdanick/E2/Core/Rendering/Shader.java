package nl.klimdanick.E2.Core.Rendering;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

public class Shader {

    private int program;

    public Shader(String vertexSrc, String fragmentSrc) {

        int vs = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vs, vertexSrc);
        GL20.glCompileShader(vs);

        int fs = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fs, fragmentSrc);
        GL20.glCompileShader(fs);

        program = GL20.glCreateProgram();
        GL20.glAttachShader(program, vs);
        GL20.glAttachShader(program, fs);
        GL20.glLinkProgram(program);

        GL20.glDeleteShader(vs);
        GL20.glDeleteShader(fs);
    }

    public void bind() {
        GL20.glUseProgram(program);
    }
    
    public int getProgram() {
    	return program;
    }
    
    public void setMatrix4(String name, Matrix4f matrix) {
        int location = GL20.glGetUniformLocation(program, name);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            matrix.get(fb);
            GL20.glUniformMatrix4fv(location, false, fb);
        }
    }
}