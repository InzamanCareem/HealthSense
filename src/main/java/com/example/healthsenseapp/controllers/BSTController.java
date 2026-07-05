package com.example.healthsenseapp.controllers;

import com.example.healthsenseapp.services.HospitalRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;

import java.util.List;

public class BSTController {

    @FXML
    private ToggleGroup traversalOrder;

    @FXML
    private RadioButton inOrderRadio;

    @FXML
    private RadioButton preOrderRadio;

    @FXML
    private RadioButton postOrderRadio;

    @FXML
    private GridPane diseaseClassificationGrid;

    private void makeGrid(List<String> traversalList){

        diseaseClassificationGrid.getChildren().clear();

        Label diseaseLabel = new Label("Disease");
        Label casesLabel = new Label("Cases");
        Label severityLabel = new Label("Severity");

        diseaseLabel.setMaxWidth(Double.MAX_VALUE);
        casesLabel.setMaxWidth(Double.MAX_VALUE);
        severityLabel.setMaxWidth(Double.MAX_VALUE);

        diseaseLabel.getStyleClass().addAll("header-row", "cell");
        casesLabel.getStyleClass().addAll("header-row", "cell");
        severityLabel.getStyleClass().addAll("header-row", "cell");

        diseaseClassificationGrid.add(diseaseLabel, 0, 0);
        diseaseClassificationGrid.add(casesLabel, 1, 0);
        diseaseClassificationGrid.add(severityLabel, 2, 0);

        int rowNo = 1;
        for (String row : traversalList){
            String[] item = row.split(":");

            Label item1 = new Label(item[0]);
            Label item2 = new Label(item[1]);
            Label item3 = new Label(item[2]);

            item1.getStyleClass().add("cell");
            item2.getStyleClass().add("cell");
            item3.getStyleClass().add("cell");

            item1.setMaxWidth(Double.MAX_VALUE);
            item2.setMaxWidth(Double.MAX_VALUE);
            item3.setMaxWidth(Double.MAX_VALUE);

            diseaseClassificationGrid.add(item1, 0, rowNo);
            diseaseClassificationGrid.add(item2, 1, rowNo);
            diseaseClassificationGrid.add(item3, 2, rowNo);

            rowNo++;
        }
    }

    @FXML
    private void viewResults() {

        HospitalRepository.getCountryHospitalList().calculateTotalCountPerDisease();

        Toggle selected = traversalOrder.getSelectedToggle();

        if (selected == inOrderRadio){
            List<String> traversalList = HospitalRepository.getCountryHospitalList().viewBinarySearchTreeOrder(1);
            makeGrid(traversalList);
        }
        else if (selected == preOrderRadio) {
            List<String> traversalList = HospitalRepository.getCountryHospitalList().viewBinarySearchTreeOrder(2);
            makeGrid(traversalList);
        }
        else if (selected == postOrderRadio) {
            List<String> traversalList = HospitalRepository.getCountryHospitalList().viewBinarySearchTreeOrder(3);
            makeGrid(traversalList);
        }
    }
}
