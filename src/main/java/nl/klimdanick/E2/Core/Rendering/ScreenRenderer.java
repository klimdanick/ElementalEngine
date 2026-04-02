package nl.klimdanick.E2.Core.Rendering;

import org.lwjgl.opengl.*;

public class ScreenRenderer {

    private int vao, vbo;
    private Shader shader;

    public ScreenRenderer() {

        float[] quad = {
            // x, y,   u, v
            -1f, -1f,  0f, 0f,
            -1f,  1f,  0f, 1f,
             1f,  1f,  1f, 1f,

            -1f, -1f,  0f, 0f,
             1f,  1f,  1f, 1f,
             1f, -1f,  1f, 0f
        };

        vao = GL30.glGenVertexArrays();
        vbo = GL15.glGenBuffers();

        GL30.glBindVertexArray(vao);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, quad, GL15.GL_STATIC_DRAW);

        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);

        shader = createShader();
    }

    private Shader createShader() {
        String vs = """
            #version 330 core
            layout (location = 0) in vec2 aPos;
            layout (location = 1) in vec2 aUV;

            out vec2 vUV;

            void main() {
                vUV = aUV;
                gl_Position = vec4(aPos, 0.0, 1.0);
            }
        """;

        String fs = """
            #version 330 core
            in vec2 vUV;
            out vec4 FragColor;

            uniform sampler2D screenTex;

            void main() {
                FragColor = texture(screenTex, vUV);
            }
        """;

        return new Shader(vs, fs);
    }

    public void render(int texture) {
        shader.bind();

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        GL30.glBindVertexArray(vao);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 6);
    }
}