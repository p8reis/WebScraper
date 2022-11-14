package com.appdetex.harvest.api;

import com.appdetex.harvest.marketplace.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static Integer numItems = 10;
    public static String term = "jacuzzi";
    public static void main(String[] args) throws HarvestException, SQLException, ClassNotFoundException, IOException, URISyntaxException, InterruptedException {

        Menu();
    }

    public static void Harvester(int numItems,String term) throws HarvestException {
        new AmazonEsHarvester().parseTarget(term,numItems);
        System.out.println("Amazon ES harvest is completed");
        new DecathlonPtHarvester().parseTarget(term, numItems);
        System.out.println("Decathlon PT harvest is completed");
        new MercadoLivreBrHarvester().parseTarget(term, numItems);
        System.out.println("Mercado Livre BR harvest is completed");
    }

    public static void Menu() throws HarvestException, IOException, URISyntaxException, InterruptedException {

        Scanner scanner = new Scanner(System.in);
        DatabaseUpdater performOperation = new DatabaseUpdater();

        System.out.println("Hi! Please give me your analyst ID.");
        int analystID = scanner.nextInt();

        System.out.println("Now, please choose one option from the menu:\n");
        System.out.println("1. Detections options");
        System.out.println("2. Analysts options");
        System.out.println("3. Audit options");
        System.out.println("4. Other options");

        int option = scanner.nextInt();

        switch (option){
            case 1:
                System.out.println("1. Harvest detections");
                System.out.println("2. Update detection");
                System.out.println("3. Delete detection");
                System.out.println("4. Get all detections");
                int option2= scanner.nextInt();

                switch (option2){
                    case 1:
                        Harvester(numItems, term);
                        break;

                    case 2:
                        System.out.println("Input the id of the detection you want to update");
                        int idDetection = scanner.nextInt();

                        System.out.println("1. Update state");
                        System.out.println("2. Update status");
                        System.out.println("3. Update reason code");

                        int option3= scanner.nextInt();
                        switch (option3){
                            case 1:
                                System.out.println("1. New");
                                System.out.println("2. Benign");
                                System.out.println("3. Enforce");

                                int stateOption = scanner.nextInt();

                                switch (stateOption){
                                    case 1:
                                        performOperation.updateMarketplaceDetection(analystID, idDetection, "state", "new");
                                        System.out.println("State is new");
                                        break;

                                    case 2:
                                        performOperation.updateMarketplaceDetection(analystID, idDetection, "state", "benign");
                                        System.out.println("State is benign");
                                        break;

                                    case 3:
                                        performOperation.updateMarketplaceDetection(analystID, idDetection, "state", "enforce");
                                        System.out.println("State is enforce");
                                        break;

                                    default:
                                        System.out.println("Wrong input");
                                }
                                break;

                            case 2:
                                System.out.println("1. Open");
                                System.out.println("2. Closed");

                                int statusOption = scanner.nextInt();
                                if (statusOption == 1){
                                    // change status to open
                                    performOperation.updateMarketplaceDetection(analystID, idDetection, "status", "open");
                                    System.out.println("Status is now open");
                                }
                                else if (statusOption == 2) {
                                    performOperation.updateMarketplaceDetection(analystID, idDetection, "status", "close");
                                    System.out.println("Status is now closed");
                                }
                                else{
                                    System.out.println("Wrong input");
                                    return;
                                }

                                break;

                            case 3:
                                System.out.println("1. Brand misuse");
                                System.out.println("2. Trademark infringement");
                                System.out.println("3. Phishing");
                                System.out.println("4. Fair use");

                                int reasonCodeOption = scanner.nextInt();

                                if (reasonCodeOption == 1){
                                    performOperation.updateMarketplaceDetection(analystID, idDetection, "reason code", "brand misuse");
                                    System.out.println("Reason code is now brand misuse");
                                }
                                else if (reasonCodeOption ==2){
                                    performOperation.updateMarketplaceDetection(analystID, idDetection, "reason code", "trademark infringement");
                                    System.out.println("Reason code is now trademark infringement");
                                }
                                else if (reasonCodeOption ==3){
                                    //change reason code to phishing
                                    performOperation.updateMarketplaceDetection(analystID, idDetection, "reason code", "phishing");
                                    System.out.println("Reason code is now phishing");
                                }
                                else if (reasonCodeOption ==4){
                                    //change reason code to fair use
                                    performOperation.updateMarketplaceDetection(analystID, idDetection, "reason code", "fair-use");
                                    System.out.println("Reason code is now fair use");
                                }
                                else{
                                    System.out.println("Wrong input");
                                }

                        }

                        break;
                }

                break;

            case 2:
                System.out.println("1. Delete analyst");
                System.out.println("2. New analyst");
                System.out.println("3. Update analyst");
                option2 = scanner.nextInt();
                break;

            case 3:
                System.out.println("1. Get all audits");
                break;

            case 4:
                System.out.println("Updating...");
                break;

            default:
                System.out.println("Wrong input");
        }
    }
}