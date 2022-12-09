package com.appdetex.harvest.database;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DatabaseWriter {

    public static void postToDatabase(String captureDate, String marketplace, Integer idx, String title, String description
            , String url, String imageUrl, String price, String seller, String paidSearch) throws Exception {

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost("http://localhost:8081/api/marketplacedetections/create");
            String json = "{\"capture_date\":\"" + captureDate
                    + "\",\"marketplace\":\"" + marketplace
                    + "\",\"order_on_page\":\"" + idx
                    + "\",\"title\":\"" + title
                    + "\",\"description\":\"" + description
                    + "\",\"url\":\"" + url
                    + "\",\"image_url\":\"" + imageUrl
                    + "\",\"price\":\"" + price
                    + "\",\"seller\":\"" + seller
                    + "\",\"paid_search\":\"" + paidSearch
                    + "\",\"status\":\"open\",\"state\":\"new\",\"account_id\":1}";
            StringEntity entity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
            CloseableHttpResponse response = client.execute(httpPost);
        }
    }

}
