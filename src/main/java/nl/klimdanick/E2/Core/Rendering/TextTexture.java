package nl.klimdanick.E2.Core.Rendering;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

public class TextTexture extends Texture {

    public TextTexture(String text, Font font, Color color) {
    	super();
        // Render text to BufferedImage
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        width = fm.stringWidth(text);
        height = fm.getHeight();
        g.dispose();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, 0, fm.getAscent());
        g.dispose();

        // Upload to OpenGL
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getRGB(x, y);
                buffer.put((byte)((argb >> 16) & 0xFF)); // R
                buffer.put((byte)((argb >> 8) & 0xFF));  // G
                buffer.put((byte)(argb & 0xFF));         // B
                buffer.put((byte)((argb >> 24) & 0xFF)); // A
            }
        }
        buffer.flip();

        id = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, id);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void destroy() {
        glDeleteTextures(id);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
