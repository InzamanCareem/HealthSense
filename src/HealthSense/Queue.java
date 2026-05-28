package HealthSense;

/**
 * Represents a Queue that holds outbreak reports.
 */
public class Queue {
    ReportLinkedList queue = new ReportLinkedList();

    /**
     * Adds a report at the end.
     */
    public void enqueue(CountryReport report){
        queue.addLast(report);
    }

    /**
     * Removes the first report.
     */
    public CountryReport dequeue(){
        return queue.isEmpty() ? null : queue.removeFirst();
    }

    /**
     * Displays all the reports.
     */
    public void display(){
        queue.displayReports();
    }
}
