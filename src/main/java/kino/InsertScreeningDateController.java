package kino;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import models.Person;
import models.Screening;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InsertScreeningDateController {
    private Person client;

    @FXML
    private DatePicker dateInput;

    @FXML
    private Label loggedUserLabel;

    @FXML
    void backToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("clientMenu.fxml"));
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

            List<Screening>  screeningsAtDate = Screening.getScreeningsAtDate(outputValue);
            if (screeningsAtDate.size() == 0) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("noScreeningsAtDate.fxml"));
                Parent root = (Parent) loader.load();
                NoScreeningsAtDateController noScreeningsAtDateController = loader.getController();
                noScreeningsAtDateController.setValues(client);
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root, 600, 400));
            }else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("screeningsAtDate.fxml"));
                Parent root = (Parent) loader.load();
                ScreeningsAtDateController screeningsAtDateController = loader.getController();
                screeningsAtDateController.setValues(client, outputValue);
                Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root, 600, 400));
            }
        }
    }

    public void setValues(Person client) {
        this.client = client;

        loggedUserLabel.setText("Zalogowano jako: " + this.client.getName());
    }
}