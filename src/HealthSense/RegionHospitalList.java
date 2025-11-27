package HealthSense;

/**
 * Represents a list of hospitals that holds hospital details.
 */
public class RegionHospitalList {
    Hospital[] hospitalList = new Hospital[2];
    int hospitalIndex = 0;  // the number of hospitals

    public TrendNode trendHead;  // Used for sorting disease case counts weekly
    public DiseaseCountNode diseaseCountHead;

    public BinarySearchTree binarySearchTree;

    /**
     * Checks if the hospital list is full.
     */
    public boolean isFull(){
        return (hospitalIndex == hospitalList.length);
    }

    /**
     * Checks if hospital exist in the list, if exist returns that hospital, else returns a new hospital
     */
    public Hospital checkHospitalExist(String hospitalRegion, String hospitalName){
        for (int i = 0; i < hospitalIndex; i++) {
            if (hospitalList[i].hospitalName.equalsIgnoreCase(hospitalName) && hospitalList[i].hospitalRegion.equalsIgnoreCase(hospitalRegion)){
                return hospitalList[i];
            }
        }
        if (isFull()){
            resize();
        }
        hospitalList[hospitalIndex] = new Hospital(hospitalName, hospitalRegion);
        return hospitalList[hospitalIndex++];
    }

    /**
     * Sorts the diseases according to their counts of a specified hospital.
     */
    public void sortDiseaseCountsPerHospital(String hospitalRegion, String hospitalName){
        for (int i = 0; i < hospitalIndex; i++) {
            if (hospitalList[i].hospitalName.equalsIgnoreCase(hospitalName) && hospitalList[i].hospitalRegion.equalsIgnoreCase(hospitalRegion)){
                hospitalList[i].sortDiseasesPerCount();
                break;
            }
        }
    }

    /**
     * Displays the diseases and their counts of a specified hospital.
     */
    public void displayDiseaseRecordOfAHospital(String hospitalRegion, String hospitalName){
        for (int i = 0; i < hospitalIndex; i++) {
            if (hospitalList[i].hospitalName.equalsIgnoreCase(hospitalName) && hospitalList[i].hospitalRegion.equalsIgnoreCase(hospitalRegion)){
                hospitalList[i].displayDiseaseRecord();
            }
        }
    }

    /**
     * Searches a disease of a specified hospital.
     * Prints the hospital region, hospital name and the disease's total count
     */
    public void searchByDiseaseName(String diseaseName){
        System.out.println("Disease: " + diseaseName);
        for (int i = 0; i < hospitalIndex; i++) {
            System.out.println(hospitalList[i].hospitalRegion + " " + hospitalList[i].hospitalName + " : " + hospitalList[i].searchByDiseaseName(diseaseName));
        }
    }

    /**
     * Searches a hospital name and total patient count of a specified hospital.
     * Returns a list of hospitals matching the given filter.
     */
    public String[] searchByHospitalNameAndPatientCount(String hospitalName, int patientCount){
        String[] similarHospitals = new String[hospitalList.length];
        int index = 0;
        for (int i = 0; i < hospitalIndex; i++) {
            if (hospitalList[i].hospitalName.equalsIgnoreCase(hospitalName) && hospitalList[i].totalPatientCount == patientCount){
                similarHospitals[index++] = hospitalList[i].hospitalRegion + " " + hospitalList[i].hospitalName + " hospital with " + hospitalList[i].totalPatientCount + " patients found!";
            }
        }
        if (index == 0){
            similarHospitals[index] = "No hospital was found with hospital name " + hospitalName + " having " + patientCount + " patients!";
            return similarHospitals;
        }
        return similarHospitals;
    }

    /**
     * Makes a deep copy of the hospital list.
     */
    public Hospital[] makeDeepCopy(){
        Hospital[] hospitalListCopy = new Hospital[hospitalList.length];
        for (int i = 0; i < hospitalIndex; i++) {
            hospitalListCopy[i] = hospitalList[i].makeDeepCopy();
        }
        return hospitalListCopy;
    }

    /**
     * Sets this hospital list to the given hospital list.
     */
    public void setHospitalList(Hospital[] hospitalList){
        this.hospitalList = hospitalList;
        this.hospitalIndex = checkLastIndex(hospitalList);
    }

    /**
     * Returns the last index of the hospital in the hospital list.
     */
    public int checkLastIndex(Hospital[] hospitalList){
        for (int i = 0; i < hospitalList.length; i++) {
            if (hospitalList[i] == null){
                return i;
            }
        }
        return 0;
    }

    /**
     * Processes a report and adds their given details.
     */
    public void processReport(RegionReport report){
        for (Hospital tempHospital : report.hospitalDetails) {
            Hospital hospital = checkHospitalExist(tempHospital.hospitalRegion, tempHospital.hospitalName);

            Node diseaseNode = tempHospital.diseaseList.getHead();
            while (diseaseNode != null){
                Node1 weekCountNode = diseaseNode.head;
                while (weekCountNode != null){
                    hospital.addDisease(weekCountNode.weekNo, diseaseNode.diseaseName, weekCountNode.countNo);
                    weekCountNode = weekCountNode.next;
                }
                diseaseNode = diseaseNode.next;
            }
        }
    }

    /**
     * Sorts the disease case count weekly of the specified disease.
     * Prints the weekly trend of the disease.
     */
    public void mergeSortToSortDiseaseCaseCountsWeekly(String diseaseName){
        trendHead = null;
        for (int i = 0; i < hospitalIndex; i++) {
            // have to get the total here per week.
            trendHead = hospitalList[i].sortDiseaseCaseCountsWeekly(diseaseName, trendHead);
        }

        System.out.println("Disease: " + diseaseName);
        System.out.println("Combined Weekly Trend: ");
        TrendNode current = trendHead;
        while (current != null){
            System.out.println(" → Week " + current.weekNo + ": " + current.totalCount + " cases");
            current = current.next;
        }
    }

    /**
     * Calculates the total disease counts per disease of all hospitals in the hospital list.
     */
    public void calculateTotalCountPerDisease(){
        diseaseCountHead = null;
        for (int i = 0; i < hospitalIndex; i++) {
            diseaseCountHead = hospitalList[i].calculateTotalCountPerDisease(diseaseCountHead);
        }
        insertDataToBinarySearchTreeNodes();
    }

    /**
     * Inserts new diseases and their counts into the BST.
     */
    public void insertDataToBinarySearchTreeNodes(){
        binarySearchTree = new BinarySearchTree();

        DiseaseCountNode current = diseaseCountHead;
        while (current != null){
            binarySearchTree.insert(current.diseaseName, current.totalCount);
            current = current.next;
        }
    }

    /**
     * Views the BST using a specified traversal order.
     */
    public void viewBinarySearchTreeOrder(int order){
        binarySearchTree.outputOrder(order);
    }

    /**
     * Resizes the hospital list if the hospital list is full.
     */
    public void resize(){
        Hospital[] resizedHospitalList = new Hospital[hospitalList.length * 2];
        System.arraycopy(hospitalList, 0, resizedHospitalList, 0, hospitalList.length);
        hospitalList = resizedHospitalList;
    }
}
