package kino;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Person;
import models.Ticket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicketInfoController implements Initializable {
    private Person client;
    private Ticket boughtTicket;

    @FXML
    private Label loggedUserLabel;

    @FXML
    private Label ticketCodeLabel;

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("clientMenu.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setValues(Person client, Ticket boughtTicket) {
        this.client = client;
        this.boughtTicket = boughtTicket;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());
        ticketCodeLabel.setText("Twój bilet ma numer " + this.boughtTicket.getTicketCode());
    }
}
