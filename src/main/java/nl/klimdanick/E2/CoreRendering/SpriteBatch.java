package nl.klimdanick.E2.CoreRendering;

import org.lwjgl.opengl.*;

public class SpriteBatch {

    private static final int MAX_SPRITES = 1000;
    private static final int VERTEX_SIZE = 4; // x, y, u, v
    private static final int VERTICES_PER_SPRITE = 6;

    private float[] vertices = new float[MAX_SPRITES * VERTICES_PER_SPRITE * VERTEX_SIZE];
    private int index = 0;

    private int vao, vbo;
    private Shader shader;
    private Texture currentTexture;

    public SpriteBatch() {
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

        shader = createDefaultShader();
    }

    public void begin() {
        index = 0;
    }

    public void draw(Texture texture, float x, float y, float width, float height) {

        // Flush if texture changes
        if (currentTexture != texture) {
            flush();
            currentTexture = texture;
        }

        // Quad positions
        float x0 = x;
        float y0 = y;
        float x1 = x + width;
        float y1 = y + height;

        // UVs
        float u0 = 0f, v0 = 0f;
        float u1 = 1f, v1 = 1f;

        // 6 vertices (2 triangles)
        addVertex(x0, y0, u0, v0);
        addVertex(x0, y1, u0, v1);
        addVertex(x1, y1, u1, v1);

        addVertex(x0, y0, u0, v0);
        addVertex(x1, y1, u1, v1);
        addVertex(x1, y0, u1, v0);
    }

    private void addVertex(float x, float y, float u, float v) {
        vertices[index++] = x;
        vertices[index++] = y;
        vertices[index++] = u;
        vertices[index++] = v;
    }

    public void end() {
        flush();
    }

    private void flush() {
        if (index == 0) return;

        GL20.glUseProgram(shader.getProgram());
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

            uniform sampler2D tex;

            void main() {
                FragColor = texture(tex, vUV);
            }
        """;

        return new Shader(vs, fs);
    }
}