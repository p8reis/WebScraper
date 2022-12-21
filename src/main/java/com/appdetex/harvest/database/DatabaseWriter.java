package com.appdetex.harvest.database;

import com.appdetex.harvest.marketplace.AmazonEsHarvester;
import com.appdetex.harvest.marketplace.DecathlonPtHarvester;
import com.appdetex.harvest.marketplace.MercadoLivreBrHarvester;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import static com.appdetex.harvest.database.DatabaseReader.getAllBrandTracks;

public class DatabaseWriter {
    public static Integer it;

    public void runHarvest(int numItems) throws Exception {

        DatabaseReader databaseReader = new DatabaseReader();

        for (it = 0; it < getAllBrandTracks().size(); it++) {
            String term = getAllBrandTracks().get(it).getSearchTerm();
            new AmazonEsHarvester().parseTarget(term, numItems);
            System.out.println("AmazonES harvest finished for brand track: " + term);
            new DecathlonPtHarvester().parseTarget(term, numItems);
            System.out.println("DecathlonPT harvest finished for brand track: " + term);
            new MercadoLivreBrHarvester().parseTarget(term, numItems);
            System.out.println("MercadoLivreBR harvest finished for brand track: " + term);
        }
    }

    public static void postToDatabase(String captureDate, String marketplace, Integer idx, String title, String description
            , String url, String imageUrl, String price, String seller, String paidSearch) throws Exception {

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost("http://localhost:8008/api/detections/create");
            String json = "{\"captureDate\":\"" + captureDate
                    + "\",\"marketplace\":\"" + marketplace
                    + "\",\"orderOnPage\":\"" + idx
                    + "\",\"title\":\"" + title
                    + "\",\"description\":\"" + description
                    + "\",\"url\":\"" + url
                    + "\",\"imageUrl\":\"" + imageUrl
                    + "\",\"price\":\"" + price
                    + "\",\"seller\":\"" + seller
                    + "\",\"paidSearch\":\"" + paidSearch
                    + "\",\"status\":\"open"
                    + "\",\"state\":\"new"
                    + "\",\"accountId\":\"" + getAllBrandTracks().get(it).getAccountId()
                    + "\",\"searchTerm\":\"" + getAllBrandTracks().get(it).getSearchTerm() + "\"}";
            System.out.println(json);
            StringEntity entity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
            CloseableHttpResponse response = client.execute(httpPost);
        }
    }

}
