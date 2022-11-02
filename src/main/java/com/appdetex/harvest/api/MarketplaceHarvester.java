package com.appdetex.harvest.api;

import java.util.List;

public interface MarketplaceHarvester {

        List<MarketplaceDetection> parseTarget(String term, int numItems);
}
