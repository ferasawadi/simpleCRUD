package com.hinet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {
ArrayList<Integer> tmp  = new ArrayList<>();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("app.fxml"));
        Stage stage = new Stage();
        stage.setTitle("database Manager");
        Scene loginscene = new Scene(root,768,450);
        stage.setScene(loginscene);
        stage.show();
    }
}
