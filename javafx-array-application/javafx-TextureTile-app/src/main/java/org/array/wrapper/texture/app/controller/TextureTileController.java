package org.array.wrapper.texture.app.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.stage.FileChooser;
import org.array.wrapper.d2.Array2di;
import org.array.wrapper.d2.transforms.Array2diTransformer;
import org.array.wrapper.texture.Texture;
import org.array.wrapper.texture.TextureTile;
import org.array.wrapper.texture.io.xml.writer.TextureTileXmlWriter;
import org.joml.Vector2i;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TextureTileController implements Initializable {

    @FXML
    private SplitPane root;

    // Texture display

    @FXML
    private Canvas canvas;

    @FXML
    private Button btTexturePrevious;

    @FXML
    private Button btTextureNext;

    // texture tile file chooser

    @FXML
    private Button btOpenTextureTile;

    @FXML
    private TextField tfTextureTile;

    @FXML
    private Button btLoadTextureTile;

    @FXML
    private Button btCloseTextureTile;

    // texture tile meta-info chooser

    @FXML
    private Button btOpenMetaInfoFile;

    @FXML
    private Button btSaveMetaInfoFile;

    @FXML
    private TextField tfMetaInfoFile;

    // texture tile properties

    @FXML
    private TextField tfTileWidth;

    @FXML
    private TextField tfTileHeight;

    @FXML
    private TextField tfTextureId;

    @FXML
    private TextField tfTexturePosX;

    @FXML
    private TextField tfTexturePosY;

    @FXML
    private TextField tfTextureTileWidth;

    @FXML
    private TextField tfTextureTileHeight;

    @FXML
    private TextField tfNumTilesWidth;

    @FXML
    private TextField tfNumTilesHeight;

    // File cache

    private Path pathWorkingDirectory;

    private File textureTileFileCache;

    // Texture

    private TextureTile textureTile;

    private final Vector2i tileDimensions = new Vector2i(32, 32);

    private List<Texture> textureList = null;

    private int idTexture = 0;

    // Layers

    private Stack<Array2di> layers = new Stack<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setButtonsActions();
        setTextFieldActions();
    }

    public void setButtonsActions() {
        btTextureNext.setOnAction(event -> {
            idTexture++;
            changeTexture();
        });
        btTexturePrevious.setOnAction(event -> {
            idTexture--;
            changeTexture();
        });

        btOpenTextureTile.setOnAction(event -> {
            File file = chooseFile(textureTileFileCache, "Abrir textura");
            loadTextureTile(file);
        });
        btLoadTextureTile.setOnAction(event -> {
            loadTextureTile(textureTileFileCache, tfTextureTile.getText());
        });
        btCloseTextureTile.setOnAction(event -> {
            tfTextureTile.setText("");
        });

        btOpenMetaInfoFile.setOnAction(event -> {
            File file = chooseFile("Abrir archivo metainformación");
            // todo leer archivo metainformación
        });

        btSaveMetaInfoFile.setOnAction(event -> {
            // todo asegurarse que todo funciona bien
            if (tfTextureTile.getText() != null) {
                String textureTileFile = tfTextureTile.getText();
                Path pathTextureTileFile = Paths.get(textureTileFile);
                String textureTileFilename = pathTextureTileFile.toFile().getName();
                String extension = textureTileFilename.substring(textureTileFilename.lastIndexOf("."));

                String metaFileFilename = textureTileFilename.replaceAll(extension, "") + "_metainfo.xml";
                Path pathMetaInfoFile = Paths.get(pathTextureTileFile.getParent().toAbsolutePath().toString(), metaFileFilename);

                try {
                    Files.createFile(pathMetaInfoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                TextureTileXmlWriter textureTileXMLWriter = new TextureTileXmlWriter(textureTileFile);
                try {
                    textureTileXMLWriter.save(textureTile, new File(pathMetaInfoFile.toAbsolutePath().toString()));
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void setTextFieldActions() {
        tfTileWidth.setOnAction(event -> {
            try {
                int tileWidth = Integer.parseInt(tfTileWidth.getText());
            } catch (NumberFormatException e) {
                Logger.getLogger(TextureTileController.class.getName()).log(Level.SEVERE, null, e);
            }
        });
        tfTileHeight.setOnAction(event -> {
            try {
                int tileWidth = Integer.parseInt(tfTileHeight.getText());
            } catch (NumberFormatException e) {
                Logger.getLogger(TextureTileController.class.getName()).log(Level.SEVERE, null, e);
            }
        });

        tfTextureId.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfTextureId.setText(newValue.replaceAll("\\D", "")); // force the text field to be numeric only
            }
        });
        tfTextureId.setOnAction(event -> {
            idTexture = Integer.parseInt(tfTextureId.getText());
            changeTexture();
        });

        tfTexturePosX.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfTextureId.setText(newValue.replaceAll("\\D", "")); // force the text field to be numeric only
            }
        });
        tfTexturePosX.setOnAction(event -> {
            int x = Integer.parseInt(tfTexturePosX.getText());
            int y = Integer.parseInt(tfTexturePosY.getText());
            idTexture = textureTile.getGrid().toIndex(x, y);
            changeTexture();
        });

        tfTexturePosY.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                tfTextureId.setText(newValue.replaceAll("\\D", "")); // force the text field to be numeric only
            }
        });
        tfTexturePosY.setOnAction(event -> {
            int x = Integer.parseInt(tfTexturePosX.getText());
            int y = Integer.parseInt(tfTexturePosY.getText());
            idTexture = textureTile.getGrid().toIndex(x, y);
            changeTexture();
        });
    }

    // File choose

    private File chooseFile(File cacheFile, final String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        if (cacheFile != null) {
            fileChooser.setInitialDirectory(cacheFile.getParentFile());
        }
        if (pathWorkingDirectory != null) {
            fileChooser.setInitialDirectory(new File(pathWorkingDirectory.toAbsolutePath().toString()));
        }
        return fileChooser.showOpenDialog(root.getScene().getWindow());
    }

    private File chooseFile(final String title) {
        return chooseFile(null, title);
    }

    private void loadTextureTile(File file) {
        if (file != null) {
            try {
                textureTile = new TextureTile(file, tileDimensions.x, tileDimensions.y);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // imageView.setImage(new Image(file.getAbsolutePath()));
            textureTileFileCache = file;
            if (textureTile != null) {
                tfTextureTile.setText(file.getAbsolutePath());

                tfTextureTileWidth.setText(String.format("%d", textureTile.getWidth()));
                tfTextureTileHeight.setText(String.format("%d", textureTile.getHeight()));
                tfNumTilesWidth.setText(String.format("%d", textureTile.getNumTilesX()));
                tfNumTilesHeight.setText(String.format("%d", textureTile.getNumTilesY()));
                tileDimensions.x = textureTile.getTileW();
                tileDimensions.y = textureTile.getTileH();
                tfTileWidth.setText(String.format("%d", tileDimensions.x));
                tfTileHeight.setText(String.format("%d", tileDimensions.y));

                textureList = textureTile.getTexturesList();
                idTexture = 0;
                changeTexture();
            }
        }
    }

    private void loadTextureTile(File file, String text) {
        if (!text.equals("")) {
            if (file != null) {
                if (text.equals(file.getAbsolutePath())) {
                    loadTextureTile(file);
                }
            }
            if (Files.exists(Paths.get(text))) {
                loadTextureTile(new File(text));
            }
        }
    }

    // change texture

    public void changeTexture() {
        if (idTexture >= textureList.size()) {
            idTexture = textureList.size() - 1;
        }
        if (idTexture < 0) {
            idTexture = 0;
        }
        tfTextureId.setText(String.format("%d", idTexture));
        tfTexturePosX.setText(String.format("%d", textureTile.getGrid().toX(idTexture)));
        tfTexturePosY.setText(String.format("%d", textureTile.getGrid().toY(idTexture)));
        if (textureList != null) {
            writeTexture(canvas.getGraphicsContext2D().getPixelWriter(), textureList.get(idTexture));
        }
    }

    // write image on canvas

    public void writeTexture(PixelWriter pw, Texture array) {
        Array2diTransformer transformer = new Array2diTransformer();
        transformer.scale((float) (canvas.getWidth() / array.getWidth()), (float) (canvas.getHeight() / array.getHeight()));
        var o = new Array2di((int) canvas.getWidth(), (int) canvas.getHeight());
        transformer.transform(array, o);
        pw.setPixels(0, 0, o.getWidth(), o.getHeight(), PixelFormat.getIntArgbInstance(), o.getArray(), 0, o.getWidth());
    }

    // Getters & Setters

    public Path getPathWorkingDirectory() {
        return pathWorkingDirectory;
    }

    public void setPathWorkingDirectory(Path pathWorkingDirectory) {
        this.pathWorkingDirectory = pathWorkingDirectory;
    }

    public void setPathWorkingDirectory(String workingDirectory) {
        setPathWorkingDirectory(Paths.get(workingDirectory));
    }

}
