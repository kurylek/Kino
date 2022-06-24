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

public class NoTicketsForScreeningController {
    private Person client;
    private String dateToList;

    @FXML
    private Label loggedUserLabel;

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("clientMenu.fxml"));
        Parent root = (Parent) loader.load();
        ClientMenuController clientMenuController = loader.getController();
        clientMenuController.setValues(client);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
    }

    @FXML
    void backToScreeningsList(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("screeningsAtDate.fxml"));
        Parent root = (Parent) loader.load();
        ScreeningsAtDateController screeningsAtDateController = loader.getController();
        screeningsAtDateController.setValues(client, dateToList);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
    }

    public void setValues(Person client, String dateToList) {
        this.client = client;
        this.dateToList = dateToList;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());
    }
}