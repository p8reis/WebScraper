package com.appdetex.harvest.database;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class UserAgents {

    private ArrayList<String> userAgentsList = new ArrayList<>();

    public UserAgents() throws IOException {
        Scanner txtFile = new Scanner(new File("userAgents.txt"));
        while (txtFile.hasNextLine()) {
            String line = txtFile.nextLine();
            userAgentsList.add(line);
        }
    }

    public String getRandomUserAgent() throws Exception {

        int randomIndex = ThreadLocalRandom.current().nextInt(0, userAgentsList.size());
        return userAgentsList.get(randomIndex);
    }
}
