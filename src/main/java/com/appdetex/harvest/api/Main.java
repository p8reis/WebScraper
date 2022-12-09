package com.appdetex.harvest.api;

import com.appdetex.harvest.database.DatabaseReader;
import com.appdetex.harvest.marketplace.*;

import java.util.List;

import static com.appdetex.harvest.database.DatabaseReader.getAllAccounts;
import static com.appdetex.harvest.database.DatabaseReader.getAllBrandTracks;


public class Main {

    public static Integer numItems = 5;
    public static String term = "jacuzzi";
    public static void main(String[] args) throws Exception {

        new AmazonEsHarvester().parseTarget(term,numItems);
        System.out.println("Amazon ES harvest is completed");
        new DecathlonPtHarvester().parseTarget(term, numItems);
        System.out.println("Decathlon PT harvest is completed");
        new MercadoLivreBrHarvester().parseTarget(term, numItems);
        System.out.println("Mercado Livre BR harvest is completed");

        /*for (int i = 0; i < getAllAccounts().size(); i++) {
            System.out.println(getAllAccounts().get(i).getName()); }

        getAllBrandTracks();*/

    }
}