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

public class ClientMenuController {
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("noScreenings.fxml"));
            Parent root = (Parent) loader.load();
            NoScreeningsController noScreeningsController = loader.getController();
            noScreeningsController.setValues(client);
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
        }
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("logIn.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
    }

    @FXML
    void showMyTickets(ActionEvent event) {

    }

    public void setValues(Person client) {
        this.client = client;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());

        if(buyTicketButton != null) {
            buyTicketButton.setDisable(true);
        }
    }
}