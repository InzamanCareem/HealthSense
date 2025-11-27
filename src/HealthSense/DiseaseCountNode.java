package HealthSense;

/**
 * Represents a node to hold a disease name, total count and a reference to the next node.
 */
public class DiseaseCountNode {
    String diseaseName;
    int totalCount;
    DiseaseCountNode next;

    /**
     * Constructs a new DiseaseCountNode containing the given disease name and the total count.
     */
    public DiseaseCountNode(String diseaseName, int totalCount) {
        this.diseaseName = diseaseName;
        this.totalCount = totalCount;
        this.next = null;
    }
}
