package org.array.wrapper.texture;

import org.array.wrapper.Array2di;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;

public class GLTexture extends Texture {

    protected int texture;

    public GLTexture(int width, int height) {
        super(width, height);
    }

    public GLTexture(int initialValue, int width, int height) {
        super(initialValue, width, height);
    }

    public GLTexture(BufferedImage image) {
        super(image);
        texture = loadTextureID();
    }

    public GLTexture(File file) throws IOException {
        super(file);
        texture = loadTextureID();
    }

    public GLTexture(Array2di array2di) {
        super(array2di);
        texture = loadTextureID();
    }

    public GLTexture(Texture texture) {
        super(texture);
        super.alpha = texture.alpha;
        this.texture = loadTextureID();
    }

    public int loadTextureID() {
        int[] data = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            int a = (array[i] & 0xff000000) >> 24;
            int r = (array[i] & 0xff0000) >> 16;
            int g = (array[i] & 0xff00) >> 8;
            int b = (array[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data).flip();

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
        glBindTexture(GL_TEXTURE_2D, 0);
        return result;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getTextureID() {
        return texture;
    }

}
