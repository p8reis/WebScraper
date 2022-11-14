package com.appdetex.harvest.api;

import com.appdetex.harvest.marketplace.*;
import java.sql.SQLException;

public class Main {

    public static Integer numItems = 5;
    public static String term = "jacuzzi";
    public static void main(String[] args) throws HarvestException, SQLException, ClassNotFoundException {

        new AmazonEsHarvester().parseTarget(term,numItems);
        new DecathlonPtHarvester().parseTarget(term, numItems);
        new MercadoLivreBrHarvester().parseTarget(term, numItems);
    }
}
