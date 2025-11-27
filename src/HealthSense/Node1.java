package HealthSense;

/**
 * Represents a node to hold a week number, count number and a reference to the next node.
 */
public class Node1 {
    public int weekNo;
    public int countNo;
    public Node1 next;

    /**
     * Constructs a new Node1 containing the given week number and the count number.
     */
    public Node1(int weekNo, int countNo) {
        this.weekNo = weekNo;
        this.countNo = countNo;
        this.next = null;
    }
}
