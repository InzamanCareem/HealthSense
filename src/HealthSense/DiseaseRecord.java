package HealthSense;

/**
 * Represents a singly linked list to store diseases.
 */
public class DiseaseRecord {
    private Node head;

    /**
     * Returns the head of this linked list.
     */
    public Node getHead(){
        return head;
    }

    /**
     * Adds/ Updates existing nodes in the linked list.
     */
    public void add(int weekNo, String diseaseName, int diseaseCount){
        boolean diseaseExist = false;
        Node current = head;

        // Traverse the linked list and look for existing disease
        while (current != null) {
            if (current.diseaseName.equalsIgnoreCase(diseaseName)){
                // Disease found, then this should we another week, update the week and the count
                current.addWeek(weekNo, diseaseCount);
                diseaseExist = true;
                break;
            }
            current = current.next;
        }

        // If disease not found, create a new node and add it to the end
        if (!diseaseExist){
            Node newNode = new Node(diseaseName);
            newNode.addWeek(weekNo, diseaseCount);

            if (head == null){
                head = newNode;
            }
            else{
                Node temp = head;
                while (temp.next != null){
                    temp = temp.next;
                }
                temp.next = newNode;
            }
        }
    }

    /**
     * Sorts the diseases using bubble sort algorithm.
     * Time Complexity: O(n^2).
     */
    public void sortDiseasesPerCount(){
        Node sortedNode = null;

        while (true){
            boolean sorted = false;
            Node current = head;

            while (current.next != sortedNode){
                if (current.calculateTotalCountPerDisease() < current.next.calculateTotalCountPerDisease()){
                    String temp1 = current.diseaseName;
                    Node1 temp2 = current.head;
                    current.diseaseName = current.next.diseaseName;
                    current.head = current.next.head;
                    current.next.diseaseName = temp1;
                    current.next.head = temp2;
                    sorted = true;
                }
                current = current.next;
            }
            if (!sorted){
                break;
            }
            sortedNode = current;
        }
    }

    /**
     * Displays each disease with their name and total count.
     */
    public void display(){
        Node current = head;

        while (current != null) {
            System.out.print("(" + current.diseaseName + " : " + current.calculateTotalCountPerDisease() + ") -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    /**
     * Searches a given disease within the linked list and returns their total count.
     */
    public int searchByDiseaseName(String diseaseName){
        Node current = head;

        while (current != null) {
            if (current.diseaseName.equalsIgnoreCase(diseaseName)){
                return current.calculateTotalCountPerDisease();
            }
            current = current.next;
        }
        return 0;
    }

    /**
     * Sorts the weeks of a given disease.
     */
    public TrendNode sortDiseaseCaseCountsWeekly(String diseaseName, TrendNode trendNode){
        Node current = head;

        while (current != null) {
            if (current.diseaseName.equalsIgnoreCase(diseaseName)){
                trendNode = current.getWeeksTotalCount(trendNode);
                break;
            }
            current = current.next;
        }
        return trendNode;
    }

    /**
     * Calculates the total count of all diseases in the linked list.
     */
    public DiseaseCountNode calculateTotalCountPerDisease(DiseaseCountNode diseaseCountHead){
        Node current = head;

        while (current != null) {
            boolean found = false;
            DiseaseCountNode diseaseCountNodeCurrent = diseaseCountHead;
            if (diseaseCountHead == null){
                diseaseCountHead = new DiseaseCountNode(current.diseaseName, current.calculateTotalCountPerDisease());
            }
            else{
                DiseaseCountNode temp = null;

                while (diseaseCountNodeCurrent != null){
                    if (diseaseCountNodeCurrent.diseaseName.equalsIgnoreCase(current.diseaseName)){
                        diseaseCountNodeCurrent.totalCount += current.calculateTotalCountPerDisease();
                        found = true;
                        break;
                    }
                    temp = diseaseCountNodeCurrent;
                    diseaseCountNodeCurrent = diseaseCountNodeCurrent.next;
                }
                if (!found){
                    temp.next = new DiseaseCountNode(current.diseaseName, current.calculateTotalCountPerDisease());
                }
            }
            current = current.next;
        }
        return diseaseCountHead;
    }

    /**
     * Makes a deep copy of the linked list containing diseases.
     */
    public DiseaseRecord makeDeepCopy(){
        DiseaseRecord diseaseListCopy = new DiseaseRecord();

        Node currentDiseaseCopy = this.head;
        Node tailDiseaseCopy = null;

        while (currentDiseaseCopy != null){
            Node newDiseaseNode = currentDiseaseCopy.makeDeepCopy();

            if (diseaseListCopy.head == null){
                diseaseListCopy.head = newDiseaseNode;
            }
            else{
                tailDiseaseCopy.next = newDiseaseNode;
            }
            tailDiseaseCopy = newDiseaseNode;
            currentDiseaseCopy = currentDiseaseCopy.next;
        }
        return diseaseListCopy;
    }
}
