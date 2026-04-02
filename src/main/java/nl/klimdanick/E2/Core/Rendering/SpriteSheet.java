package nl.klimdanick.E2.Core.Rendering;

public class SpriteSheet {

    private Texture texture;
    private int cols, rows;

    public SpriteSheet(Texture texture, int cols, int rows) {
        this.texture = texture;
        this.cols = cols;
        this.rows = rows;
    }

    public TextureRegion get(int x, int y) {
        float u0 = (float)x / cols;
        float v0 = (float)y / rows;
        float u1 = (float)(x + 1) / cols;
        float v1 = (float)(y + 1) / rows;

        return new TextureRegion(texture, u0, v0, u1, v1);
    }
}