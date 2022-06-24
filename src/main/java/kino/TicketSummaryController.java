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
import objectPlus.ObjectPlus;

import java.io.*;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("clientMenu.fxml"));
        Parent root = (Parent) loader.load();
        ClientMenuController clientMenuController = loader.getController();
        clientMenuController.setValues(client);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
    }

    @FXML
    void payForTicket(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ticketInfo.fxml"));
        Parent root = (Parent) loader.load();
        TicketInfoController ticketInfoController = loader.getController();
        ticketInfoController.setValues(client, client.buyTicketForScreening(selectedScreening, selectedDiscount));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));

        ObjectPlus.writeExtent(new ObjectOutputStream(new DataOutputStream(new BufferedOutputStream(new FileOutputStream("D:\\projekt")))));
    }

    public void setValues(Person client, Screening selectedScreening, EnumTicketType selectedDiscount) {
        this.client = client;
        this.selectedScreening = selectedScreening;
        this.selectedDiscount = selectedDiscount;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());
        ticketSummaryLabel.setText("Twój bilet kosztuje " + Ticket.checkPriceWithDiscount(selectedScreening, selectedDiscount) + "zł");
    }
}
