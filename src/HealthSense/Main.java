package HealthSense;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Queue regionReportsHandler = new Queue();
        RegionHospitalList regionHospitalList = new RegionHospitalList();
        UndoStack undoStack = new UndoStack();

        int reportNumber = 1;

        boolean running = true;
        while (running){

            System.out.println("----------MENU----------\n1. Add\n2. Search\n3. sort data\n4. Undo recent operation\n5. Process outbreak report\n6. View queue\n7. View BST analysis\n8. Exit\n------------------------\n");

            System.out.print("Enter a command: ");

            int command = -1;

            if (scanner.hasNextInt()){
                command = scanner.nextInt();
            }

            scanner.nextLine();

            switch (command){

                case 1 -> {
                    System.out.println("ADDING REPORT "+ reportNumber);

                    System.out.print("Enter the region name: ");
                    String regionName = scanner.nextLine();

                    System.out.print("Enter the week number: ");
                    int weekNo = scanner.nextInt();

                    scanner.nextLine();

                    System.out.print("Enter the no of hospitals in " + regionName + " with disease cases: ");
                    int hospitalNo = scanner.nextInt();

                    scanner.nextLine();

                    Hospital[] allHospitalDetails = new Hospital[hospitalNo];

                    for (int i = 0; i < hospitalNo; i++) {

                        System.out.print("Enter the hospital name: ");
                        String hospitalName = scanner.nextLine();

                        System.out.print("Enter the number of diseases: ");
                        int diseasesNo = scanner.nextInt();

                        scanner.nextLine();

                        String[] diseaseNames = new String[diseasesNo];
                        int[] diseaseCounts = new int[diseasesNo];

                        for (int j = 0; j < diseasesNo; j++) {
                            System.out.print("Enter the disease name: ");
                            diseaseNames[j] = scanner.nextLine();

                            System.out.print("Enter the disease count: ");
                            diseaseCounts[j] = scanner.nextInt();

                            scanner.nextLine();
                        }

                        Hospital hospital = new Hospital(hospitalName, regionName);

                        for (int j = 0; j < diseasesNo; j++) {
                            hospital.addDisease(weekNo, diseaseNames[j], diseaseCounts[j]);
                        }

                        allHospitalDetails[i] = hospital;
                    }

                    RegionReport newRegionReport = new RegionReport(reportNumber++, regionName, weekNo, allHospitalDetails);
                    regionReportsHandler.enqueue(newRegionReport);
                }

                case 2 -> {
                    System.out.println("Do you want to search by:\n1. Disease name\n2. Hospital with specific patient count\n");
                    System.out.print("Enter the search command: ");

                    int searchCommand = 0;
                    if (scanner.hasNextInt()){
                        searchCommand = scanner.nextInt();
                    }
                    scanner.nextLine();

                    if (searchCommand == 1){
                        System.out.println("SEARCHING BY DISEASE NAME");

                        System.out.print("Enter the disease name: ");
                        String diseaseName = scanner.nextLine();
                        regionHospitalList.searchByDiseaseName(diseaseName);
                    }
                    else if (searchCommand == 2){
                        System.out.println("SEARCHING BY HOSPITAL AND PATIENT COUNT");

                        System.out.print("Enter the hospital name: ");
                        String hospitalName = scanner.nextLine();
                        System.out.print("Enter the patient count: ");
                        int patientCount = scanner.nextInt();
                        scanner.nextLine();

                        String[] similarHospitals = regionHospitalList.searchByHospitalNameAndPatientCount(hospitalName, patientCount);

                        for (String similarHospital : similarHospitals) {
                            if (similarHospital == null) {
                                break;
                            }
                            System.out.println(similarHospital);
                        }
                    }
                }

                case 3 -> {
                    System.out.println("Do you want to sort:\n1. Disease counts per hospital\n2. Merge Sort to sort disease case counts over time (weekly)\n");
                    System.out.print("Enter the sort command: ");

                    int sortCommand = 0;
                    if (scanner.hasNextInt()){
                        sortCommand = scanner.nextInt();
                    }
                    scanner.nextLine();

                    if (sortCommand == 1){
                        System.out.println("SORTING DISEASE COUNTS PER HOSPITAL");

                        System.out.print("Enter the region name: ");
                        String regionName = scanner.nextLine();
                        System.out.print("Enter the hospital name: ");
                        String hospitalName = scanner.nextLine();

                        regionHospitalList.sortDiseaseCountsPerHospital(regionName, hospitalName);
                        regionHospitalList.displayDiseaseRecordOfAHospital(regionName, hospitalName);
                    }
                    else if (sortCommand == 2){
                        System.out.println("SORTING DISEASE CASE COUNTS WEEKLY");

                        System.out.print("Enter the disease name: ");
                        String diseaseName = scanner.nextLine();

                        regionHospitalList.mergeSortToSortDiseaseCaseCountsWeekly(diseaseName);
                    }
                }

                case 4 -> {
                    System.out.println("UNDOING LATEST OPERATION");

                    Hospital[] undo = undoStack.pop();
                    if (undo != null){
                        regionHospitalList.setHospitalList(undo);
                    }
                    else{
                        System.out.println("Cannot undo operation!");
                    }
                }

                case 5 -> {
                    RegionReport processingReport = regionReportsHandler.dequeue();

                    if (processingReport != null){
                        undoStack.push(regionHospitalList.makeDeepCopy());
                        regionHospitalList.processReport(processingReport);
                        System.out.println("Report " + processingReport.reportNo + " processed successfully!");
                    }
                    else{
                        System.out.println("No reports to process!");
                    }
                }

                case 6 -> {
                    System.out.println("VIEWING REPORTS");
                    regionReportsHandler.display();
                }

                case 7 -> {
                    System.out.println("VIEWING CLASSIFIED DISEASE OUTBREAKS");

                    regionHospitalList.calculateTotalCountPerDisease();

                    System.out.println("Do you want to view:\n1. diseases in-order\n2. diseases pre-order\n3. diseases post-order\n");
                    System.out.print("Enter the view command: ");

                    int viewCommand = 0;
                    if (scanner.hasNextInt()){
                        viewCommand = scanner.nextInt();
                    }
                    scanner.nextLine();

                    if (viewCommand == 1){
                        regionHospitalList.viewBinarySearchTreeOrder(viewCommand);
                    }
                    else if (viewCommand == 2){
                        regionHospitalList.viewBinarySearchTreeOrder(viewCommand);
                    }
                    else if (viewCommand == 3){
                        regionHospitalList.viewBinarySearchTreeOrder(viewCommand);
                    }
                }

                case 8 -> running = false;

                default -> System.out.println("Not a valid command!");
            }
            System.out.println();
        }
        scanner.close();
        System.out.println("Thank you for using this application");
    }
}
