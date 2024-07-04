package org.example.desktopapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.desktopapp.restapi.RestAPIAlunoService;
import org.example.desktopapp.utils.Utils;

public class ThesismanApp extends Application {
    private static Scene previousScene;
    private final RestAPIAlunoService service = RestAPIAlunoService.getInstance();
    @Override
    public void start(Stage stage) {
        service.populate();
        Scene scene = Utils.loadScene("/org/example/desktopapp/templates/login.fxml");
        stage.setTitle("Thesisman");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        previousScene = scene;
    }
    public static void main(String[] args) {
        launch();
    }
}