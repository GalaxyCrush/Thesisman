package org.example.desktopapp.listTemas;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.desktopapp.dtos.TemaDTO;
import org.example.desktopapp.restapi.RestAPIAlunoService;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;


public class ListarTemasController implements Initializable {

    @FXML
    private Button logOut;
    @FXML
    private Button candidatarButton;
    @FXML
    private Button candidaturasButton;
    @FXML
    private ListView<TemaDTO> temaListView;

    private TemaDTO selectedTema;

    private final RestAPIAlunoService service = RestAPIAlunoService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        temaListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedTema = newSelection;
            }
        });
        this.candidaturasButton.setOnAction(e -> {
            service.listarCandidaturas();
            URL fxmlURL = getClass().getResource("/org/example/desktopapp/templates/list-candidaturas.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            Parent root;
            try {
                root = fxmlLoader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        this.candidatarButton.setOnAction(e -> candidatarTema());
        this.logOut.setOnAction(e -> {
            service.logout();
            URL fxmlURL = getClass().getResource("/org/example/desktopapp/templates/login.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            Parent root;
            try {
                root = fxmlLoader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });

        temaListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(TemaDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox vbox = new VBox(new Label("Título: " + item.getTitulo()),
                            new Label("Remuneração: " + item.getRenumeracaoMensal()),
                            new Label("Criador: " + item.getCriadorTema().getNome()));
                    setGraphic(vbox);
                }
            }
        });
        List<TemaDTO> temas = service.listarTemasDisponivelAno(getAno());
        ObservableList<TemaDTO> data = FXCollections.observableArrayList(temas);
        temaListView.setItems(data);
    }

    public void candidatarTema() {
        if (selectedTema == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Selecione um tema");
            alert.show();
        }
        else {
            service.criarCandidatura(selectedTema.getId());
        }
    }

    private String getAno() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        if (currentMonth < Calendar.SEPTEMBER) {
            return (currentYear - 1) + "/" + currentYear;
        } else {
            return currentYear + "/" + (currentYear + 1);
        }
    }

}