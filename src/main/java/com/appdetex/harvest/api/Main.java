package com.appdetex.harvest.api;

import com.appdetex.harvest.marketplace.*;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, HarvestException {

        int numItems = 10;
        String term = "jacuzzi";

        MercadoLivreBrHarvester mercadoLivreBrHarvester = new MercadoLivreBrHarvester();
        mercadoLivreBrHarvester.parseTarget(term,numItems);

        AmazonEsHarvester amazonEsHarvester =new AmazonEsHarvester();
        amazonEsHarvester.parseTarget(term, numItems);

        AmazonUsHarvester amazonUsHarvester =new AmazonUsHarvester();
        amazonUsHarvester.parseTarget(term, numItems);

        DecathlonPtHarvester decathlonPtHarvester = new DecathlonPtHarvester();
        decathlonPtHarvester.parseTarget(term, numItems);





    }
}
