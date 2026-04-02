package nl.klimdanick.E2.Core.Rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;

public class Texture {

    private int id;

    public Texture(String path) {
        int[] w = new int[1];
        int[] h = new int[1];
        int[] c = new int[1];

        STBImage.stbi_set_flip_vertically_on_load(true);
        ByteBuffer data = STBImage.stbi_load(path, w, h, c, 4);

        if (data == null) {
            throw new RuntimeException("Failed to load texture: " + path +
                "\nReason: " + STBImage.stbi_failure_reason());
        }

        id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA,
                w[0], h[0], 0,
                GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, data);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        if (data != null) {
            STBImage.stbi_image_free(data);
        }
    }

    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }
}