package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.api.MarketplaceHarvester;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AmazonHarvester implements MarketplaceHarvester {

    @Override
    public List<MarketplaceDetection> parseTarget(String term, int numItems) throws HarvestException {

        Document doc = null;
        try {
            String targetUrl = String.format("https://www.amazon.es/s?k=%s", term);
            doc = Jsoup.connect(targetUrl).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31").get();
            return parseTargetInternal(doc, numItems);
        } catch (IOException e) {
            e.printStackTrace();
            throw new HarvestException();
        }
    }

    protected List<MarketplaceDetection> parseTargetInternal(Document doc, int numItems) throws HarvestException {

        ArrayList<MarketplaceDetection> detections = new ArrayList<>();

            Elements items = doc.select("div.s-main-slot div.sg-col-4-of-12");
            int idx = 0;
            for (Element src : items) {
                String imageUrl = src.select("img.s-image").attr("src");
                String price = src.select("span.a-price-whole").text() +
                        src.select("span.a-price-symbol").text();
                String title = src.select("span.a-size-base-plus").text();
                String url = "https://www.amazon.es" + src.select("a").attr("href");
                String paidSearch = src.select("span.s-label-popover-default span.a-color-secondary").text();

                detections.add(new MarketplaceDetectionItem(++idx, title, url, imageUrl, price, paidSearch));
                if (idx == 10) break;
            }
            return detections;
    }
}
