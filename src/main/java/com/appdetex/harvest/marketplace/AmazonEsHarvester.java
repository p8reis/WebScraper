package com.appdetex.harvest.marketplace;

public class AmazonEsHarvester extends AbstractAmazonHarvester {

    public AmazonEsHarvester() {
        super("https://www.amazon.es/s?k=%s");
    }

}
