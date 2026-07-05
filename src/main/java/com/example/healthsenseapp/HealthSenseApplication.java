package com.example.healthsenseapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HealthSenseApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/app.fxml"));
        Scene scene = new Scene(loader.load());

        stage.setTitle("HealthSense");
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();

//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent windowEvent) {
//                windowEvent.consume();
//                exit(stage);
//            }
//        });
    }

//    public void exit(Stage stage) {
//        ButtonType confirmExit = AlertManager.showConfirmation("Exit", "You're about to exit the application", "Do you really want to exit?");
//        if (confirmExit == ButtonType.OK) {
//            stage.close();
//        }
//    }
}