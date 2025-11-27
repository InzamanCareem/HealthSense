package HealthSense;

/**
 * Represents a singly linked list to store weekly region-based health reports.
 */
public class ReportLinkedList {
    private ReportNode head;
    private ReportNode tail;

    /**
     * Checks if the linked list is empty.
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Adds a new report to the end of the linked list.
     */
    public void addLast(RegionReport report) {
        ReportNode newReportNode = new ReportNode(report);

        // If the linked list is empty, set both the head and the tail to the new node
        if (head == null){
            head = tail = newReportNode;
        }
        else{
            // Add to the end of the list and update the tail
            tail.next = newReportNode;
            tail = newReportNode;
        }
    }

    /**
     * Removes the first node of the linked list.
     */
    public RegionReport removeFirst(){
        if (head == null){
            return null;
        }
        RegionReport regionReport = head.report;
        head = head.next;
        if (head == null){
            tail = null;
        }
        return regionReport;
    }

    /**
     * Displays all the reports stored in the linked list.
     * Prints report number, region name, week number and hospital details.
     */
    public void displayReports() {
        System.out.println();
        ReportNode current = head;
        while (current != null){
            System.out.println("REPORT NO. " + current.report.reportNo);
            System.out.println("REGION NAME: " + current.report.regionName);
            System.out.println("WEEK: " + current.report.weekNo);
            for (int i = 0; i < current.report.hospitalDetails.length; i++) {
                System.out.println("HOSPITAL NAME: " + current.report.hospitalDetails[i].hospitalName);
                System.out.println("HOSPITAL PATIENT COUNT: " + current.report.hospitalDetails[i].totalPatientCount);
            }
            current = current.next;
            System.out.println();
        }
    }
}
