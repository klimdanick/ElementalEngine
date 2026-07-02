package nl.klimdanick.E2.Core.Rendering;

public class TextureRegion {

    public Texture texture;
    public float u0, v0, u1, v1;

    public TextureRegion(Texture texture, float u0, float v0, float u1, float v1) {
        this.texture = texture;
        this.u0 = u0;
        this.v0 = v0;
        this.u1 = u1;
        this.v1 = v1;
    }
}