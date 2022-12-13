package com.appdetex.harvest.api;

import com.appdetex.harvest.database.DatabaseWriter;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

public class Main {

    public static void main(String[] args) throws Exception {
        /*
        try {
            JobDetail jobEveryDay = JobBuilder.newJob(CronJob.class).withIdentity("jobEveryDay", "group1").build();
            Trigger triggerEveryDay = TriggerBuilder.newTrigger().withIdentity("triggerEveryDay", "group1").
                    withSchedule(CronScheduleBuilder.cronSchedule("0 * * ? * * *")).build(); // everyday is 0 0 0 ? * * *
            Scheduler schedulerEveryDay = new StdSchedulerFactory().getScheduler();
            schedulerEveryDay.start();
            schedulerEveryDay.scheduleJob(jobEveryDay, triggerEveryDay);
        }
        catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
         */
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