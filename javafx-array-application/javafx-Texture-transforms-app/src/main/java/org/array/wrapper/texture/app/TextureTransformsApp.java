package org.array.wrapper.texture.app;

import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyCode;
import org.array.wrapper.d2.Array2di;
import org.array.wrapper.d2.operations.ShapeArray2diOperations;
import org.array.wrapper.d2.transforms.Array2diTransformer;
import org.array.wrapper.texture.Texture;
import org.javafx.game.AbstractGame;
import org.javafx.game.GameApplication;
import org.joml.Vector2f;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class TextureTransformsApp extends GameApplication {
    
    static class Game extends AbstractGame {
        
        private Array2di pixels;

        private ShapeArray2diOperations op;

        private Texture texture;

        private float theta = 15.0f;

        private Vector2f offset = new Vector2f(100);

        private Vector2f scale = new Vector2f(5);

        @Override
        public void initialize(GameApplication gc) {
            pixels = new Array2di(gc.getWidth(), gc.getHeight());
            op = new ShapeArray2diOperations(pixels);
            texture = null;
            final String filename = Paths.get("javafx-array-application", "javafx-Texture-transforms-app", "src", "main", "resources", "images", "dg_features32_bricks.png").toAbsolutePath().toString();
            try {
                texture = new Texture(new File(filename));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void update(GameApplication gc, float dt) {
            final float v = 500f;
            final float space1 = v * dt;
            final float space2 = space1 / 100;
            if (gc.getInput().isKeyHeld(KeyCode.LEFT)) {
                offset.x -= space1;
            }
            if (gc.getInput().isKeyHeld(KeyCode.RIGHT)) {
                offset.x += space1;
            }
            if (gc.getInput().isKeyHeld(KeyCode.UP)) {
                offset.y -= space1;
            }
            if (gc.getInput().isKeyHeld(KeyCode.DOWN)) {
                offset.y += space1;
            }

            if (gc.getInput().isKeyHeld(KeyCode.A)) {
                theta -= space1;
            }
            if (gc.getInput().isKeyHeld(KeyCode.Q)) {
                theta += space1;
            }

            if (gc.getInput().isKeyHeld(KeyCode.NUMPAD4)) {
                scale.x -= space2;
            }
            if (gc.getInput().isKeyHeld(KeyCode.NUMPAD6)) {
                scale.x += space2;
            }
            if (gc.getInput().isKeyHeld(KeyCode.NUMPAD2)) {
                scale.y += space2;
            }
            if (gc.getInput().isKeyHeld(KeyCode.NUMPAD8)) {
                scale.y += space2;
            }
        }

        private void writePixels(GameApplication gc) {
            PixelWriter pw = gc.getGraphicsContext().getPixelWriter();
            pw.setPixels(0, 0, gc.getWidth(), gc.getHeight(), PixelFormat.getIntArgbInstance(), pixels.getArray(), 0, gc.getWidth());
        }

        @Override
        public void render(GameApplication gc) {
            pixels.fill(0xffA11245);

            if (texture != null) {
                Array2diTransformer t = new Array2diTransformer();
                t.translate(offset.x, offset.y);
                t.rotate(theta);
                t.translate(-texture.getWidth() / 2f, -texture.getHeight() / 2f);
                t.scale(scale.x, scale.y);
                op.writeArray2d(texture, t);
                t.clearTransforms();
            }

            writePixels(gc);
        }
    }
    
    @Override
    public void init() throws Exception {
        super.init();
        setAppName("Image afine transformations");
        setGame(new Game());
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
