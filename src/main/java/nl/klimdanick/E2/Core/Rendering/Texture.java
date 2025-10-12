package nl.klimdanick.E2.Core.Rendering;

import static org.lwjgl.opengl.GL30.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import nl.klimdanick.E2.Core.GameLoop;

public class Texture {
	 protected int id;
	 protected int width;
	 protected int height;
	 protected int fbo;
	 private Matrix4f previousProjection;
	 private int previousBuffer;
	 private IntBuffer previousViewport;
	 public Quad quad;
	 public E2Color tint = new E2Color(1.0f, 1.0f, 1.0f, 1.0f);
	 
	 // TODO
	 //	Use integer scaling (e.g., scale by 2x, 3x, not 1.37x)
	 //	Snap sprite positions to whole pixels before drawing
	 
	 public Texture(String path) {
		 this.quad = new Quad();
		 // Load file from classpath
		 ByteBuffer imageBuffer;
		 try (InputStream in = Texture.class.getResourceAsStream(path)) {
			 if (in == null)
				 throw new RuntimeException("Resource not found: " + path);
			 byte[] bytes = in.readAllBytes();
			 imageBuffer = BufferUtils.createByteBuffer(bytes.length);
			 imageBuffer.put(bytes).flip();
		 } catch (IOException e) {
			 throw new RuntimeException("Failed to load resource: " + path, e);
		 }
		 
		 IntBuffer w = BufferUtils.createIntBuffer(1);
		 IntBuffer h = BufferUtils.createIntBuffer(1);
		 IntBuffer comp = BufferUtils.createIntBuffer(1);
	         
		 ByteBuffer image = STBImage.stbi_load_from_memory(imageBuffer, w, h, comp, 4);
		 if (image == null)
			 throw new RuntimeException("Failed to load image: " + STBImage.stbi_failure_reason());
		 this.width = w.get();
		 this.height = h.get();
	         
	     // get texture ID from openGL
		 id = glGenTextures();
	         
		 // make this the current texture we’re modifying
		 // now all following glTex* calls affect this texture
		 glBindTexture(GL_TEXTURE_2D, id);
	         
		 // Upload image data to the GPU
		 // 2D texture;
		 // Mipmap level 0;
		 // RGBA (4 channels like the loaded image before);
		 // width/height;
		 // border must be 0;
		 // RGBA format in CPU mem;
		 // each color component is a byte;
		 // the pixel buffer;
		 glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
	         
		 // Set texture sampling
		 // Nearest for sharp pixel edges; GL_LINEAR for smooth edges;
		 glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		 glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	         
		 // free CPU mem
		 // stbi_image_free(image);
	         
		 // image lives entirely in GPU VRAM, ready to be drawn with a shader.
		 
		 // renderTagert allows for drawing with GPU on texture
		 createRenderTarget();
	 }
	 
	 public Texture(int width, int height) {
		 this.quad = new Quad();
		 this.width = width;
		 this.height = height;
		 
		 id = glGenTextures();
		 glBindTexture(GL_TEXTURE_2D, id);
		 
		 // Allocate empty RGBA texture on GPU
		 glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0,
				 GL_RGBA, GL_UNSIGNED_BYTE, (ByteBuffer) null);
		 
		 glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		 glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		 
		 // image lives entirely in GPU VRAM, ready to be drawn with a shader.
		 
		 // renderTagert allows for drawing with GPU on texture
		 createRenderTarget();
	 }
	 
	 public Texture() {
		 quad = new Quad();
	 }
	 
	 public void createRenderTarget() {
		 fbo = glGenFramebuffers();
		 glBindFramebuffer(GL_FRAMEBUFFER, fbo);
	     glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.id, 0);
	     glDrawBuffer(GL_COLOR_ATTACHMENT0);
	     glReadBuffer(GL_COLOR_ATTACHMENT0);
	     int status = glCheckFramebufferStatus(GL_FRAMEBUFFER);
	     if (status != GL_FRAMEBUFFER_COMPLETE) {
	         throw new RuntimeException("Framebuffer not complete: " + status);
	     }
	     glBindFramebuffer(GL_FRAMEBUFFER, 0);
	 }
	 
	 public void begin() {
		 previousViewport = BufferUtils.createIntBuffer(4);
		 glGetIntegerv(GL_VIEWPORT, previousViewport);
		 previousBuffer = glGetInteger(GL_FRAMEBUFFER_BINDING);
		 glBindFramebuffer(GL_FRAMEBUFFER, fbo);
		 glViewport(0, 0, width, height);
		 previousProjection = GameLoop.renderer.getProjection();
		 GameLoop.renderer.setProjection(new Matrix4f().ortho2D(0, width, 0, height));
		 GameLoop.renderer.renderingScreen = false;
	 }

	 public void end() {
		 if (previousBuffer == 0) GameLoop.renderer.renderingScreen = true;
		 glBindFramebuffer(GL_FRAMEBUFFER, previousBuffer);
		 glViewport(previousViewport.get(0), previousViewport.get(1), previousViewport.get(2), previousViewport.get(3));
		 GameLoop.renderer.setProjection(previousProjection);
	 }
		 
	 public void bind() {
		 glBindTexture(GL_TEXTURE_2D, id);
	 }

	 public void unbind() {
		 glBindTexture(GL_TEXTURE_2D, 0);
	 }

	 public void destroy() {
		 glDeleteTextures(id);
		 quad.destroy();
	 }

	 public int getWidth() { return width; }
	 public int getHeight() { return height; }
}
