package org.example.desktopapp.utils;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class Utils {

    public static Scene loadScene(String fxmlPath) {
        try {
            URL fxmlURL = Utils.class.getResource(fxmlPath);
            if (fxmlURL == null) {
                throw new IllegalArgumentException("fxmlPath not found");
            }
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            Parent root = fxmlLoader.load();
            return new Scene(root);
        }
        catch (IOException e) {
            System.out.println("Error loading scene" + e.getMessage());
            return null;
        }
    }

    public static void showAlertDialog(
            String title, String headerText, String contentText, Alert.AlertType type, URL cssUrl) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        if (contentText != null) {
            alert.setContentText(contentText);
        }
        if (cssUrl != null) {
            alert.getDialogPane().getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.out.println("Cannot find cssURL resource ");
        }
        alert.show();
    }

}