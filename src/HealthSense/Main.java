package HealthSense;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Queue countryReportsHandler = new Queue();
        CountryHospitalList countryHospitalList = new CountryHospitalList();
        UndoStack undoStack = new UndoStack();
        HttpRequestService httpRequestService = new HttpRequestService();

        int reportNumber = 1;

        boolean running = true;
        while (running){

            System.out.println("----------MENU----------\n1. Add\n2. Search\n3. sort data\n4. Undo recent operation\n5. Process outbreak report\n6. View queue\n7. View BST analysis\n8. Forecast disease outbreak\n9. Exit\n------------------------\n");

            System.out.print("Enter a command: ");

            int command = -1;

            if (scanner.hasNextInt()){
                command = scanner.nextInt();
            }

            scanner.nextLine();

            switch (command){

                case 1 -> {
                    System.out.println("ADDING REPORT "+ reportNumber);

                    System.out.print("Enter the country name: ");
                    String countryName = scanner.nextLine();

                    System.out.print("Enter the week number: ");
                    int weekNo = scanner.nextInt();

                    scanner.nextLine();

                    System.out.print("Enter the no of hospitals in " + countryName + " with disease cases: ");
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

                        Hospital hospital = new Hospital(hospitalName, countryName);

                        for (int j = 0; j < diseasesNo; j++) {
                            hospital.addDisease(weekNo, diseaseNames[j], diseaseCounts[j]);
                        }

                        allHospitalDetails[i] = hospital;
                    }

                    CountryReport newcountryReport = new CountryReport(reportNumber++, countryName, weekNo, allHospitalDetails);
                    countryReportsHandler.enqueue(newcountryReport);
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
                        countryHospitalList.searchByDiseaseName(diseaseName);
                    }
                    else if (searchCommand == 2){
                        System.out.println("SEARCHING BY HOSPITAL AND PATIENT COUNT");

                        System.out.print("Enter the hospital name: ");
                        String hospitalName = scanner.nextLine();
                        System.out.print("Enter the patient count: ");
                        int patientCount = scanner.nextInt();
                        scanner.nextLine();

                        String[] similarHospitals = countryHospitalList.searchByHospitalNameAndPatientCount(hospitalName, patientCount);

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

                        System.out.print("Enter the country name: ");
                        String countryName = scanner.nextLine();
                        System.out.print("Enter the hospital name: ");
                        String hospitalName = scanner.nextLine();

                        countryHospitalList.sortDiseaseCountsPerHospital(countryName, hospitalName);
                        countryHospitalList.displayDiseaseRecordOfAHospital(countryName, hospitalName);
                    }
                    else if (sortCommand == 2){
                        System.out.println("SORTING DISEASE CASE COUNTS WEEKLY");

                        System.out.print("Enter the disease name: ");
                        String diseaseName = scanner.nextLine();

                        countryHospitalList.mergeSortToSortDiseaseCaseCountsWeekly(diseaseName);
                    }
                }

                case 4 -> {
                    System.out.println("UNDOING LATEST OPERATION");

                    Hospital[] undo = undoStack.pop();
                    if (undo != null){
                        countryHospitalList.setHospitalList(undo);
                    }
                    else{
                        System.out.println("Cannot undo operation!");
                    }
                }

                case 5 -> {
                    CountryReport processingReport = countryReportsHandler.dequeue();

                    if (processingReport != null){
                        undoStack.push(countryHospitalList.makeDeepCopy());
                        countryHospitalList.processReport(processingReport);
                        System.out.println("Report " + processingReport.reportNo + " processed successfully!");
                    }
                    else{
                        System.out.println("No reports to process!");
                    }
                }

                case 6 -> {
                    System.out.println("VIEWING REPORTS");
                    countryReportsHandler.display();
                }

                case 7 -> {
                    System.out.println("VIEWING CLASSIFIED DISEASE OUTBREAKS");

                    countryHospitalList.calculateTotalCountPerDisease();

                    System.out.println("Do you want to view:\n1. diseases in-order\n2. diseases pre-order\n3. diseases post-order\n");
                    System.out.print("Enter the view command: ");

                    int viewCommand = 0;
                    if (scanner.hasNextInt()){
                        viewCommand = scanner.nextInt();
                    }
                    scanner.nextLine();

                    if (viewCommand == 1){
                        countryHospitalList.viewBinarySearchTreeOrder(viewCommand);
                    }
                    else if (viewCommand == 2){
                        countryHospitalList.viewBinarySearchTreeOrder(viewCommand);
                    }
                    else if (viewCommand == 3){
                        countryHospitalList.viewBinarySearchTreeOrder(viewCommand);
                    }
                }

                case 8 -> {
                    System.out.println("FORECASTING DISEASE OUTBREAKS");

                    System.out.print("Enter the country name: ");
                    String countryName = scanner.nextLine();
                    System.out.print("Enter the disease name: ");
                    String diseaseName = scanner.nextLine();

                    httpRequestService.post(countryName, diseaseName);

                    String data = httpRequestService.get();

                    String obj = data.substring(1, data.length() - 1);

                    String datesPart = obj.split("\"test_dates\":\\[")[1].split("],\"test_predictions\"")[0];

                    String[] dates = datesPart.replace("\"", "").split(",");

                    // Remove T00:00:00
                    for (int i = 0; i < dates.length; i++) {
                        dates[i] = dates[i].replace("T00:00:00", "");
                    }

                    String predictionsPart = obj.split("\"test_predictions\":\\[")[1].split("]")[0];

                    String[] predictionStrings = predictionsPart.split(",");

                    int[] predictions = new int[predictionStrings.length];

                    for (int i = 0; i < predictionStrings.length; i++) {

                        double value = Double.parseDouble(predictionStrings[i]);

                        predictions[i] = (int) Math.round(value);
                    }

                    System.out.println("┌──────────────┬──────────────────────┐");
                    System.out.println("│ Date         │ Predicted New Cases  │");
                    System.out.println("├──────────────┼──────────────────────┤");
                    for (int i = 0; i < dates.length; i++) {
                        System.out.printf("│ %-12s │ %,20d │%n", dates[i], predictions[i]);
                    }
                    System.out.println("└──────────────┴──────────────────────┘");
                }

                case 9 -> running = false;

                default -> System.out.println("Not a valid command!");
            }
            System.out.println();
        }
        scanner.close();
        System.out.println("Thank you for using this application");
    }
}
