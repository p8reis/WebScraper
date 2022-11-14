package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.HarvestException;
import com.appdetex.harvest.api.MarketplaceDetection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

abstract class AbstractHarvesterTest {

    private String expectedHtml;
    private AbstractHarvester harvester;

    public AbstractHarvesterTest(AbstractHarvester harvester, String expectedHtml) {
        this.harvester = harvester;
        this.expectedHtml = expectedHtml;
    }

    @ParameterizedTest
    @ValueSource (ints = {0, 5, 10, 20})
    void testWithExpectedResults(int numResults) {
        List<MarketplaceDetection> detections = null;
        try {
            Document doc = getHtml(expectedHtml);
            detections = harvester.parseTargetInternal(doc, numResults);
            Assertions.assertNotNull(detections);
            Assertions.assertTrue(detections.size() <= numResults, "Expecting " + numResults);
        } catch (HarvestException e) { throw new RuntimeException(e);
        } catch (IOException e) { throw new RuntimeException(e); }
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