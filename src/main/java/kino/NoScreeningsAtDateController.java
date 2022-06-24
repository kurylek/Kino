package kino;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Person;

import java.io.IOException;

public class NoScreeningsAtDateController {
    private Person client;

    @FXML
    private Label loggedUserLabel;

    @FXML
    void backToDateInput(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("insertScreeningDate.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("clientMenu.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }

    public void setValues(Person client) {
        this.client = client;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());
    }
}