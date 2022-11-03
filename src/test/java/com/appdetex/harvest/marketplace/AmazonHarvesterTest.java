package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

class AmazonHarvesterTest {

    @Test
    void testWithExpectedResults() {
        AmazonHarvester harvest = new AmazonHarvester();
        List<MarketplaceDetection> detections = null;
        try {
            Document doc = getHtml("/amazon.html");
            detections = harvest.parseTargetInternal(doc, 10); //27.03
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() <= 10, "Expecting 10 or less!");
        } catch (HarvestException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testEmptyCase() {
        AmazonHarvester harvest = new AmazonHarvester();
        List<MarketplaceDetection> detections = null;
        try {
            Document doc = getHtml("/amazon-empty.html");
            detections = harvest.parseTargetInternal(doc, 10);
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() == 0, "Expecting no items!");
        } catch (HarvestException e) {
            throw new RuntimeException(e);
        }
    }

    private Document getHtml(String fileName) {
        File html = getResourceHtml(fileName);
        try {
            return Jsoup.parse(html);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getResourceHtml(String fileName) {
        URL url = this.getClass().getResource(fileName);
        try {
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


}