package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.api.MarketplaceHarvester;

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
    public List<MarketplaceDetection> parseTarget(String term, int numItems) throws Exception {

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

    protected final List<MarketplaceDetection> parseTargetInternal(Document doc, int numItems) throws Exception {

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

    protected abstract MarketplaceDetection createDetection(Element src, int idx) throws Exception;

    public String getCaptureDate() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String captureDate = dtf.format(LocalDateTime.now());
        return captureDate;
    }
}
