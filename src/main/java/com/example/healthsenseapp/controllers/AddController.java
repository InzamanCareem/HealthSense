package com.example.healthsenseapp.controllers;

import com.example.healthsenseapp.models.CountryReport;
import com.example.healthsenseapp.models.Hospital;
import com.example.healthsenseapp.services.ReportRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class AddController implements Initializable {

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField weekTextField;

    @FXML
    private VBox hospitalContainer;

    @FXML
    private Button addHospitalButton;

    private final Map<TextField, Map<TextField, Spinner<Integer>>> hospitalNameMap = new HashMap<>();

    DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    private TitledPane createHospitalPane() {

        VBox content = new VBox(10);

        Label hospitalLabel = new Label("Hospital Name");

        TextField hospitalName = new TextField();
        hospitalName.setPromptText("Enter hospital name");

        VBox diseaseContainer = new VBox(8);

        Button addDisease = new Button("+ Add Disease");

        HBox removeContainer = new HBox();
        Button remove = new Button("- Remove Hospital");
        removeContainer.getChildren().add(remove);
        removeContainer.setAlignment(Pos.CENTER_RIGHT);

        Map<TextField, Spinner<Integer>> diseaseMap = new HashMap<>();

        addDisease.setOnAction(e -> diseaseContainer.getChildren().add(
                createDiseaseRow(diseaseContainer, diseaseMap)));

        diseaseContainer.getChildren().add(createDiseaseRow(diseaseContainer, diseaseMap));

        content.getChildren().addAll(hospitalLabel, hospitalName, new Label("Diseases"), diseaseContainer,
                addDisease, removeContainer);

        hospitalNameMap.put(hospitalName, diseaseMap);

        TitledPane pane = new TitledPane();
        pane.setText("Hospital");
        pane.setContent(content);
        pane.setExpanded(true);

        hospitalName.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isBlank()) {
                pane.setText("Hospital");
            } else {
                pane.setText(newVal);
            }
        });

        remove.setOnAction(e -> {
            VBox parent = (VBox) pane.getParent();
            parent.getChildren().remove(pane);
        });

        return pane;
    }

    private HBox createDiseaseRow(VBox parent, Map<TextField, Spinner<Integer>> diseaseMap) {

        TextField diseaseName = new TextField();
        diseaseName.setPromptText("Disease Name");

        Spinner<Integer> count = new Spinner<>(0, 100000, 0);

        count.setEditable(true);

        diseaseMap.put(diseaseName, count);

        Button remove = new Button("X");

        HBox row = new HBox(10);

        HBox.setHgrow(diseaseName, Priority.ALWAYS);

        row.getChildren().addAll(diseaseName, count, remove);

        remove.setOnAction(e -> parent.getChildren().remove(row));

        return row;
    }

    @FXML
    private void saveAddedDetails(){
        String countryName = countryTextField.getText();
        int weekNumber = Integer.parseInt(weekTextField.getText());

        Hospital[] allHospitalDetails = new Hospital[hospitalNameMap.size()];
        int index = 0;

        for (Map.Entry<TextField, Map<TextField, Spinner<Integer>>> hospitalMapKV : hospitalNameMap.entrySet()) {

            String hospitalName = hospitalMapKV.getKey().getText();

            Hospital hospital = new Hospital(hospitalName, countryName);

            for (Map.Entry<TextField, Spinner<Integer>> diseaseMapKV : hospitalMapKV.getValue().entrySet()) {

                String diseaseName = diseaseMapKV.getKey().getText();
                int diseaseCount = diseaseMapKV.getValue().getValue();

                hospital.addDisease(weekNumber, diseaseName, diseaseCount);
            }

            allHospitalDetails[index++] = hospital;
        }

        CountryReport newCountryReport = new CountryReport("pending", ReportRepository.getReportNumber(), countryName, weekNumber, allHospitalDetails);
        ReportRepository.getCountryReportsHandler().enqueue(newCountryReport);
        ReportRepository.getCountryReportsViewer().enqueue(newCountryReport);
        ReportRepository.incrementReportNumber();

        System.out.println("Method End");

        for (int i = 0; i < index; i++) {
            allHospitalDetails[i].displayDiseaseRecord();
        }

        // reset all fields
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addHospitalButton.setOnAction(e ->
                hospitalContainer.getChildren().add(createHospitalPane())
        );

        hospitalContainer.getChildren().add(createHospitalPane());
    }
}