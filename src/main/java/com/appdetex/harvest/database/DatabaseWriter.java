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

    public static Integer numItems = 5;
    public static Integer  it;
    public void runHarvest() throws Exception {
        for (it = 0; it < getAllBrandTracks().size(); it++) {
            String term = getAllBrandTracks().get(it).getSearch_term();
            new AmazonEsHarvester().parseTarget(term, numItems);
            System.out.println("Amazon ES harvest is completed");
            new DecathlonPtHarvester().parseTarget(term, numItems);
            System.out.println("Decathlon PT harvest is completed");
            new MercadoLivreBrHarvester().parseTarget(term, numItems);
            System.out.println("Mercado Livre BR harvest is completed");
        }
    }

    public static void postToDatabase(String captureDate, String marketplace, Integer idx, String title, String description
            , String url, String imageUrl, String price, String seller, String paidSearch) throws Exception {


        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost("http://localhost:8008/api/marketplacedetections/create");
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
                    + "\",\"status\":\"open\",\"state\":\"new\",\"account_id\":\""
                    + getAllBrandTracks().get(it).getAccountId() + "\"}";
            StringEntity entity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
            CloseableHttpResponse response = client.execute(httpPost);
        }
    }

}
