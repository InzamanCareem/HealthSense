package com.example.healthsenseapp.controllers;

import com.example.healthsenseapp.models.CountryReport;
import com.example.healthsenseapp.models.Hospital;
import com.example.healthsenseapp.models.Node;
import com.example.healthsenseapp.services.AlertManager;
import com.example.healthsenseapp.services.HospitalRepository;
import com.example.healthsenseapp.services.ReportRepository;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashSet;
import java.util.Set;


public class ProcessController {

    @FXML
    private Label summaryTitle;

    @FXML
    private Label reports;

    @FXML
    private Label countries;

    @FXML
    private Label hospitals;

    @FXML
    private Label totalCases;

    @FXML
    private VBox reportContainer;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressLabel;

    DashboardController dashboardController;

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;

        makeReportCards();
    }

    private void makeReportCards() {

        reportContainer.getChildren().clear();

        for (CountryReport report : ReportRepository.getCountryReportsViewer().getReports()) {

            VBox reportCard = new VBox();

            String color = report.status.equals("pending") ? "#FFF3CD" : "#D4EDDA";

            reportCard.setStyle("""
                        -fx-padding: 10;
                        -fx-border-color: lightgray;
                        -fx-border-radius: 5;
                        -fx-border-width: 2;
                        -fx-background-radius: 5;
                        -fx-background-color: %s;
                    """.formatted(color));

            Label statusLabel = new Label("Status: " + report.status);
            Label countryLabel = new Label("Country: " + report.countryName);

            reportCard.getChildren().addAll(statusLabel, countryLabel);

            for (Hospital hospital : report.hospitalDetails) {

                Label hospitalName = new Label(hospital.hospitalName);

                VBox hospitalCard = new VBox();
                hospitalCard.getChildren().add(hospitalName);

                Node current = hospital.diseaseList.getHead();

                while (current != null) {

                    Label disease = new Label("  • " + current.diseaseName + ":");
                    Label cases = new Label(String.format("%,d", current.calculateTotalCountPerDisease()) + " cases");

                    disease.setMinWidth(100);
                    disease.setAlignment(Pos.CENTER_LEFT);
                    cases.setMinWidth(75);
                    cases.setAlignment(Pos.CENTER_RIGHT);

                    HBox diseaseRow = new HBox(10, disease, cases);

                    hospitalCard.getChildren().add(diseaseRow);

                    current = current.next;
                }

                reportCard.getChildren().add(hospitalCard);

            }

            reportContainer.getChildren().add(reportCard);
        }

        updateSummary();
    }

    @FXML
    private void processNextReport() {
        CountryReport processingReport = ReportRepository.getCountryReportsHandler().dequeue();

        if (processingReport != null) {
            HospitalRepository.getCountryHospitalList().processReport(processingReport);

            for (CountryReport report : ReportRepository.getCountryReportsViewer().getReports()) {

                if (report.reportNo == processingReport.reportNo && report.status.equals("pending")) {
                    report.status = "completed";
                }
            }
        } else {
            AlertManager.showError("Processing", "Error processing report",
                    "No reports to process!");
        }

        makeReportCards();
    }

    @FXML
    private void processAllReports() {

        int totalReports = ReportRepository.getCountryReportsViewer().getReports().size();

        Task<Void> task = new Task<>() {

            @Override
            protected Void call() {

                int processed = 0;
                CountryReport processingReport;

                while ((processingReport = ReportRepository.getCountryReportsHandler().dequeue()) != null) {

                    HospitalRepository.getCountryHospitalList().processReport(processingReport);

                    for (CountryReport report : ReportRepository.getCountryReportsViewer().getReports()) {

                        if (report.reportNo == processingReport.reportNo && report.status.equals("pending")) {
                            report.status = "completed";
                            break;
                        }
                    }

                    processed++;

                    updateProgress(processed, totalReports);

                    int percentage = (int) ((processed * 100.0) / totalReports);
                    updateMessage("Processed " + processed + " / " + totalReports + " reports (" + percentage + "%)");
                }

                return null;
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());
        progressLabel.textProperty().bind(task.messageProperty());

        task.setOnSucceeded(e -> {

            progressBar.progressProperty().unbind();
            progressLabel.textProperty().unbind();

            progressBar.setProgress(1.0);
            progressLabel.setText("Processing complete!");

            makeReportCards();
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void refresh() {
        ReportRepository.updateCountryReportsViewer();

        makeReportCards();

        progressLabel.setText("");
    }

    private void updateSummary(){

        int numReports = ReportRepository.getCountryReportsHandler().getReports().size();

        if (numReports == 0){
            summaryTitle.setText("Processing completed");
            reports.setText("Reports Processed: " + (ReportRepository.getReportNumber() - 1));
        }
        else{
            reports.setText(reports.getText().split(":")[0] + ": " + numReports);
        }

        Set<String> countryNames = new HashSet<>();
        Set<String> hospitalNames = new HashSet<>();
        int cases = 0;

        for (CountryReport report: ReportRepository.getCountryReportsHandler().getReports()){
            countryNames.add(report.countryName);

            for (int i = 0; i < report.hospitalDetails.length; i++) {
                hospitalNames.add(report.hospitalDetails[i].hospitalName);
                cases += report.hospitalDetails[i].totalPatientCount;
            }
        }

        countries.setText(countries.getText().split(":")[0] + ": " + countryNames.size());
        hospitals.setText(hospitals.getText().split(":")[0] + ": " + hospitalNames.size());
        totalCases.setText(totalCases.getText().split(":")[0] + ": " + String.format("%,d", cases));
    }
}
