package org.array.wrapper.gltexture;

import org.array.wrapper.d2.Array2di;
import org.array.wrapper.texture.Texture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class GLTexture extends Texture implements IGLTexture {

    protected int texture;

    // Array constructors

    public GLTexture(int width, int height) {
        super(width, height);
    }

    public GLTexture(int[] array, int width, int height) {
        super(array, width, height);
    }

    public GLTexture(int size) {
        super(size);
    }

    public GLTexture(int initialValue, int width, int height) {
        super(initialValue, width, height);
    }

    // Image constructors

    public GLTexture(BufferedImage image) {
        super(image);
        texture = loadId();
    }

    public GLTexture(File file) throws IOException {
        super(file);
        texture = loadId();
    }

    // Copy constructors

    public GLTexture(Array2di array2di) {
        super(array2di);
        texture = loadId();
    }

    public GLTexture(Texture texture) {
        super(texture);
        this.texture = loadId();
    }

    public GLTexture(Texture texture, int id) {
        super(texture);
        this.texture = id;
    }

    public GLTexture(GLTexture copy) {
        super(copy);
        texture = copy.texture;
    }

    // OpenGL inherit methods

    @Override
    public int loadId() {
        return GLTextureUtils.loadId(width, height, array);
    }

    @Override
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, texture);
    }

    @Override
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    // Getters

    public int getTextureID() {
        if (texture == 0) {
            texture = loadId();
        }
        return texture;
    }

    @Override
    public String toString() {
        return super.toString().replaceAll("^Texture", "GLTexture") +
                ", id=" + texture;
    }

}
