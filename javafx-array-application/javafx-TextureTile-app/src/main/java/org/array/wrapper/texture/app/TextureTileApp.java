package org.array.wrapper.texture.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.array.wrapper.texture.app.controller.TextureTileController;

import java.util.List;
import java.util.Objects;

public class TextureTileApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/view_textures.fxml")));
        Parent root = fxmlLoader.load();
        TextureTileController controller = fxmlLoader.<TextureTileController>getController();

        List<String> params = getParameters().getRaw();
        if (!params.isEmpty()) {
            String workingDirectory = params.get(0);
            controller.setPathWorkingDirectory(workingDirectory);
        }

        stage.setTitle("Editor de metainformación de texturas de mazmorras");
        stage.setScene(new Scene(root, 970, 580));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
