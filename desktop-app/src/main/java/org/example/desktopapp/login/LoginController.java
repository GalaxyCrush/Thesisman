package org.example.desktopapp.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.desktopapp.restapi.RestAPIAlunoService;

import java.net.URL;

public class LoginController {

    private final RestAPIAlunoService service = RestAPIAlunoService.getInstance();
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private Label errorLabel;

    @FXML
    public void login(ActionEvent event) {
        String username = this.emailField.getText();
        String password = this.passwordField.getText();
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        try {
            if (this.service.login(username, password)) {
                URL fxmlURL;
                if (service.getRespostaLoginAluno().getTese() != null){
                    fxmlURL = getClass().getResource("/org/example/desktopapp/templates/tesedetails.fxml");
                }
                else{
                    fxmlURL = getClass().getResource("/org/example/desktopapp/templates/list_temas.fxml");
                }
                FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } else {
                this.errorLabel.setVisible(true);
                this.errorLabel.setText("Login failed");
            }
        }
        catch(Exception e){
            this.errorLabel.setVisible(true);
            this.errorLabel.setText("Vista não encontrada");
            e.printStackTrace();
        }
    }
}
