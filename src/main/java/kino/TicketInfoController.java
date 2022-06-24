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

public class TicketInfoController {
    private Person client;
    private Ticket boughtTicket;

    @FXML
    private Label loggedUserLabel;

    @FXML
    private Label ticketCodeLabel;

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("clientMenu.fxml"));
        Parent root = (Parent) loader.load();
        ClientMenuController clientMenuController = loader.getController();
        clientMenuController.setValues(client);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
    }

    public void setValues(Person client, Ticket boughtTicket) {
        this.client = client;
        this.boughtTicket = boughtTicket;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());
        ticketCodeLabel.setText("Twój bilet ma numer " + this.boughtTicket.getTicketCode());
    }
}
