package com.appdetex.harvest.database;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Scanner;

public class DatabaseExporter {

    public static String[] getAll() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8081/api/marketplacedetections/getAll");
        HttpResponse httpresponse = httpclient.execute(httpGet);
        Scanner sc = new Scanner(httpresponse.getEntity().getContent());
        String entireDatabase = sc.nextLine();
        entireDatabase = entireDatabase
                .replace("[", "")
                .replace("]","")
                .replace("{", "");
        String databaseDetections[] = entireDatabase.split("},");

        return databaseDetections;
    }

    public static void postToDatabase(String captureDate, String marketplace, Integer idx, String title, String description
            , String url, String imageUrl, String price, String seller, String paidSearch) throws Exception {

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost("http://localhost:8081/api/marketplacedetections/create");
            String json = "{\"capture_date\":\"" + captureDate + "\",\"marketplace\":\"" + marketplace
                    + "\",\"order_on_page\":\"" + idx + "\",\"title\":\"" + title + "\",\"description\":\""
                    + description + "\",\"url\":\"" + url + "\",\"image_url\":\"" + imageUrl
                    + "\",\"price\":\"" + price + "\",\"seller\":\"" + seller + "\",\"paid_search\":\""
                    + paidSearch + "\",\"status\":\"open\",\"state\":\"new\"}";
            String jsonCompare = json.replace("{", "").replace("}", "");
            StringEntity entity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
            CloseableHttpResponse response = client.execute(httpPost);

        }
    }

}
