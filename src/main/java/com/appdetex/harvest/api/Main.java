package com.appdetex.harvest.api;

import com.appdetex.harvest.marketplace.*;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static Integer numItems = 5;
    public static String term = "jacuzzi";
    public static void main(String[] args) throws HarvestException, SQLException, ClassNotFoundException {

        //new AmazonEsHarvester().parseTarget(term,numItems);
        //new DecathlonPtHarvester().parseTarget(term, numItems);
        //new MercadoLivreBrHarvester().parseTarget(term, numItems);

        Menu();
    }

    public static void Harvester(int numItems,String term) throws HarvestException {
        new AmazonEsHarvester().parseTarget(term,numItems);
        new DecathlonPtHarvester().parseTarget(term, numItems);
        new MercadoLivreBrHarvester().parseTarget(term, numItems);
    }

    public static void Menu() throws HarvestException {

        System.out.println("1. Detections options");
        System.out.println("2. Analysts options");
        System.out.println("3. Audit options");
        System.out.println("4. Other options");

        Scanner scanner = new Scanner(System.in);
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
                                if (stateOption == 1){
                                    //new state
                                    //...
                                    System.out.println("State is new");
                                }
                                else if (stateOption == 2){
                                    //benign state
                                    //...
                                    System.out.println("State is benign");
                                }
                                else if (stateOption == 3){
                                    //enforce state
                                    //...
                                    System.out.println("State is enforce");
                                }
                                else{
                                    System.out.println("Wrong input");
                                }
                                break;
                            case 2:
                                System.out.println("1. Open");
                                System.out.println("2. Closed");

                                int statusOption = scanner.nextInt();
                                if (statusOption == 1){
                                    // change status to open
                                    //...
                                    System.out.println("Status is now open");
                                }
                                else if (statusOption == 2) {
                                    //change status to closed
                                    //...
                                    System.out.println("Status is now closed");
                                }
                                else{
                                    System.out.println("Wrong input");
                                }

                                break;

                            case 3:
                                System.out.println("1. Brand misuse");
                                System.out.println("2. Trademark infringement");
                                System.out.println("3. Phishing");
                                System.out.println("4. Fair use");

                                int reasonCodeOption = scanner.nextInt();

                                if (reasonCodeOption == 1){
                                    //change reason code to brand misuse
                                    //...
                                    System.out.println("Reason code is now brand misuse");
                                }
                                else if (reasonCodeOption ==2){
                                    //change reason code to trademark infringement
                                    //...
                                    System.out.println("Reason code is now trademark infringement");
                                }
                                else if (reasonCodeOption ==3){
                                    //change reason code to phishing
                                    //...
                                    System.out.println("Reason code is now phishing");
                                }
                                else if (reasonCodeOption ==4){
                                    //change reason code to fair use
                                    //...
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
