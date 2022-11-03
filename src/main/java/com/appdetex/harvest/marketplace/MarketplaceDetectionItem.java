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

    public MarketplaceDetectionItem(String detectionDate, String title, String description, String url, String imgUrl, int order, boolean paidSearch, double price, String currency, String seller) {
        this.detectionDate = detectionDate;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imgUrl = imgUrl;
        this.order = order;
        this.paidSearch = paidSearch;
        this.price = price;
        this.currency = currency;
        this.seller = seller;
    }

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
        return description;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public boolean isPaidSearch() {
        return paidSearch;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    public String getSeller() {
        return seller;
    }
}
