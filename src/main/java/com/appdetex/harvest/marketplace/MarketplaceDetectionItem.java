package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

public class MarketplaceDetectionItem implements MarketplaceDetection {

    private String captureDate;
    private Integer order;
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String price;
    private Boolean paidSearch;
    private String seller;

    public MarketplaceDetectionItem(String captureDate, Integer order, String title, String description, String url, String imageUrl, String price, Boolean paidSearch, String seller) {
        this.captureDate = captureDate;
        this.order = order;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.price = price;
        this.paidSearch = paidSearch;
        this.seller = seller;
    }

    @Override
    public String getCaptureDate() { return captureDate; }

    @Override
    public Integer getOrder() {
        return order;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() { return description; }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getPrice() {
        return price;
    }

    @Override
    public Boolean getPaidSearch() { return paidSearch; }

    @Override
    public String getSeller() { return seller; }
}
