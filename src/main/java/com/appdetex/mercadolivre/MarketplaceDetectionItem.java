package com.appdetex.mercadolivre;

import com.appdetex.harvest.api.MarketplaceDetection;

public class MarketplaceDetectionItem implements MarketplaceDetection {

    private String order;

    private String title;

    private String url;

    private String imageUrl;

    private String description;

    private String paid;

    private String price;

    public MarketplaceDetectionItem(String order, String title, String url, String imageUrl, String description, String paid, String price) {
        this.order = order;
        this.title = title;
        this.url = url;
        this.imageUrl = imageUrl;
        this.description = description;
        this.paid = paid;
        this.price = price;
    }

    @Override
    public String getOrder() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getProdLink() {
        return null;
    }

    @Override
    public String getImageLink() {
        return null;
    }

    @Override
    public String getPrice() {
        return null;
    }

    @Override
    public String description() {
        return null;
    }

    @Override
    public Boolean isPaid() {
        return null;
    }
}
