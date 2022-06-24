package kino;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Person;
import models.Screening;

import java.io.IOException;

public class ClientUIController {
    private Person client;

    @FXML
    private Button buyTicketButton;

    @FXML
    private Label loggedUserLabel;

    @FXML
    void showScreenings(ActionEvent event) throws IOException {
        if(Screening.doScreeningExist()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("insertScreeningDate.fxml"));
            Parent root = (Parent) loader.load();
            InsertScreeningDateController insertScreeningDateController = loader.getController();
            insertScreeningDateController.setValues(client);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
        }else{
            Parent root = FXMLLoader.load(getClass().getResource("noScreenings.fxml"));
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 600, 400));
        }
    }


    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("clientMenu.fxml"));
        Parent root = (Parent) loader.load();
        ClientUIController clientUIController = loader.getController();
        clientUIController.setValues(client);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
    }

    @FXML
    void backToScreeningsList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("screeningsAtDate.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }

    public void setValues(Person client) {
        this.client = client;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());

        if(buyTicketButton != null) {
            buyTicketButton.setDisable(true);
        }
    }
}