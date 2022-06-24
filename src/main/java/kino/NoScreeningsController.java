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

public class NoScreeningsController {
    private Person client;

    @FXML
    private Label loggedUserLabel;

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("clientMenu.fxml"));
        Parent root = (Parent) loader.load();
        ClientUIController clientUIController = loader.getController();
        clientUIController.setValues(client);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
    }

    public void setValues(Person client) {
        this.client = client;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());
    }
}