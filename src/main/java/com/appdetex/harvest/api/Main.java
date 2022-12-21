package com.appdetex.harvest.api;

import com.appdetex.harvest.database.DatabaseWriter;

public class Main {

    public static void main(String[] args) throws Exception {

        DatabaseWriter databaseWriter = new DatabaseWriter();
        databaseWriter.runHarvest(5);
        System.out.println("Harvest done");

    }
}