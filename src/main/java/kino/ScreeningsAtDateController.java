package kino;

import enums.EnumScreeningStatus;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Person;
import models.Screening;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ScreeningsAtDateController {
    private Person client;
    private String dateToList;
    private List<Screening> screeningsAtDate;
    private Screening selectedScreening;

    @FXML
    private Label badScreeningInputLabel;

    @FXML
    private Button buyTicketButton;

    @FXML
    private Label loggedUserLabel;

    @FXML
    private TextField screeningInput;

    @FXML
    private Label screeningsAtDateLabel;

    @FXML
    private TextArea screeningsList;

    @FXML
    void backToDateInput(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("insertScreeningDate.fxml"));
        Parent root = (Parent) loader.load();
        InsertScreeningDateController insertScreeningDateController = loader.getController();
        insertScreeningDateController.setValues(client);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
    }

    @FXML
    void buyTicket(ActionEvent event) throws IOException {
        String input = screeningInput.getText();
        try {
            int selectedScreeningNumber = Integer.parseInt(input);
            badScreeningInputLabel.setText("");
            selectedScreening = screeningsAtDate.get(selectedScreeningNumber-1);
            if(selectedScreening.getScreeningStatus() != EnumScreeningStatus.PLANNED){
                badScreeningInputLabel.setText("Ten seans zakończył się, lub jest w trakcie!");
            }else {
                if(!selectedScreening.canBuyTicket()){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("noTicketsForScreening.fxml"));
                    Parent root = (Parent) loader.load();
                    NoTicketsForScreeningController noTicketsForScreeningController = loader.getController();
                    noTicketsForScreeningController.setValues(client, dateToList);
                    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root, 600, 400));
                }else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("selectTicketDiscount.fxml"));
                    Parent root = (Parent) loader.load();
                    SelectTicketDiscountController selectTicketDiscountController = loader.getController();
                    selectTicketDiscountController.setValues(client, selectedScreening, dateToList);
                    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root, 600, 400));
                }
            }
        }catch (NumberFormatException e) {
            badScreeningInputLabel.setText("Wprowadzono niepoprawny numer seansu!");
        }catch (IndexOutOfBoundsException e) {
            badScreeningInputLabel.setText("Wprowadzony numer seansu nie pokrywa się z listą!");
        }
    }

    @FXML
    void changeBuyTicketButton(KeyEvent event) {
        if(screeningInput.getText() == null || screeningInput.getText().trim().isEmpty()) {
            buyTicketButton.setDisable(true);
        }else {
            buyTicketButton.setDisable(false);
        }
    }

    public void setValues(Person client, String dateToList) {
        this.client = client;
        this.dateToList = dateToList;
        this.screeningsAtDate = Screening.getScreeningsAtDate(this.dateToList);

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());


        Screening.updateScreeningStatus();
        screeningsAtDateLabel.setText("Lista seansów w dniu " + this.dateToList);
        screeningsList.setEditable(false);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        for(int i=0; i<screeningsAtDate.size(); i++){
            Screening s = screeningsAtDate.get(i);
            String employeeWarning = "";
            try {
                if(this.client.operateOn(s.getTakesPlace())){
                    employeeWarning = "Obsługujesz tę salę, sprawdź czy masz wolne!";
                }
            } catch (Exception ignore) {}
            screeningsList.setText(screeningsList.getText() + "Seans numer " + (i+1) + ": " + timeFormat.format(s.getScreeningDateTime()) + " Film- " + s.getMovieOnScreening().getTitle() + " " + employeeWarning + "\n");
        }
    }
}
