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
            Person p = Person.getClientByEmail(emailInput.getText());
            ClientUI.setClient(p);

            Parent root = FXMLLoader.load(getClass().getResource("clientMenu.fxml"));
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 600, 400));
        } catch (Exception e) {
            clientNotFoundLabel.setText("Nie znaleziono klienta o takim emailu!");
        }
    }
}
