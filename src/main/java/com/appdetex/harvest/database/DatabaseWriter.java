package com.appdetex.harvest.database;

import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.marketplace.AmazonEsHarvester;
import com.appdetex.harvest.marketplace.DecathlonPtHarvester;
import com.appdetex.harvest.marketplace.MercadoLivreBrHarvester;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.appdetex.harvest.database.DatabaseReader.getAllBrandTracks;

public class DatabaseWriter {
    public static Integer i;
    public static Long amazonTime;
    public static Long decathlonTime;
    public static Long mercadolivreTime;

    public void runHarvest(int numItems) throws Exception {

        StopWatch watch = new StopWatch();

        for (i = 0; i < getAllBrandTracks().size(); i++) {

            String term = getAllBrandTracks().get(i).getSearchTerm();

            watch.start();
            postToDatabase(new AmazonEsHarvester().parseTarget(term, numItems));
            watch.stop();
            amazonTime = watch.getTime(TimeUnit.SECONDS);
            watch.reset();
            System.out.println("AmazonES harvest finished for brand track \"" + term + "\" in " + amazonTime + " seconds.");

            watch.start();
            postToDatabase(new DecathlonPtHarvester().parseTarget(term, numItems));
            watch.stop();
            decathlonTime = watch.getTime(TimeUnit.SECONDS);
            watch.reset();
            System.out.println("DecathlonPT harvest finished for brand track \"" + term + "\" in " + decathlonTime + " seconds.");

            watch.start();
            postToDatabase(new MercadoLivreBrHarvester().parseTarget(term, numItems));
            watch.stop();
            mercadolivreTime = watch.getTime(TimeUnit.SECONDS);
            watch.reset();
            System.out.println("MercadoLivreBR harvest finished for brand track \"" + term + "\" in " + mercadolivreTime + " seconds.");
        }
    }

    public static void postToDatabase(List<MarketplaceDetection> detections) throws Exception {

        for (int d = 0; d < detections.size(); d++) {
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost("http://localhost:8008/api/detections/create");
                ObjectMapper mapper = new ObjectMapper();
                String jsonString = mapper.writeValueAsString(detections.get(d));
                System.out.println(jsonString);
                StringEntity entity = new StringEntity(jsonString, "UTF-8");
                httpPost.setEntity(entity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
                CloseableHttpResponse response = client.execute(httpPost);
            }
        }
    }
}
