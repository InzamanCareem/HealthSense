package HealthSense;

/**
 * Represents a node to hold a disease name, total count, and severity and a reference to the parent, left child and right child nodes.
 */
public class TreeNode {
    public String diseaseName;
    public int totalCount;
    public String severity;
    public TreeNode leftChild;
    public TreeNode rightChild;
    public TreeNode parent;

    /**
     * Constructs a new TreeNode containing the given disease name, total count, and severity.
     */
    public TreeNode(String diseaseName, int totalCount, String severity) {
        this.diseaseName = diseaseName;
        this.totalCount = totalCount;
        this.severity = severity;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }
}
