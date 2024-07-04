package org.example.desktopapp.listCandidaturas;

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
import org.example.desktopapp.dtos.CandidaturaDTO;
import org.example.desktopapp.restapi.RestAPIAlunoService;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListCandidaturasController implements Initializable {

    @FXML
    private Button removeButton;
    @FXML
    private Button backButton;
    @FXML
    private ListView<CandidaturaDTO> candidaturaListView;

    private CandidaturaDTO selectedCandidatura;

    private final RestAPIAlunoService service = RestAPIAlunoService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        candidaturaListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                this.selectedCandidatura = newSelection;
            }
        });
        this.removeButton.setOnAction(e -> {
            service.deleteCandidatura(selectedCandidatura.getId());
            ObservableList<CandidaturaDTO> candidaturas = FXCollections.observableArrayList(service.getCandidaturas());
            candidaturaListView.setItems(candidaturas);
        });
        this.backButton.setOnAction(e -> {
            URL fxmlURL = getClass().getResource("/org/example/desktopapp/templates/list_temas.fxml");
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

        candidaturaListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(CandidaturaDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox vbox = new VBox(
                            new Label("Id" + item.getId()),
                            new Label("Tema: " + item.getTema()),
                            new Label("Aluno: " + item.getAluno().getNome()),
                            new Label("Estado: " + item.getEstado()));
                    setGraphic(vbox);
                }
            }
        });
        List<CandidaturaDTO> candidaturas = service.getCandidaturas();
        ObservableList<CandidaturaDTO> data = FXCollections.observableArrayList(candidaturas);
        candidaturaListView.setItems(data);
    }
}
