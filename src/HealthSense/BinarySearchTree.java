package HealthSense;

/**
 * Represents a Binary Search Tree to store and organize diseases based on their total case counts.
 */
public class BinarySearchTree {
    private TreeNode root;  // The root node of the binary search tree.

    /**
     * Inserts new diseases and their counts into the BST.
     */
    public void insert(String diseaseName, int totalCount){
        root = insertData(root, diseaseName, totalCount);
    }

    /**
     * Recursive algorithm to insert data according to the disease's total count.
     */
    private TreeNode insertData(TreeNode node, String diseaseName, int totalCount){
        String severity;
        if (totalCount <= 20){
            severity = "Mild";
        }
        else if (totalCount <= 50){
            severity = "Moderate";
        }
        else{
            severity = "Severe";
        }

        if (node == null){
            node = new TreeNode(diseaseName, totalCount, severity);
            return node;
        }
        if (totalCount < node.totalCount){  // move to left
            node.leftChild = insertData(node.leftChild, diseaseName, totalCount);
        }
        else if (totalCount > node.totalCount){  // move to right
            node.rightChild = insertData(node.rightChild, diseaseName, totalCount);
        }
        else{  // move to right by default
            node.rightChild = insertData(node.rightChild, diseaseName, totalCount);
        }
        return node;
    }

    /**
     * Outputs the BST using a specified traversal order.
     */
    public void outputOrder(int order){
        if (order == 1){
            inOrder(root);
        }
        else if (order == 2){
            preOrder(root);
        }
        else if (order == 3){
            postOrder(root);
        }
    }

    /**
     * In-order traversal: Left -> Root -> Right.
     * Recursively displays disease data in ascending order of total count.
     */
    private void inOrder(TreeNode node){
        if (node != null){
            inOrder(node.leftChild);
            System.out.println("Disease: " + node.diseaseName + " | Count: " + node.totalCount + " | Severity: " + node.severity);
            inOrder(node.rightChild);
        }
    }

    /**
     * Pre-order traversal: Root -> Left -> Right.
     * Recursively displays disease data in the order they were inserted.
     */
    private void preOrder(TreeNode node){
        if (node != null){
            System.out.println("Disease: " + node.diseaseName + " | Count: " + node.totalCount + " | Severity: " + node.severity);
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        }
    }

    /**
     * Post-order traversal: Left -> Right -> Root.
     * Recursively displays disease data of child nodes and then the root node.
     */
    private void postOrder(TreeNode node){
        if (node != null){
            postOrder(node.leftChild);
            postOrder(node.rightChild);
            System.out.println("Disease: " + node.diseaseName + " | Count: " + node.totalCount + " | Severity: " + node.severity);
        }
    }
}
