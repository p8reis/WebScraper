package com.appdetex.harvest.api;

import com.appdetex.harvest.marketplace.*;

import static com.appdetex.harvest.database.DatabaseReader.getAllBrandTracks;


public class Main {

    public static Integer numItems = 1;

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < getAllBrandTracks().size(); i++) {
            String term = getAllBrandTracks().get(i).getSearch_term();
            new AmazonEsHarvester().parseTarget(term,numItems);
            System.out.println("Amazon ES harvest is completed");
            new DecathlonPtHarvester().parseTarget(term, numItems);
            System.out.println("Decathlon PT harvest is completed");
            new MercadoLivreBrHarvester().parseTarget(term, numItems);
            System.out.println("Mercado Livre BR harvest is completed");
        }


    }
}