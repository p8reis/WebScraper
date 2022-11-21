package com.appdetex.harvest.api;

import java.util.List;

public interface MarketplaceHarvester {

    /**
     * Parse the marketplace target and return the number of item requests
     * @param term the search term
     * @param numItems the number of items to return
     * @return returns detections found for the search term
     * @throws HarvestException
     */
    List<MarketplaceDetection> parseTarget(String term, int numItems) throws HarvestException, Exception;
}
