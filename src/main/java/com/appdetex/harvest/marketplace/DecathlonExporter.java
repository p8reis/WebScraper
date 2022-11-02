package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DecathlonExporter {

    private ArrayList<MarketplaceDetection> detections;

    public DecathlonExporter(ArrayList<MarketplaceDetection> detections) {
        this.detections = detections;
    }

    public void exportDecathlon() throws IOException {

        // Initializes the file where the detections will be saved
        String filePath = "/Users/pedro.reis/Desktop/DetectionsOutput/Detections.csv";
        FileWriter csvFile = new FileWriter(filePath, true);
        PrintWriter detectionsFile = new PrintWriter(csvFile);

        // Writing the header on the file (if it was not written before)
        if (!isHeaderPrinted(filePath)) {
            detectionsFile.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n","detectionDate","productTitle","productDescription",
                    "productPrice","productCurrency","prodImageUrl","productUrl","paidSearch", "orderOnPage", "productSeller");
        }

        for (MarketplaceDetection detection: detections) {
            String detectionDate = detection.getDetectionDate();
            String productTitle = detection.getTitle();
            String productDescription = detection.getDescription();
            Double productPrice = detection.getPrice();
            String productCurrency = detection.getCurrency();
            String prodImgUrl = detection.getImgUrl();
            String productUrl = detection.getUrl();
            boolean paidSearch = detection.isPaidSearch();
            int orderOnPage = detection.getOrder();
            String productSeller = detection.getSeller();

            detectionsFile.printf("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\n",detectionDate,productTitle,productDescription,
                    productPrice,productCurrency,prodImgUrl,productUrl,paidSearch, orderOnPage, productSeller);

        }

        detectionsFile.close();
    }

    private static boolean isHeaderPrinted(String filepath) {

        boolean hasHeader = false;

        try {
            File csv = new File(filepath);
            Scanner detections = new Scanner(csv);

            if (detections.hasNextLine()) {
                hasHeader = true;
            }

            detections.close();

        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        return (hasHeader);

    }
}
