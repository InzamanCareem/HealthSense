package HealthSense;

/**
 * Represents a report that holds report details.
 */
public class RegionReport {
    public int reportNo;
    public String regionName;
    public int weekNo;
    public Hospital[] hospitalDetails;  // Stores a list of hospital objects

    /**
     * Constructs a new RegionReport containing the given report number, region name, week number and hospital details.
     */
    public RegionReport(int reportNo, String regionName, int weekNo, Hospital[] hospitalDetails) {
        this.reportNo = reportNo;
        this.regionName = regionName;
        this.weekNo = weekNo;
        this.hospitalDetails = hospitalDetails;
    }
}
