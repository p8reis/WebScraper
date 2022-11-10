package com.appdetex.harvest.api;

import com.appdetex.harvest.marketplace.*;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, HarvestException {
        int numItems = 10;
        String term = "jacuzzi";

        System.out.println("Step1");

        MercadoLivreBrHarvester mercadoLivreBrHarvester = new MercadoLivreBrHarvester();
        mercadoLivreBrHarvester.parseTarget(term,numItems);

        System.out.println("Step2");

        AmazonEsHarvester amazonEsHarvester = new AmazonEsHarvester();
        amazonEsHarvester.parseTarget(term, numItems);

        System.out.println("Step3");

        AmazonUsHarvester amazonUsHarvester = new AmazonUsHarvester();
        amazonUsHarvester.parseTarget(term, numItems);

        System.out.println("Step4");

        DecathlonPtHarvester decathlonPtHarvester = new DecathlonPtHarvester();
        decathlonPtHarvester.parseTarget(term, numItems);
    }
}
