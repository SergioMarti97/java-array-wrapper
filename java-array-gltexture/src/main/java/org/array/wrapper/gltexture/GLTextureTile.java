package org.array.wrapper.gltexture;

import org.array.wrapper.d2.Array2di;
import org.array.wrapper.d2.Array2do;
import org.array.wrapper.texture.Texture;
import org.array.wrapper.texture.TextureTile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class GLTextureTile extends TextureTile implements IGLTexture {

    protected int texture;

    protected Array2di ids;

    // Array constructors

    public GLTextureTile(int[] array, int width, int height, int tileW, int tileH) {
        super(array, width, height, tileW, tileH);
    }

    public GLTextureTile(int initialValue, int width, int height, int tileW, int tileH) {
        super(initialValue, width, height, tileW, tileH);
    }

    public GLTextureTile(int width, int height, int tileW, int tileH) {
        super(width, height, tileW, tileH);
    }

    public GLTextureTile(int size, int tileW, int tileH) {
        super(size, tileW, tileH);
    }

    // Image constructors

    public GLTextureTile(BufferedImage image, int tileW, int tileH) {
        super(image, tileW, tileH);
        this.texture = loadId();
        this.ids = loadIdsArray2di();
    }

    public GLTextureTile(File file, int tileW, int tileH) throws IOException {
        super(file, tileW, tileH);
        this.texture = loadId();
        this.ids = loadIdsArray2di();
    }

    // Copy constructors

    public GLTextureTile(Array2di array2di, int tileW, int tileH) {
        super(array2di, tileW, tileH);
        this.texture = loadId();
        this.ids = loadIdsArray2di();
    }

    public GLTextureTile(Texture texture, int tileW, int tileH) {
        super(texture, tileW, tileH);
        this.texture = loadId();
        this.ids = loadIdsArray2di();
    }

    public GLTextureTile(Texture texture, int id, int tileW, int tileH) {
        super(texture, tileW, tileH);
        this.texture = id;
        this.ids = loadIdsArray2di();
    }

    public GLTextureTile(GLTexture texture, int tileW, int tileH) {
        super(texture, tileW, tileH);
        this.texture = texture.texture;
        this.ids = loadIdsArray2di();
    }

    public GLTextureTile(GLTextureTile copy) {
        super(copy);
        this.texture = copy.texture;
        this.ids = copy.ids;
    }

    // Methods

    public int[] loadIdsArray() {
        int[] ids = new int[numTilesX * numTilesY];
        for (int i = 0; i < grid.size(); i++) {
            ids[i] = new GLTexture(grid.get(i)).loadId();
        }
        return ids;
    }

    public Array2di loadIdsArray2di() {
        return new Array2di(loadIdsArray(), numTilesX, numTilesY);
    }

    // Getters

    @Override
    public GLTexture getTile(int tileX, int tileY) {
        return new GLTexture(super.getTile(tileX, tileY));
    }

    @Override
    public GLTexture getTile(int index) {
        return new GLTexture(super.getTile(index));
    }

    @Override
    public void writeArray() {
        super.writeArray();
        texture = loadId();
        this.ids = loadIdsArray2di();
    }

    public Array2di getIds() {
        if (ids == null) {
            ids = loadIdsArray2di();
        }
        return ids;
    }

    public int[] getIdsArray() {
        return ids.getArray();
    }

    public List<Integer> getIdsList() {
        final int[] ids = getIdsArray();
        List<Integer> l = new ArrayList<>();
        for (int id : ids) {
            l.add(id);
        }
        return l;
    }

    public GLTexture[] getGLTextures() {
        GLTexture[] array = new GLTexture[grid.size()];
        for (int i = 0; i < grid.size(); i++) {
            array[i] = new GLTexture(grid.get(0), ids.get(i));
        }
        return array;
    }

    public Array2do<GLTexture> getArray2dGLTexture() { // todo no sé si tendrán los "textures" correctos las instancias GLTexture
        return Array2do.newNotType(getGLTextures(), grid.getWidth(), grid.getHeight());
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

    public int getTextureID() {
        if (texture == 0) {
            texture = loadId();
        }
        return texture;
    }

    @Override
    public String toString() {
        return super.toString().replaceAll("^TextureTile", "GLTextureTile") + " id=" + texture;
    }
    
}
