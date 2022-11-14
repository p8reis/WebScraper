package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.api.MarketplaceHarvester;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractHarvester implements MarketplaceHarvester {

    private String baseUrl;

    public AbstractHarvester(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public List<MarketplaceDetection> parseTarget(String term, int numItems) throws HarvestException {

        Document doc = null;
        try {
            String targetUrl = String.format(this.baseUrl, term);
            doc = Jsoup.connect(targetUrl).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31").get();
            return parseTargetInternal(doc, numItems);
        } catch (IOException e) {
            e.printStackTrace();
            throw new HarvestException();
        }
    }

    protected final List<MarketplaceDetection> parseTargetInternal(Document doc, int numItems) throws HarvestException, IOException {

        ArrayList<MarketplaceDetection> detections = new ArrayList<>();

        Elements items = getListingElements(doc);
        int idx = 0;
        for (Element src : items) {
            if (idx == numItems) break;
            detections.add(createDetection(src, ++idx));
        }
        return detections;
    }

    protected abstract Elements getListingElements(Document doc);

    protected abstract MarketplaceDetection createDetection(Element src, int idx) throws IOException;

    public String getCaptureDate() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String captureDate = dtf.format(LocalDateTime.now());
        return captureDate;
    }

    public void exportToDatabase(String captureDate, String marketplace, Integer idx, String title, String description
            , String url, String imageUrl, String price, String seller, String paidSearch) throws IOException {

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost("http://localhost:8081/api/marketplacedetections/create");
            String json = "{\n\"capture_date\" : \"" + captureDate + "\",\n\"marketplace\" : \"" + marketplace
                    + "\",\n\"order_on_page\" : \"" + idx + "\",\n\"title\" : \"" + title + "\",\n\"description\" : \""
                    + description + "\",\n\"url\" : \"" + url + "\",\n\"image_url\" : \"" + imageUrl
                    + "\",\n\"price\" : \"" + price + "\",\n\"seller\" : \"" + seller + "\",\n\"paid_search\" : \""
                    + paidSearch + "\",\n\"status\" : \"open\",\n\"state\" : \"new\"\n}";
            StringEntity entity = new StringEntity(json, "UTF-8");
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
            CloseableHttpResponse response = client.execute(httpPost);
        }
    }

}
