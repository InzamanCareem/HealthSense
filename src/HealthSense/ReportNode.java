package HealthSense;

/**
 * Represents a node to hold a report and a reference to the next node.
 */
public class ReportNode {
    RegionReport report;
    ReportNode next;

    /**
     * Constructs a new ReportNode containing the given report.
     */
    public ReportNode(RegionReport report){
        this.report = report;
        this.next = null;
    }
}
