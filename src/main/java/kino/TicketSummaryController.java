package kino;

import enums.EnumTicketType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Person;
import models.Screening;
import models.Ticket;

import java.io.IOException;

public class TicketSummaryController {
    private Person client;
    private Screening selectedScreening;
    private EnumTicketType selectedDiscount;

    @FXML
    private Label loggedUserLabel;

    @FXML
    private Label ticketSummaryLabel;

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("clientMenu.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }

    @FXML
    void payForTicket(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ticketInfo.fxml"));
        Parent root = (Parent) loader.load();
        TicketInfoController ticketInfoController = loader.getController();
        ticketInfoController.setValues(client, client.buyTicketForScreening(selectedScreening, selectedDiscount));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        //zapis ekstensji!
    }

    public void setValues(Person client, Screening selectedScreening, EnumTicketType selectedDiscount) {
        this.client = client;
        this.selectedScreening = selectedScreening;
        this.selectedDiscount = selectedDiscount;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());
        ticketSummaryLabel.setText("Twój bilet kosztuje " + Ticket.checkPriceWithDiscount(selectedScreening, selectedDiscount) + "zł");
    }
}
