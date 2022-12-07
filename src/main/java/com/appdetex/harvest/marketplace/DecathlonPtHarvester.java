package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.appdetex.harvest.database.DatabaseWriter.postToDatabase;

public class DecathlonPtHarvester extends AbstractHarvester {

    public DecathlonPtHarvester() { super("https://www.decathlon.pt/search?Ntt=%s"); }

    @Override
    protected Elements getListingElements(Document doc) {
        return doc.select(".vtmn-w-full.vtmn-flex.vtmn-flex-col.vtmn-items-center");
    }

    @Override
    protected MarketplaceDetection createDetection(Element src, int idx) throws Exception {

        String captureDate = getCaptureDate();
        String marketplace = "DecathlonPT";
        String url = "https://www.decathlon.pt" + src.select("a").attr("href");
        String title = src.select(".vtmn-p-0.vtmn-m-0.vtmn-text-sm.vtmn-font-normal.vtmn-overflow-hidden.vtmn-text-ellipsis.svelte-1l3biyf").text().replace(",", " ");
        String seller = src.getElementsByClass("svelte-ht6pwr").text().replace("Vendido e expedido por ","");

        src = Jsoup.connect(url).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31").get();
        String imageUrl = src.select(".svelte-w1lrdd").attr("abs:src");
        String description = src.select(".svelte-1uw9j0x").text().replace(",", " ").replace("\"", "");;
        String price = src.select("div.prc__active-price.svelte-t8m03u").text().replace(",", ".");
        Character currency = price.charAt(price.length() - 1);
        price = (currency + price).substring(0, price.length());

        postToDatabase(captureDate, marketplace, idx, title, description, url, imageUrl, price, seller, String.valueOf(Boolean.FALSE));

        return new MarketplaceDetectionItem(captureDate, marketplace, idx, title, description, url, imageUrl, price, String.valueOf(Boolean.FALSE), seller);
    }
}
