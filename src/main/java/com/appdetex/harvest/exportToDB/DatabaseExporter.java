package com.appdetex.harvest.exportToDB;

import com.appdetex.harvest.api.MarketplaceDetection;

import java.util.List;

public class DatabaseExporter {

    private String marketplace;

    private List<MarketplaceDetection> marketplaceDetections;

    public String exportToDb(List<MarketplaceDetection> marketplaceDetections) {

        return "Export was successful";

    }
}
