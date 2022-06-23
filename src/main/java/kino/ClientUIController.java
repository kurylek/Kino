package kino;

import enums.EnumScreeningStatus;
import enums.EnumTicketType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import models.Screening;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClientUIController {
    private static List<Screening> screeningsAtDate;
    private static String date;
    private static Screening selectedScreening;

    @FXML
    private DatePicker dateInput;

    @FXML
    private Button buyTicketButton;

    @FXML
    private Label loggedUserLabel;

    @FXML
    private Label badScreeningInputLabel;

    @FXML
    private Label screeningsAtDateLabel;

    @FXML
    private TextField screeningInput;

    @FXML
    private TextArea screeningsList;

    @FXML
    void showScreenings(ActionEvent event) throws IOException {
        Parent root;
        if(Screening.doScreeningExist()){
            root = FXMLLoader.load(getClass().getResource("insertScreeningDate.fxml"));
        }else{
            root = FXMLLoader.load(getClass().getResource("noScreenings.fxml"));
        }
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }

    @FXML
    void showScreeningsAtDate(ActionEvent event) throws ParseException, IOException {
        if(dateInput.getValue() != null){
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date d = inputFormat.parse(String.valueOf(dateInput.getValue()));
            String outputValue = outputFormat.format(d);
            date = outputValue;

            Parent root;
            screeningsAtDate = Screening.getScreeningsAtDate(outputValue);
            if (screeningsAtDate.size() == 0) {
                root = FXMLLoader.load(getClass().getResource("noScreeningsAtDate.fxml"));
            }else {
                root = FXMLLoader.load(getClass().getResource("screeningsAtDate.fxml"));
            }
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root, 600, 400));
        }
    }


    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("clientMenu.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }


    @FXML
    void backToDateInput(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("insertScreeningDate.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
    }


    @FXML
    void backToScreeningsList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("screeningsAtDate.fxml"));
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root, 600, 400));
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
                    Parent root = FXMLLoader.load(getClass().getResource("noTicketsForScreening.fxml"));

                    Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    window.setScene(new Scene(root, 600, 400));
                }else {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("selectTicketDiscount.fxml"));
                    Parent root = (Parent) loader.load();
                    SelectTicketDiscountController selectTicketDiscountController = loader.getController();
                    selectTicketDiscountController.setValues(ClientUI.getClient(), selectedScreening);
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

    @FXML
    private void initialize() {
        loggedUserLabel.setText("Zalogowano jako: " + ClientUI.getClientName());

        if(buyTicketButton != null) {
            buyTicketButton.setDisable(true);
        }

        if(screeningsList != null) {
            Screening.updateScreeningStatus();
            screeningsAtDateLabel.setText("Lista seansów w dniu " + date);
            screeningsList.setEditable(false);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            for(int i=0; i<screeningsAtDate.size(); i++){
                Screening s = screeningsAtDate.get(i);
                String employeeWarning = "";
                try {
                    if(ClientUI.getClient().operateOn(s.getTakesPlace())){
                        employeeWarning = "Obsługujesz tę salę, sprawdź czy masz wolne!";
                    }
                } catch (Exception ignore) {}
                screeningsList.setText(screeningsList.getText() + "Seans numer " + (i+1) + ": " + timeFormat.format(s.getScreeningDateTime()) + " Film- " + s.getMovieOnScreening().getTitle() + " " + employeeWarning + "\n");
            }
        }
    }
}