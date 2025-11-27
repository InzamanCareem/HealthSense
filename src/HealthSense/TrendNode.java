package HealthSense;

/**
 * Represents a node to hold a week number, total count and a reference to the next node.
 */
public class TrendNode {
    int weekNo;
    int totalCount;
    TrendNode next;

    /**
     * Constructs a new TrendNode containing the given week number and the total count.
     */
    public TrendNode(int weekNo, int totalCount) {
        this.weekNo = weekNo;
        this.totalCount = totalCount;
        this.next = null;
    }
}
