package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

public class MarketplaceDetectionItem implements MarketplaceDetection {

    private String detectionDate;
    private String title;
    private String description;
    private String url;
    private String imgUrl;
    private int order;
    private boolean paidSearch;
    private double price;
    private String currency;
    private String seller;






    @Override
    public String getDetectionDate() {
        return detectionDate;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getImgUrl() {
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Boolean isPaidSearch() {
        return null;
    }

    @Override
    public Double getPrice() {
        return null;
    }

    @Override
    public String getCurrency() {
        return null;
    }

    @Override
    public String getSeller() {
        return null;
    }
}
