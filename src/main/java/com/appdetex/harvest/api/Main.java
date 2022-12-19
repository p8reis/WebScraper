package com.appdetex.harvest.api;

import com.appdetex.harvest.database.DatabaseWriter;

import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {

        try {
            System.out.println("Time is "+ new Date());
            DatabaseWriter databaseWriter = new DatabaseWriter();
            databaseWriter.runHarvest(10);
            System.out.println("Harvest done");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}