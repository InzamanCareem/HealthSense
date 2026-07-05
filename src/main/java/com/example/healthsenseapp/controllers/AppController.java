package com.example.healthsenseapp.controllers;


import com.example.healthsenseapp.models.CountryReport;
import com.example.healthsenseapp.models.Hospital;
import com.example.healthsenseapp.services.ReportRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {
    @FXML
    private BorderPane rootPane;

    public BorderPane getRootPane() {
        return rootPane;
    }

    @FXML
    protected void changeToDashboardScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/healthsenseapp/fxml/dashboard.fxml"));
        Parent root = loader.load();

        DashboardController dashboardController = loader.getController();
        dashboardController.setAppController(this);

        this.rootPane.setCenter(root);
        this.rootPane.setPadding(new Insets(10, 0, 10, 0));
    }

//    @FXML
//    protected void changeToReportsScene() throws IOException {
//    }
//
//    @FXML
//    protected void changeToAnalyticsScene() throws IOException {
//    }
//
//    @FXML
//    protected void changeToForecastScene() throws IOException {
//    }
//
//    @FXML
//    protected void changeToQueueScene() throws IOException {
//    }
//
//    @FXML
//    protected void changeToSettingsScene() throws IOException {
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String[] countries = {"Canada", "USA", "UK", "Germany", "France", "Japan", "Australia", "India", "Brazil",
                "Italy"};

        String[] hospitalNames = {"Hemas", "Asiri", "General", "CityCare", "Apollo", "Mercy", "Central", "National",
                "Hope", "Sunrise"};

        String[] diseases = {"Corona", "Dengue", "Malaria", "Flu", "Cholera", "Tuberculosis", "Measles", "Typhoid",
                "ChickenPox", "Diabetes"};

        for (int i = 1; i <= 10; i++) {

            Hospital[] hospitals = new Hospital[2];

            String country = countries[(i - 1) % countries.length];

            Hospital hospital1 = new Hospital(hospitalNames[(i * 2 - 2) % hospitalNames.length] + i, country);
            hospital1.addDisease(1, diseases[(i - 1) % diseases.length], 1000 + i);

            Hospital hospital2 = new Hospital(hospitalNames[(i * 2 - 1) % hospitalNames.length] + i, country);
            hospital2.addDisease(1, diseases[i % diseases.length], 20 + i * 2);

            // Add another disease to every second hospital
            if (i % 2 == 0) {
                hospital2.addDisease(1, diseases[(i + 3) % diseases.length], 5 + i);
            }

            hospitals[0] = hospital1;
            hospitals[1] = hospital2;

            CountryReport report = new CountryReport("pending", i, country, 1, hospitals);

            ReportRepository.getCountryReportsHandler().enqueue(report);
            ReportRepository.getCountryReportsViewer().enqueue(report);
            ReportRepository.incrementReportNumber();
        }


        try {
            changeToDashboardScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}