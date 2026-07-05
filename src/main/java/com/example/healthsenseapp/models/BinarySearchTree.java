package com.example.healthsenseapp.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Binary Search Tree to store and organize diseases based on their total case counts.
 */
public class BinarySearchTree {
    private TreeNode root;  // The root node of the binary search tree.

    List<String> traversalList = new ArrayList<>();

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
        if (totalCount <= 500){
            severity = "Mild";
        }
        else if (totalCount <= 1000){
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
    public List<String> outputOrder(int order){
        if (order == 1){
            return inOrder(root);
        }
        else if (order == 2){
            return preOrder(root);
        }
        else if (order == 3){
            return postOrder(root);
        }

        return null;
    }

    /**
     * In-order traversal: Left -> Root -> Right.
     * Recursively displays disease data in ascending order of total count.
     */
    private List<String> inOrder(TreeNode node){
        if (node != null){
            inOrder(node.leftChild);
            traversalList.add(node.diseaseName + ":" + node.totalCount + ":" + node.severity);
            inOrder(node.rightChild);
        }
        return traversalList;
    }

    /**
     * Pre-order traversal: Root -> Left -> Right.
     * Recursively displays disease data in the order they were inserted.
     */
    private List<String> preOrder(TreeNode node){
        if (node != null){
            traversalList.add(node.diseaseName + ":" + node.totalCount + ":" + node.severity);
            preOrder(node.leftChild);
            preOrder(node.rightChild);
        }
        return traversalList;
    }

    /**
     * Post-order traversal: Left -> Right -> Root.
     * Recursively displays disease data of child nodes and then the root node.
     */
    private List<String> postOrder(TreeNode node){
        if (node != null){
            postOrder(node.leftChild);
            postOrder(node.rightChild);
            traversalList.add(node.diseaseName + ":" + node.totalCount + ":" + node.severity);
        }
        return traversalList;
    }
}
