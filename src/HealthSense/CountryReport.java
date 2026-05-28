package HealthSense;

/**
 * Represents a report that holds report details.
 */
public class CountryReport {
    public int reportNo;
    public String countryName;
    public int weekNo;
    public Hospital[] hospitalDetails;  // Stores a list of hospital objects

    /**
     * Constructs a new countryReport containing the given report number, country name, week number and hospital details.
     */
    public CountryReport(int reportNo, String countryName, int weekNo, Hospital[] hospitalDetails) {
        this.reportNo = reportNo;
        this.countryName = countryName;
        this.weekNo = weekNo;
        this.hospitalDetails = hospitalDetails;
    }
}
