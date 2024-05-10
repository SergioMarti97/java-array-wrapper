package org.array.wrapper.texture.app;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import org.array.wrapper.d2.Array2di;
import org.array.wrapper.d2.operations.ShapeArray2diOperations;
import org.array.wrapper.texture.TextureTile;
import org.javafx.game.AbstractGame;
import org.javafx.game.GameApplication;
import org.joml.Vector2i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TextureTileSwapApp extends GameApplication {

    static class TextureTileEditorGame extends AbstractGame {

        private Array2di pixels;

        private ShapeArray2diOperations op;

        private final String path;

        private final TextureTile it;

        private Vector2i cursor;

        private Vector2i selectedCursor = null;

        public TextureTileEditorGame(String path, int tileW, int tileH) throws IOException {
            this.path = path;
            this.it = new TextureTile(new File(path), tileW, tileH);
        }

        @Override
        public void initialize(GameApplication gc) {
            // imageTile = new TextureTile(imagePath, 32, 32);
            pixels = new Array2di(gc.getWidth(), gc.getHeight());
            op = new ShapeArray2diOperations(pixels);
            cursor = new Vector2i();
        }

        @Override
        public void update(GameApplication gc, float dt) {
            if (gc.getInput().isButtonDown(MouseButton.PRIMARY)) {
                cursor.x = ((int)gc.getInput().getMouseX() / it.getTileW());
                cursor.y = ((int)gc.getInput().getMouseY() / it.getTileH());
                if (selectedCursor != null) {
                    it.getGrid().swap(selectedCursor.x, selectedCursor.y, cursor.x, cursor.y);
                    selectedCursor = null;
                }
                selectedCursor = new Vector2i(cursor);
            }

            if (gc.getInput().isButtonDown(MouseButton.SECONDARY)) {
                selectedCursor = null;
            }

            if (gc.getInput().isKeyDown(KeyCode.DELETE)) {
                if (selectedCursor != null) {
                    // it.getGrid().swap(selectedCursor.getX(), selectedCursor.getY(), cursor.getX(), cursor.getY());
                    it.getGrid().get(selectedCursor.x, selectedCursor.y).fill(0xffffffff);
                }
            }

            if (gc.getInput().isKeyDown(KeyCode.ENTER)) {
                save();
            }
        }

        public void writePixels(GameApplication gc) {
            PixelWriter pw = gc.getGraphicsContext().getPixelWriter();
            pw.setPixels(0, 0, gc.getWidth(), gc.getHeight(), PixelFormat.getIntArgbInstance(), pixels.getArray(), 0, gc.getWidth());
        }

        @Override
        public void render(GameApplication gc) {
            pixels.fill(0xffA11245);
            for (int x = 0; x < it.getGrid().getWidth(); x++) {
                for (int y = 0; y < it.getGrid().getHeight(); y++) {
                    op.writeArray2d(it.getTile(x, y), x * it.getTileW(), y * it.getTileH());
                }
            }
            op.strokeRect(cursor.x * it.getTileW(), cursor.y * it.getTileH(), it.getTileW(), it.getTileH(), 0xffffffff);
            writePixels(gc);
        }

        public void save() {
            new Thread(() -> {
                try {
                    // retrieve image
                    it.writeArray();
                    BufferedImage bi = new BufferedImage(it.getWidth(), it.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    bi.setRGB(0, 0, it.getWidth(), it.getHeight(), it.getArray(), 0, it.getWidth());
                    File output = new File(path);
                    ImageIO.write(bi, "png", output);
                    System.out.println("¡Guardado!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @Override
    public void init() throws Exception {
        super.init();
        setAppName("Texture Tile editor");
        Parameters params = getParameters();
        if (!params.getRaw().isEmpty()) {
            TextureTile it = new TextureTile(new File(params.getRaw().get(0)), 32, 32);
            setGame(new TextureTileEditorGame(params.getRaw().get(0), 32, 32));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
