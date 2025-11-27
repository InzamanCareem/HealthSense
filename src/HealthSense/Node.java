package HealthSense;

/**
 * Represents a node to hold a disease name, weeks and a reference to the next node.
 */
public class Node {
    public String diseaseName;
    public Node1 head;  // A node to hold the weeks and the case counts
    public Node next;

    /**
     * Constructs a new Node containing the given disease name.
     */
    public Node(String diseaseName) {
        this.diseaseName = diseaseName;
        this.next = null;
        this.head = null;
    }

    /**
     * Adds/ Updates existing nodes containing the given week number and case count.
     */
    public void addWeek(int weekNo, int countNo) {
        // Checks if the week already exists, then update it.
        Node1 current = head;
        while (current != null) {
            if (current.weekNo == weekNo) {
                current.countNo = countNo;
                return;
            }
            current = current.next;
        }

        // Adds a new week with the count.
        Node1 newNode = new Node1(weekNo, countNo);
        if (head == null) {
            head = newNode;
        } else {
            current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    /**
     * Calculates the total case count for all the weeks.
     */
    public int calculateTotalCountPerDisease(){
        int total = 0;

        Node1 current = head;
        while (current != null) {
            total += current.countNo;
            current = current.next;
        }
        return total;
    }

    /**
     * Gets the respective weeks and their case counts and adds/ updates an existing node in the TrendNode linked list.
     */
    public TrendNode getWeeksTotalCount(TrendNode trendNodeHead){

        mergeSort();  // Calls the merge sort to sort all the weeks

        Node1 current = head;

        while (current != null) {
            boolean found = false;
            TrendNode trendNodeCurrent = trendNodeHead;

            if (trendNodeHead == null){
                trendNodeHead = new TrendNode(current.weekNo, current.countNo);
            }
            else{
                TrendNode temp = null;

                while (trendNodeCurrent != null){
                    if (trendNodeCurrent.weekNo == current.weekNo){  // existing week found
                        trendNodeCurrent.totalCount += current.countNo;  // the add the count number to the existing week
                        found = true;
                        break;
                    }
                    temp = trendNodeCurrent;
                    trendNodeCurrent = trendNodeCurrent.next;
                }
                // if no existing week found add a new TrendNode
                if (!found){
                    temp.next = new TrendNode(current.weekNo, current.countNo);
                }
            }
            current = current.next;  // move to the next week
        }
        return trendNodeHead;  // return the updated head of the trend linked list
    }

    /**
     * Sorts the weeks using merge sort algorithm in ascending order.
     * Time Complexity: O(nlog(n)).
     */
    public void mergeSort() {
        head = divide(head);  // updates the original linked lists head to the newly sorted head of the linked list
    }

    /**
     * Divides the linked list into two halves until a single node is present.
     */
    private Node1 divide(Node1 head) {
        // base condition
        if (head == null || head.next == null)
            return head;  // means already sorted

        Node1 mid = getMiddle(head);
        Node1 rightHead = mid.next;
        mid.next = null;

        Node1 left = divide(head);  // left half to divide
        Node1 right = divide(rightHead);  // right half to divide

        return merge(left, right);  // merge the left and right halves
    }

    /**
     * Gets the middle of the linked list by traversing through it.
     */
    private Node1 getMiddle(Node1 head) {
        Node1 slow = head;  // moves by one step
        Node1 fast = head.next;  // moves by two steps
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;  // middle of the list
    }

    /**
     * Merges the two divided halves.
     */
    private Node1 merge(Node1 left, Node1 right) {
        Node1 temp = new Node1(-1, -1);  // temporary node
        Node1 tail = temp;  // last node in the sorted list

        while (left != null && right != null) {
            if (left.weekNo <= right.weekNo) {
                tail.next = left;
                left = left.next;
            }
            else {
                tail.next = right;
                right = right.next;
            }
            tail = tail.next;
        }
        if (left != null){
            tail.next = left;
        }
        else{
            tail.next = right;
        }
        return temp.next;  // skips the head node
    }

    /**
     * Makes a deep copy of the node containing the disease name, their weeks and their case counts.
     */
    public Node makeDeepCopy(){
        Node diseaseWeekCopy = new Node(this.diseaseName);

        Node1 weekCurrentCopy = this.head;
        Node1 weekHeadCopy = null;
        Node1 weekTailCopy = null;

        while (weekCurrentCopy != null){
            Node1 newWeekNode = new Node1(weekCurrentCopy.weekNo, weekCurrentCopy.countNo);

            if (weekHeadCopy == null){
                weekHeadCopy = newWeekNode;
            }
            else{
                weekTailCopy.next = newWeekNode;
            }
            weekTailCopy = newWeekNode;
            weekCurrentCopy = weekCurrentCopy.next;
        }
        diseaseWeekCopy.head = weekHeadCopy;

        return diseaseWeekCopy;
    }
}
