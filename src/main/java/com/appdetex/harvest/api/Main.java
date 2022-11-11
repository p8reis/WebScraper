package com.appdetex.harvest.api;

import com.appdetex.harvest.exportToDB.DatabaseExporter;
import com.appdetex.harvest.marketplace.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException, HarvestException, URISyntaxException, InterruptedException {

        int numItems = 1;
        String term = "jacuzzi";
        DatabaseExporter dbExport = new DatabaseExporter();

        MercadoLivreBrHarvester mercadoLivreBrHarvester = new MercadoLivreBrHarvester();
        List<MarketplaceDetection> mercadoLivreBrDetections = mercadoLivreBrHarvester.parseTarget(term,numItems);

        AmazonEsHarvester amazonEsHarvester = new AmazonEsHarvester();
        List<MarketplaceDetection> amazonEsDetections = amazonEsHarvester.parseTarget(term, numItems);

        DecathlonPtHarvester decathlonPtHarvester = new DecathlonPtHarvester();
        List<MarketplaceDetection> decathlonPtDetections = decathlonPtHarvester.parseTarget(term, numItems);

        System.out.println(dbExport.exportToDb(mercadoLivreBrDetections, "Mercado Livre BR"));
        System.out.println(dbExport.exportToDb(amazonEsDetections,"Amazon ES"));
        System.out.println(dbExport.exportToDb(decathlonPtDetections,"Decathlon PT"));


    }
}
