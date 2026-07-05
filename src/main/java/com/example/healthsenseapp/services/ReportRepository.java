package com.example.healthsenseapp.services;

import com.example.healthsenseapp.models.CountryReport;
import com.example.healthsenseapp.models.Queue;

public class ReportRepository {

    private static final Queue countryReportsHandler = new Queue();
    private static Queue countryReportsViewer = new Queue();
    private static int reportNumber = 1;

    public static Queue getCountryReportsHandler() {
        return countryReportsHandler;
    }

    public static Queue getCountryReportsViewer() {
        return countryReportsViewer;
    }

    public static int getReportNumber() {
        return reportNumber;
    }

    public static void incrementReportNumber() {
        reportNumber++;
    }

    public static void updateCountryReportsViewer(){
        Queue newViewer = new Queue();

        for (CountryReport report : countryReportsHandler.getReports()){
            newViewer.enqueue(report);
        }

        countryReportsViewer = newViewer;
    }
}
