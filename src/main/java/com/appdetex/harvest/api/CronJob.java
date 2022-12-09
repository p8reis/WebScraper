package com.appdetex.harvest.api;

import com.appdetex.harvest.database.DatabaseWriter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class CronJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            System.out.println("Time is "+ new Date());
            DatabaseWriter databaseWriter = new DatabaseWriter();
            databaseWriter.runHarvest();
            System.out.println("Harvest done");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
