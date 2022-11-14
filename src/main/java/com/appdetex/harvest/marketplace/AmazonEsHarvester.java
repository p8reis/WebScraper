package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class AmazonEsHarvester extends AbstractHarvester {

    public AmazonEsHarvester() { super("https://www.amazon.es/s?k=%s"); }

    @Override
    protected Elements getListingElements(Document doc) {
        return doc.select("div[data-component-type=\"s-search-result\"]");
    }

    @Override
    protected MarketplaceDetection createDetection(Element src, int idx) throws IOException {

        String captureDate = getCaptureDate();
        String marketplace = "AmazonES";
        String url = "https://www.amazon.es" + src.select("a").attr("href");
        String imageUrl = src.select("img.s-image").attr("src");
        String price = src.select("span.a-price-symbol").text() +
                src.select("span.a-price-whole").text().replace(",", ".");
        String title = src.select("span.a-size-base-plus").text();
        String description = getDescription(url);
        String paidSearch = String.valueOf(!("".equals(src.select("a.s-sponsored-label-text").text())));
        String seller = getSeller(url);

        exportToDatabase(captureDate, marketplace, idx, title, description, url, imageUrl, price, seller, paidSearch);

        return new MarketplaceDetectionItem(captureDate, marketplace, idx, title, description, url, imageUrl, price, paidSearch, seller);
    }

    static String getDescription(String url) {

        String description = "";
        try {
            Document src = Jsoup.connect(url).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31").get();
            description = src.select("ul.a-unordered-list.a-vertical.a-spacing-mini").text().replace(",", " ");
        } catch (IOException e) { e.printStackTrace(); }
        if (description.isEmpty()) { return null; }
        return description;
    }

    static String getSeller(String url) {

        String seller = "";
        try {
            Document src = Jsoup.connect(url).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31").get();
            seller = src.select("div.tabular-buybox-text span.a-size-small a").text();
            if (seller.isEmpty()) {
                seller = src.select("div#bylineInfo_feature_div.celwidget").text().replace("Visita la Store de ", "");
            }
        } catch (IOException e) { e.printStackTrace(); }
        if (seller.isEmpty()) { return null; }
        return seller;
    }

}