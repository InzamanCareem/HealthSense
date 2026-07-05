package com.example.healthsenseapp.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML
    private RadioButton diseaseNameRadio;

    @FXML
    private RadioButton hospitalNameRadio;

    @FXML
    private TextField searchTextField;

    @FXML
    private ToggleGroup searchGroup;

    @FXML
    private void searchDetails() {
        Toggle selected = searchGroup.getSelectedToggle();

        if (selected == diseaseNameRadio) {

        }
        else if (selected == hospitalNameRadio) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> {
            if (diseaseNameRadio.isSelected()) {
                searchTextField.setPromptText("Enter disease name");
            }
            else if (hospitalNameRadio.isSelected()) {
                searchTextField.setPromptText("Enter hospital name");
            }
        });
    }
}