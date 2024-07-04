module org.example.desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;


    opens org.example.desktopapp to javafx.fxml;
    exports org.example.desktopapp;
    exports org.example.desktopapp.listTemas to javafx.fxml;
    opens org.example.desktopapp.listTemas to javafx.fxml;
    exports org.example.desktopapp.dtos;
    exports org.example.desktopapp.login;
    opens org.example.desktopapp.login to javafx.fxml;
    opens org.example.desktopapp.dtos to com.fasterxml.jackson.databind;
    exports org.example.desktopapp.listCandidaturas to javafx.fxml;
    opens org.example.desktopapp.listCandidaturas to javafx.fxml;
    exports org.example.desktopapp.TeseDetails to javafx.fxml;
    opens org.example.desktopapp.TeseDetails to javafx.fxml;

}