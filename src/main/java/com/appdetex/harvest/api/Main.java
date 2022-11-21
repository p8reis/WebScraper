package com.appdetex.harvest.api;

import com.appdetex.harvest.marketplace.*;

public class Main {

    public static Integer numItems = 10;
    public static String term = "jacuzzi";
    public static void main(String[] args) throws Exception {

        new AmazonEsHarvester().parseTarget(term,numItems);
        System.out.println("Amazon ES harvest is completed");
        new DecathlonPtHarvester().parseTarget(term, numItems);
        System.out.println("Decathlon PT harvest is completed");
        new MercadoLivreBrHarvester().parseTarget(term, numItems);
        System.out.println("Mercado Livre BR harvest is completed");

    }
}