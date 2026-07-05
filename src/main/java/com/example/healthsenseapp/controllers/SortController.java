package com.example.healthsenseapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SortController implements Initializable {

    @FXML
    private ToggleGroup sortGroup;

    @FXML
    private RadioButton diseaseSort;

    @FXML
    private RadioButton weekSort;

    @FXML
    private VBox sortTextField;
    // TODO: vbox as a text field?

    private void addDiseaseSortTextFields(VBox sortTextField){
        TextField country = new TextField();
        TextField hospital = new TextField();

        country.setPromptText("Enter country name");
        hospital.setPromptText("Enter hospital name");

        sortTextField.getChildren().addAll(country, hospital);
    }

    private void addWeekSortTextFields(VBox sortTextField){
        TextField disease = new TextField();

        disease.setPromptText("Enter disease name");

        sortTextField.getChildren().add(disease);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addDiseaseSortTextFields(sortTextField);

        sortGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            sortTextField.getChildren().clear();

            if (diseaseSort.isSelected()) {
                addDiseaseSortTextFields(sortTextField);
            } else if (weekSort.isSelected()) {
                addWeekSortTextFields(sortTextField);
            }
        });
    }
}
