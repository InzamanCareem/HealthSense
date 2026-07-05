package com.example.healthsenseapp.controllers;

import com.example.healthsenseapp.services.AlertManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;

import java.io.IOException;

public class DashboardController {
    private AppController appController;

    public void setAppController(AppController appController) {
        this.appController = appController;
    }

    @FXML
    protected void changeToAddScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/healthsenseapp/fxml/add.fxml"));
        Parent root = loader.load();

        AddController addController = loader.getController();
        addController.setDashboardController(this);

        appController.getRootPane().setCenter(root);
        appController.getRootPane().setPadding(new Insets(10, 0, 10, 0));
    }

    @FXML
    protected void changeToSearchScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/healthsenseapp/fxml/search.fxml"));
        Parent root = loader.load();

        appController.getRootPane().setCenter(root);
        appController.getRootPane().setPadding(new Insets(10, 0, 10, 0));
    }

    @FXML
    protected void changeToSortScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/healthsenseapp/fxml/sort.fxml"));
        Parent root = loader.load();

        appController.getRootPane().setCenter(root);
        appController.getRootPane().setPadding(new Insets(10, 0, 10, 0));
    }

    @FXML
    protected void undo() {
        ButtonType confirmUndo = AlertManager.showConfirmation("Undo", "You're about to undo the recent operation", "Do you really want to undo the operation?");
        if (confirmUndo == ButtonType.OK) {

        }
    }

    @FXML
    protected void changeToProcessScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/healthsenseapp/fxml/process.fxml"));
        Parent root = loader.load();

        ProcessController processController = loader.getController();
        processController.setDashboardController(this);

        appController.getRootPane().setCenter(root);
        appController.getRootPane().setPadding(new Insets(10, 0, 10, 0));
    }

    @FXML
    protected void changeToBSTScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/healthsenseapp/fxml/bst.fxml"));
        Parent root = loader.load();

        appController.getRootPane().setCenter(root);
        appController.getRootPane().setPadding(new Insets(10, 0, 10, 0));
    }

    @FXML
    protected void changeToForecastScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/healthsenseapp/fxml/forecast.fxml"));
        Parent root = loader.load();

        appController.getRootPane().setCenter(root);
        appController.getRootPane().setPadding(new Insets(10, 0, 10, 0));
    }

    @FXML
    protected void exit() {
        ButtonType confirmExit = AlertManager.showConfirmation("Exit", "You're about to exit the application", "Do you really want to exit?");
        if (confirmExit == ButtonType.OK) {

        }
    }


}
