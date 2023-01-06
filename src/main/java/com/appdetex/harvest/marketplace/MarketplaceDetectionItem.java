package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

public class MarketplaceDetectionItem implements MarketplaceDetection {

    private String captureDate;
    private String marketplace;
    private Integer orderOnPage;
    private String title;
    private String description;
    private String url;
    private String imageUrl;
    private String price;
    private String paidSearch;
    private String seller;
    private String status;
    private String state;
    private Integer accountId;
    private String searchTerm;

    public MarketplaceDetectionItem(String captureDate, String marketplace, Integer orderOnPage, String title
            , String description, String url, String imageUrl, String price, String paidSearch, String seller
            , String status, String state, Integer accountId, String searchTerm) {
        this.captureDate = captureDate;
        this.marketplace = marketplace;
        this.orderOnPage = orderOnPage;
        this.title = title;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.price = price;
        this.paidSearch = paidSearch;
        this.seller = seller;
        this.status = status;
        this.state = state;
        this.accountId = accountId;
        this.searchTerm = searchTerm;
    }

    @Override
    public String getCaptureDate() { return captureDate; }

    @Override
    public String getMarketplace() { return marketplace; }

    @Override
    public Integer getOrderOnPage() {
        return orderOnPage;
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
    public String getPrice() {return price;}

    @Override
    public String getPaidSearch() { return paidSearch; }

    @Override
    public String getSeller() { return seller; }

    @Override
    public String getStatus() { return status; }

    @Override
    public String getState() { return state; }

    @Override
    public Integer getAccountId() { return accountId; }

    @Override
    public String getSearchTerm() { return searchTerm; }

}
