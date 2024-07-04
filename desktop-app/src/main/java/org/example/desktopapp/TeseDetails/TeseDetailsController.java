package org.example.desktopapp.TeseDetails;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.desktopapp.dtos.EntregaDTO;
import org.example.desktopapp.dtos.TeseDTO;
import org.example.desktopapp.restapi.RestAPIAlunoService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TeseDetailsController implements Initializable {
    @FXML
    public VBox finalEntregaVBox;
    public Button logoutButton;
    @FXML
    private ListView<EntregaDTO> entregasListView;
    @FXML
    private Label finalEntregaIdLabel;
    @FXML
    private Label finalEntregaDataLabel;
    @FXML
    private Label finalEntregaFicheiroLabel;
    @FXML
    private Label temaLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label mailLabel;
    @FXML
    private Label nomeLabel;
    @FXML
    private Button sendPropostaButton;
    @FXML
    private Button sendFinalButton;

    private TeseDTO tese;

    private final RestAPIAlunoService service = RestAPIAlunoService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.tese = service.getRespostaLoginAluno().getTese();
        EntregaDTO finalEntrega = tese.getEntregaFinal();
        if (finalEntrega != null) {
            finalEntregaIdLabel.setText("Entrega " + finalEntrega.getId());
            finalEntregaDataLabel.setText("Data: " + finalEntrega.getData());
            finalEntregaFicheiroLabel.setText(finalEntrega.getFicheiro() != null ? "Ficheiro: " + this.tese.getEntregaFinal().getFileName() : "Sem ficheiro");

        } else {
            finalEntregaIdLabel.setText("Sem entrega final");
        }
        entregasListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(EntregaDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                }else {
                    String entregaId = "Entrega " + item.getId();
                    String entregaData = "Data: " + item.getData();
                    String entregaFicheiro = item.getFicheiro() != null ? "Ficheiro: " + item.getId() : "Sem ficheiro";
                    String nota = item.getNota();
                    if (nota.equals("-1")) {
                        nota = "Nota não atribuida";
                    }
                    else if (nota.equals("Sem defesa associada")){
                        nota = "Sem defesa associada";
                    }

                    VBox vbox = new VBox(
                            new Label(entregaId),
                            new Label(entregaData),
                            new Label(entregaFicheiro),
                            new Label("Nota: " + nota)
                    );
                    setGraphic(vbox);
                }
            }
        });
        temaLabel.setText("Tema: " + this.tese.getTema().getTitulo());
        statusLabel.setText("Status: " + this.tese.getStatus());
        mailLabel.setText("Mail: " + this.tese.getAluno().getEmail());
        nomeLabel.setText("Nome: " + this.tese.getAluno().getNome());
        sendPropostaButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                try {
                    List<EntregaDTO> newTese = service.sendProposta(this.tese.getAluno().getId(), selectedFile);
                    if (newTese == null) {
                        return;
                    }
                    this.entregasListView.setItems(FXCollections.observableArrayList(newTese));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        sendFinalButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                try {
                    EntregaDTO entrega = service.sendPropostaFinal(this.tese.getAluno().getId(), selectedFile);
                    if (entrega == null) {
                        return;
                    }
                    this.finalEntregaIdLabel.setText("Entrega " + entrega.getId());
                    this.finalEntregaDataLabel.setText("Data: " + entrega.getData());
                    this.finalEntregaFicheiroLabel.setText(entrega.getFicheiro() != null ? "Ficheiro: " + entrega.getFileName() : "Sem ficheiro");
                    this.statusLabel.setText("Status: " + entrega.getEstadoTese());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.logoutButton.setOnAction(event -> {
            service.logout();
            URL fxmlURL = getClass().getResource("/org/example/desktopapp/templates/login.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
            Parent root;
            try {
                root = fxmlLoader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        });
        ObservableList<EntregaDTO> entregas = FXCollections.observableArrayList(this.tese.getEntregas());
        entregasListView.setItems(entregas);
    }

}
