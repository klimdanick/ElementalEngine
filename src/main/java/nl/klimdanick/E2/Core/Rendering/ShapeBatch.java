package nl.klimdanick.E2.Core.Rendering;

import org.lwjgl.opengl.*;

import nl.klimdanick.E2.Utils.E2Color;

public class ShapeBatch extends Batch{

    private static final int MAX_VERTICES = 10000;
    private static final int VERTEX_SIZE = 6; // x, y, r, g, b, a

    private float[] vertices = new float[MAX_VERTICES * VERTEX_SIZE];
    private int index = 0;

    private int vao, vbo;
    private Shader shader;
    private Camera2D camera;

    public ShapeBatch(Camera2D camera) {
        this.camera = camera;
        shader = createShader();
        init();
    }
    
    public ShapeBatch(Camera2D camera, Shader shader) {
        this.camera = camera;
        this.shader = shader;
        init();
    }
    
    protected void init() {
    	vao = GL30.glGenVertexArrays();
        vbo = GL15.glGenBuffers();

        GL30.glBindVertexArray(vao);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        GL15.glBufferData(GL15.GL_ARRAY_BUFFER,
                vertices.length * Float.BYTES,
                GL15.GL_DYNAMIC_DRAW);

        // position
        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 0);
        GL20.glEnableVertexAttribArray(0);

        // color
        GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, VERTEX_SIZE * Float.BYTES, 2 * Float.BYTES);
        GL20.glEnableVertexAttribArray(1);
    }

    public void begin() {
        index = 0;
    }

    public void end() {
        flush();
    }

    private void addVertex(float x, float y, E2Color c) {
        vertices[index++] = x;
        vertices[index++] = y;
        vertices[index++] = c.r;
        vertices[index++] = c.g;
        vertices[index++] = c.b;
        vertices[index++] = c.a;
    }

    protected void flush() {
        if (index == 0) return;

        shader.bind();
        shader.setMatrix4("uMatrix", camera.getMatrix());

        GL30.glBindVertexArray(vao);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, 0, vertices);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, index / VERTEX_SIZE);

        index = 0;
    }

    private Shader createShader() {
        String vs = """
            #version 330 core
            layout (location = 0) in vec2 aPos;
            layout (location = 1) in vec4 aColor;

            uniform mat4 uMatrix;

            out vec4 vColor;

            void main() {
                vColor = aColor;
                gl_Position = uMatrix * vec4(aPos, 0.0, 1.0);
            }
        """;

        String fs = """
            #version 330 core
            in vec4 vColor;
            out vec4 FragColor;

            void main() {
                FragColor = vColor;
            }
        """;

        return new Shader(vs, fs);
    }
    
    public void rect(float x, float y, float w, float h, E2Color c) {

        float x0 = x;
        float y0 = y;
        float x1 = x + w;
        float y1 = y + h;

        addVertex(x0, y0, c);
        addVertex(x0, y1, c);
        addVertex(x1, y1, c);

        addVertex(x0, y0, c);
        addVertex(x1, y1, c);
        addVertex(x1, y0, c);
    }
    
    public void line(float x0, float y0, float x1, float y1, E2Color c) {

        float thickness = 1f;

        float dx = x1 - x0;
        float dy = y1 - y0;

        float length = (float)Math.sqrt(dx * dx + dy * dy);

        float nx = -dy / length * thickness;
        float ny = dx / length * thickness;

        addVertex(x0 - nx, y0 - ny, c);
        addVertex(x0 + nx, y0 + ny, c);
        addVertex(x1 + nx, y1 + ny, c);

        addVertex(x0 - nx, y0 - ny, c);
        addVertex(x1 + nx, y1 + ny, c);
        addVertex(x1 - nx, y1 - ny, c);
    }
    
    public void circle(float cx, float cy, float radius, E2Color c) {

        int segments = 24;

        for (int i = 0; i < segments; i++) {

            float a0 = (float)(i * Math.PI * 2 / segments);
            float a1 = (float)((i + 1) * Math.PI * 2 / segments);

            float x0 = cx;
            float y0 = cy;

            float x1 = cx + (float)Math.cos(a0) * radius;
            float y1 = cy + (float)Math.sin(a0) * radius;

            float x2 = cx + (float)Math.cos(a1) * radius;
            float y2 = cy + (float)Math.sin(a1) * radius;

            addVertex(x0, y0, c);
            addVertex(x1, y1, c);
            addVertex(x2, y2, c);
        }
    }
}