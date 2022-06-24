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
    private String selectedDate;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("screeningsAtDate.fxml"));
        Parent root = (Parent) loader.load();
        ScreeningsAtDateController screeningsAtDateController = loader.getController();
        screeningsAtDateController.setValues(client, selectedDate);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
    }

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

    public void setValues(Person client, Screening selectedScreening, String selectedDate) {
        this.client = client;
        this.selectedScreening = selectedScreening;
        this.selectedDate = selectedDate;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());
    }
}
