package HealthSense;

/**
 * Represents a Hospital that holds all hospital details.
 */
public class Hospital {
    public String hospitalName;
    public String hospitalRegion;
    public int totalPatientCount;
    public DiseaseRecord diseaseList;  // A linked list which holds diseases of a hospital

    /**
     * Constructs a new Hospital containing the given hospital name and region.
     */
    public Hospital(String hospitalName, String hospitalRegion) {
        this.hospitalName = hospitalName;
        this.hospitalRegion = hospitalRegion;
        this.diseaseList = new DiseaseRecord();
    }

    /**
     * Adds a new disease containing the given week number, disease name and disease count.
     */
    public void addDisease(int weekNo, String diseaseName, int diseaseCount) {
        diseaseList.add(weekNo, diseaseName, diseaseCount);
        totalPatientCount += diseaseCount;
    }

    /**
     * Sorts all diseases in a hospital by their total case count in descending order.
     */
    public void sortDiseasesPerCount(){
        diseaseList.sortDiseasesPerCount();
    }

    /**
     * Displays all diseases in a hospital and their total case count.
     */
    public void displayDiseaseRecord(){
        diseaseList.display();
    }

    /**
     * Searches a disease in a hospital by their given name.
     */
    public int searchByDiseaseName(String diseaseName){
        return diseaseList.searchByDiseaseName(diseaseName);
    }

    /**
     * Sorts the weeks of a given disease in a hospital in ascending order.
     */
    public TrendNode sortDiseaseCaseCountsWeekly(String diseaseName, TrendNode head){
        return diseaseList.sortDiseaseCaseCountsWeekly(diseaseName, head);
    }

    /**
     * Calculates the total case counts of a disease in a hospital.
     */
    public DiseaseCountNode calculateTotalCountPerDisease(DiseaseCountNode diseaseCountHead){
        return diseaseList.calculateTotalCountPerDisease(diseaseCountHead);
    }

    /**
     * Makes a deep copy of the hospital.
     */
    public Hospital makeDeepCopy(){
        Hospital hospitalCopy = new Hospital(this.hospitalName, this.hospitalRegion);
        hospitalCopy.totalPatientCount = this.totalPatientCount;
        hospitalCopy.diseaseList = this.diseaseList.makeDeepCopy();
        return hospitalCopy;
    }
}
