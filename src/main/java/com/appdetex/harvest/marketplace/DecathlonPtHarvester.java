package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class DecathlonPtHarvester extends AbstractHarvester {

    public DecathlonPtHarvester() { super("https://www.decathlon.pt/search?Ntt=%s"); }

    @Override
    protected Elements getListingElements(Document doc) {
        return doc.select(".vtmn-w-full.vtmn-flex.vtmn-flex-col.vtmn-items-center");
    }

    @Override
    protected MarketplaceDetection createDetection(Element src, int idx) throws IOException {

        String captureDate = getCaptureDate();
        String marketplace = "DecathlonPT";
        String url = "https://www.decathlon.pt" + src.select("a").attr("href");
        String imageUrl = getProductImageUrl(url);
        String price = getPrice(url);
        String title = src.select(".vtmn-p-0.vtmn-m-0.vtmn-text-sm.vtmn-font-normal.vtmn-overflow-hidden.vtmn-text-ellipsis.svelte-1l3biyf").text().replace(",", " ");
        String description = getProductDescription(url);
        String seller = src.getElementsByClass("svelte-ht6pwr").text().replace("Vendido e expedido por ","");

        exportToDatabase(captureDate, marketplace, idx, title, description, url, imageUrl, price, seller, String.valueOf(Boolean.FALSE));

        return new MarketplaceDetectionItem(captureDate, marketplace, idx, title, description, url, imageUrl, price, String.valueOf(Boolean.FALSE), seller);
    }

    public static String getProductDescription(String url) {

        String description = "";
        try {
            Document src = Jsoup.connect(url).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31").get();
            description = src.select(".svelte-1uw9j0x").text().replace(",", " ");
        } catch (IOException e) { e.printStackTrace(); }
        if (description.length() == 0) { description = null; }
        return description;
    }

    public static String getProductImageUrl(String url) {

        String imageUrl = "";
        try {
            Document src = Jsoup.connect(url).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31").get();
            imageUrl = src.select(".svelte-w1lrdd").attr("abs:src");
        } catch (IOException e) { e.printStackTrace(); }
        return imageUrl;
    }

    public static String getPrice(String url) {

        String price = "";
        Character currency;
        try {
            Document src = Jsoup.connect(url).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31").get();
            price = src.select("div.prc__active-price.svelte-t8m03u").text().replace(",", ".");
            currency = price.charAt(price.length() - 1);
            price = (currency + price).substring(0, price.length());
        } catch (IOException e) { e.printStackTrace(); }
        return price;
    }

}
