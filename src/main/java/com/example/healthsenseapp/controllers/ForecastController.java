package com.example.healthsenseapp.controllers;

import com.example.healthsenseapp.models.ForecastResponse;
import com.example.healthsenseapp.models.ForecastStatistics;
import com.example.healthsenseapp.services.HttpRequestService;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class ForecastController {

    HttpRequestService httpRequestService = new HttpRequestService();

    @FXML
    private TextField userQuery;

    @FXML
    private Label AIReply;

    @FXML
    private Button generateForecast;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Label progressLabel;

    @FXML
    private Label forecastStatistics;

    @FXML
    private VBox chartContainer;

    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();

    LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

    @FXML
    public void sendUserQuery() {
        Task<ForecastResponse> task = httpRequestService.post(userQuery.getText());

        generateForecast.disableProperty().bind(task.runningProperty());

        progressIndicator.visibleProperty().bind(task.runningProperty());

        progressLabel.textProperty().bind(Bindings.when(task.runningProperty()).then("AI Thinking...")
                .otherwise("Done!"));

        task.setOnSucceeded(e -> {
            ForecastResponse result = task.getValue();
            AIReply.setText(result.response);

            chartContainer.getChildren().clear();

            lineChart.getData().clear();

            xAxis.setLabel("Dates");
            yAxis.setLabel("Cases");
            lineChart.setTitle("Future Forecast Predictions");

            lineChart.setId("forecast-chart");

            makeStatistics(result.statistics);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Forecast Data");

            int size = Math.min(result.forecast.size(), result.dates.size());

            for (int i = 0; i < size; i++) {
                series.getData().add(new XYChart.Data<>(result.dates.get(i), result.forecast.get(i)));
            }

            lineChart.getData().add(series);

            chartContainer.getChildren().add(lineChart);
        });

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private void makeStatistics(ForecastStatistics statistics) {

        Label country = new Label("Country Forecast - " + statistics.country);
        Label trend = new Label("Trend - " + statistics.trend);
        Label peak = new Label("Peak - " + statistics.forecast_peak_value + "(" + statistics.forecast_peak_date + ")");
        Label forecastEnd = new Label("Forecast End - " + statistics.forecast_end_value + "(" + statistics.forecast_end_date + ")");
        Label change = new Label("Change - " + statistics.pct_change_vs_recent_avg + " vs last " + statistics.forecast_horizon_days);
        Label lastReported = new Label("Last Reported - " + statistics.last_observed_cases + "(" + statistics.last_observed_date + ")");

        String statistic =
                "Country Forecast - " + statistics.country + "\n" +
                        "Trend - " + statistics.trend + "\n" +
                        "Peak - " + statistics.forecast_peak_value + " (" + statistics.forecast_peak_date + ")" + "\n" +
                        "Forecast End - " + statistics.forecast_end_value + " (" + statistics.forecast_end_date + ")" + "\n" +
                        "Change - " + statistics.pct_change_vs_recent_avg + "% vs last " + statistics.forecast_horizon_days + " days" + "\n" +
                        "Last Reported - " + statistics.last_observed_cases + " (" + statistics.last_observed_date + ")";

        forecastStatistics.setText(statistic);
//
//        Trend           📈 Rising
//        Peak            1,680 cases (Jul 8)
//        Forecast End    1,590 cases (Jul 11)
//        Change          +18.4% vs last 14 days
//        Last Reported   1,245 cases (Jul 4)

    }

}
