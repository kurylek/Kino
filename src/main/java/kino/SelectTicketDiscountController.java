package kino;

import enums.EnumTicketType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import models.Person;
import models.Screening;

import java.io.IOException;

public class SelectTicketDiscountController {
    private Person client;
    private Screening selectedScreening;
    private EnumTicketType selectedDiscount;

    @FXML
    private Label loggedUserLabel;

    @FXML
    private RadioButton normalTicket;

    @FXML
    private RadioButton reducedTicket;

    @FXML
    private ToggleGroup ticketDiscount;

    @FXML
    void backToScreeningsList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("screeningsAtDate.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }

    @FXML
    void selectDiscount(ActionEvent event) throws IOException {
        if(normalTicket.isSelected()){
            selectedDiscount = EnumTicketType.NORMAL;
        }else {
            selectedDiscount = EnumTicketType.REDUCED;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ticketSummary.fxml"));
        Parent root = (Parent) loader.load();
        TicketSummaryController ticketSummaryController = loader.getController();
        ticketSummaryController.setValues(client, selectedScreening, selectedDiscount);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));

    }


    public void setValues(Person client, Screening selectedScreening) {
        this.client = client;
        this.selectedScreening = selectedScreening;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());
    }
}
