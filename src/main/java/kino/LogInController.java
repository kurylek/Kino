package kino;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Person;


public class LogInController {
    @FXML
    private Label clientNotFoundLabel;
    @FXML
    private TextField emailInput;

    @FXML
    void loginIn(ActionEvent event) {

        try {
            Person client = Person.getClientByEmail(emailInput.getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("clientMenu.fxml"));
            Parent root = (Parent) loader.load();
            ClientMenuController clientMenuController = loader.getController();
            clientMenuController.setValues(client);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
        } catch (Exception e) {
            clientNotFoundLabel.setText("Nie znaleziono klienta o takim emailu!");
        }
    }

    @FXML
    void close(ActionEvent event) {

    }
}
