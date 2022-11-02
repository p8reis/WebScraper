package com.appdetex.harvest.api;

public interface MarketplaceDetection {

    String getDetectionDate();

    String getTitle();

    String getDescription();

    String getUrl();

    String getImgUrl();

    int getOrder();

    boolean isPaidSearch();

    Double getPrice();

    String getCurrency();

    String getSeller();

}
