package com.appdetex.harvest.api;

import com.appdetex.harvest.marketplace.DecathlonExporter;
import com.appdetex.harvest.marketplace.DecathlonHarvester;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        DecathlonHarvester harvest = new DecathlonHarvester();
        ArrayList<MarketplaceDetection> detections = harvest.parseTarget("jacuzzi",10);

        DecathlonExporter export = new DecathlonExporter(detections);
        export.exportDecathlon();

    }
}