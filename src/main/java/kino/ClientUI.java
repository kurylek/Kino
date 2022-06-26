package kino;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objectPlus.ObjectPlus;

import java.io.*;

public class ClientUI extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        try {
            ObjectPlus.readExtent(new ObjectInputStream(new DataInputStream(new BufferedInputStream(new FileInputStream("D:\\projekt")))));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Couldn't read extent from file!");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(ClientUI.class.getResource("logIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 400);
        stage.setTitle("Menu użytkownika");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
        stage.setMinWidth(616);
        //stage.setWidth(616);
        stage.setWidth(1000);
        stage.setMinHeight(400);
        stage.setHeight(439);
        stage.setScene(scene);
        stage.show();
    }
}