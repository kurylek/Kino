package kino;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import objectPlus.ObjectPlus;

import java.io.*;

public class ClientUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            ObjectPlus.readExtent(new ObjectInputStream(new DataInputStream(new BufferedInputStream(new FileInputStream("D:\\projekt")))));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(ClientUI.class.getResource("logIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Menu użytkownika");
        stage.setMinWidth(616);
        stage.setWidth(616);
        stage.setMinHeight(400);
        stage.setHeight(439);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}