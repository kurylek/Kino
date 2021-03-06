package kino;

import enums.EnumPersonType;
import enums.EnumScreeningStatus;
import exceptions.WrongPersonTypeException;
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
import java.text.ParseException;
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
    void backToMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("clientMenu.fxml"));
        Parent root = (Parent) loader.load();
        ClientMenuController clientMenuController = loader.getController();
        clientMenuController.setValues(client);
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
                badScreeningInputLabel.setText("Ten seans zako??czy?? si??, lub jest w trakcie!");
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
            badScreeningInputLabel.setText("Wprowadzony numer seansu nie pokrywa si?? z list??!");
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
        screeningsAtDateLabel.setText("Lista seans??w w dniu " + this.dateToList);
        screeningsList.setEditable(false);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        for(int i=0; i<screeningsAtDate.size(); i++){
            Screening s = screeningsAtDate.get(i);
            String warning = "";
            if(s.getScreeningStatus() == EnumScreeningStatus.DURING) {
                warning = "\tSeans w trakcie";
            }else if(s.getScreeningStatus() == EnumScreeningStatus.COMPLETED) {
                warning = "\tSeans zako??czony";
            }else if(s.getScreeningStatus() == EnumScreeningStatus.CANCELLED) {
                warning = "\tSeans odwo??any";
            }else if(this.client.getPersonTypes().contains(EnumPersonType.EMPLOYEE)){
                try {
                    if(this.client.operateOn(s.getTakesPlace())) {
                        warning = "\tObs??ugujesz t?? sal??, sprawd?? czy masz wolne!";
                    }
                    if(this.client.canBeBusy(s)) {
                        warning = "\tW sali, kt??r?? obs??ugujesz jest w tym czasie seans, sprawd?? czy masz wolne!";
                    }
                }catch (WrongPersonTypeException | ParseException ignore) {}
            }
            screeningsList.setText(screeningsList.getText() + "Seans numer " + (i+1) + ": " + timeFormat.format(s.getScreeningDateTime()) + " Film- " + s.getMovieOnScreening().getTitle() + " " + warning + "\n");
        }
    }
}
