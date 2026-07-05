package com.example.healthsenseapp.models;


/**
 * Represents a report that holds report details.
 */
public class CountryReport {
    public String status;
    public int reportNo;
    public String countryName;
    public int weekNo;
    public Hospital[] hospitalDetails;  // Stores a list of hospital objects

    /**
     * Constructs a new countryReport containing the given report number, country name, week number and hospital details.
     */
    public CountryReport(String status, int reportNo, String countryName, int weekNo, Hospital[] hospitalDetails) {
        this.status = status;
        this.reportNo = reportNo;
        this.countryName = countryName;
        this.weekNo = weekNo;
        this.hospitalDetails = hospitalDetails;
    }

    public CountryReport(){

    }
}
