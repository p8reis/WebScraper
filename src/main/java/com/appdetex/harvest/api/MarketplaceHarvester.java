package com.appdetex.harvest.api;

import java.io.IOException;
import java.util.ArrayList;

public interface MarketplaceHarvester {


    public ArrayList<MarketplaceDetection> parseTarget(String term, int numItems) throws IOException;

}
