package kino;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import models.Person;
import models.Ticket;

import java.io.IOException;

public class ListTicketsController {
    private Person client;

    @FXML
    private TextArea expiredScreeningsTickets;

    @FXML
    private Label loggedUserLabel;

    @FXML
    private TextArea upcomingScreeningsTickets;

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("clientMenu.fxml"));
        Parent root = (Parent) loader.load();
        ClientMenuController clientMenuController = loader.getController();
        clientMenuController.setValues(client);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
    }

    public void setValues(Person client) {
        this.client = client;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());
        upcomingScreeningsTickets.setEditable(false);
        expiredScreeningsTickets.setEditable(false);

        for(Ticket t : client.getBoughtTickets()) {
            //only in upcomingScreeningsTickets for simplify
            upcomingScreeningsTickets.setText(upcomingScreeningsTickets.getText() + t + "\n");
        }
    }
}