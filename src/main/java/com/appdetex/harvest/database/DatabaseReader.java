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
        HttpGet httpGet = new HttpGet("http://localhost:8008/api/marketplacedetections/getAll");
        HttpResponse httpresponse = httpclient.execute(httpGet);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        String databaseDetections = sc.nextLine();

        return databaseDetections;
    }

    public static class BrandTrack {

        private Integer id;
        private String searchTerm;
        private Integer accountId;
        public Integer getId() { return id; }
        public String getSearchTerm() { return searchTerm; }
        public Integer getAccountId() { return accountId; }

    }

    public static List<BrandTrack> getAllBrandTracks() throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8008/api/brandtracks/getAll");
        HttpResponse httpresponse = httpclient.execute(httpGet);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        String allBrandTracks = sc.nextLine();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<BrandTrack> brandTracksList = mapper.readValue(allBrandTracks, new TypeReference<List<BrandTrack>>(){});
        return brandTracksList;
    }

}
