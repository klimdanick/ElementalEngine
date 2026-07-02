package nl.klimdanick.E2.Core.Rendering;

import org.joml.Matrix4f;
import org.joml.Vector2f;

public class Camera2D {

    public Vector2f position = new Vector2f(0, 0);
    public float zoom = 1.0f;

    private Matrix4f projection;

    public Camera2D(int width, int height) {
        projection = new Matrix4f().ortho(
                0, width,     // left, right
                height, 0,    // bottom, top (FLIPPED → top-left origin)
                -1f, 1f
        );
    }

    public Matrix4f getMatrix() {
        float snappedX = (float) Math.floor(position.x);
        float snappedY = (float) Math.floor(position.y);

        Matrix4f view = new Matrix4f()
                .translate(-snappedX, -snappedY, 0)
                .scale(zoom);

        return new Matrix4f(projection).mul(view);
    }
}