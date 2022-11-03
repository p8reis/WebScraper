package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

public class MarketplaceDetectionItem implements MarketplaceDetection {

    private Integer order;
    private String title;
    private String url;
    private String imageUrl;
    private String price;
    private String paidSearch;

    public MarketplaceDetectionItem(Integer order, String title, String url, String imageUrl, String price, String paidSearch) {
        this.order = order;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.price = price;
        this.paidSearch = paidSearch;
    }

    @Override
    public Integer getOrder() {
        return order;
    }

    @Override
    public String getTitle() {
        return title;
    }

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
    public String getPaidSearch() { return paidSearch; }
}
