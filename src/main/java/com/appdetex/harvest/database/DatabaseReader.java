package com.appdetex.harvest.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Scanner;

public class DatabaseReader {

    public static String getAll() throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8081/api/marketplacedetections/getAll");
        HttpResponse httpresponse = httpclient.execute(httpGet);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        String databaseDetections = sc.nextLine();

        return databaseDetections;
    }

    public static class Account {
        private Integer id;
        private String name;

        public String getName() { return name; }

        public Integer getId() { return id; }
    }

    public static List<Account> getAllAccounts() throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8081/api/account/getAll");
        HttpResponse httpresponse = httpclient.execute(httpGet);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        String allAccounts = sc.nextLine();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Account> accountList = mapper.readValue(allAccounts, new TypeReference<List<Account>>(){});

        for (int i = 0; i < accountList.size(); i++) {
            System.out.println(accountList.get(i).getId());
            System.out.println(accountList.get(i).getName());
        }

        return accountList;
    }

    public static class BrandTrack {
        private Integer id;
        private String searchTerm;
        private Integer accountId;

        public String getSearchTerm() { return searchTerm; }
        public Integer getId() { return id; }
        public Integer getAccountId() { return accountId; }
    }

    public static List<BrandTrack> getAllBrandTracks() throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8081/api/brandtracks/getAll");
        HttpResponse httpresponse = httpclient.execute(httpGet);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        String allBrandTracks = sc.nextLine();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<BrandTrack> brandTracksList = mapper.readValue(allBrandTracks, new TypeReference<List<BrandTrack>>(){});

        for (int i = 0; i < brandTracksList.size(); i++) {
            System.out.println(brandTracksList.get(i).getId());
            System.out.println(brandTracksList.get(i).getSearchTerm());
            System.out.println(brandTracksList.get(i).getAccountId());
        }

        return brandTracksList;
    }

}
