package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MercadoLivreBrHarvester extends AbstractHarvester {

    public MercadoLivreBrHarvester() { super("https://lista.mercadolivre.com.br/%s"); }

    @Override
    protected Elements getListingElements(Document doc) {
        return doc.select("li.ui-search-layout__item");
    }

    @Override
    protected MarketplaceDetection createDetection(Element src, int idx) {

        String captureDate = getCaptureDate();
        String url = src.select(".ui-search-result__content.ui-search-link").first().attr("href");
        String imageUrl = src.select("div.slick-slide.slick-active")
                .select("img.ui-search-result-image__element.shops__image-element").first().attr("src");
        String price = src.select("span.price-tag-symbol").first().text()
                + src.select("span.price-tag-fraction").first().text();
        String title = src.select("h2.ui-search-item__title.ui-search-item__group__element.shops__items-group-details.shops__item-title").text().replace(",", " ");
        String description = getDescription(url);
        String seller = getSeller(url);
        Boolean paidSearch = url.contains("is_advertising=true");

        return new MarketplaceDetectionItem(captureDate, idx, title, description, url, imageUrl, price, paidSearch, seller);
    }

    static String getDescription(String url) {

        String description = "";
        try {
            Document src = Jsoup.connect(url).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31").get();
            description = src.select("div.ui-pdp-description > p.ui-pdp-description__content").text().replace(",", " ");
        } catch (IOException e) { e.printStackTrace(); }
        return description;
    }

    static String getSeller(String url) {

        String seller = "";
        try {
            Document src = Jsoup.connect(url).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31").get();
            String sellerUrl = src.select("a.ui-pdp-media__action.ui-box-component__action").first().attr("href");
            src = Jsoup.connect(sellerUrl).get();
            seller = src.select("div.store-info-title > h3.store-info__name").text();
        } catch (IOException e) { e.printStackTrace(); }
        return seller;
    }

}