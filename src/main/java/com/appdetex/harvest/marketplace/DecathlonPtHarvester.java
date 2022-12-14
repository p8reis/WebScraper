package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import static com.appdetex.harvest.database.DatabaseReader.getAllBrandTracks;
import static com.appdetex.harvest.database.DatabaseWriter.i;

public class DecathlonPtHarvester extends AbstractHarvester {

    public DecathlonPtHarvester() throws IOException { super("https://www.decathlon.pt/search?Ntt=%s"); }

    @Override
    protected Elements getListingElements(Document doc) {
        return doc.select(".vtmn-w-full.vtmn-flex.vtmn-flex-col.vtmn-items-center");
    }

    @Override
    protected MarketplaceDetection createDetection(Element src, int orderOnPage) throws Exception {

        String captureDate = getCaptureDate();
        String marketplace = "DecathlonPT";
        String url = "https://www.decathlon.pt" + src.select("a").attr("href");
        String title = src.select(".vtmn-p-0.vtmn-m-0.vtmn-text-sm.vtmn-font-normal.vtmn-overflow-hidden.vtmn-text-ellipsis.svelte-1l3biyf").text();
        String seller = src.getElementsByClass("svelte-ht6pwr").text().replace("Vendido e expedido por ","");
        if (seller.isEmpty()) { seller = "Decathlon"; }
        src = Jsoup.connect(url).userAgent(userAgent.getRandomUserAgent()).get();
        String imageUrl = src.select(".svelte-w1lrdd").attr("abs:src");
        String description = src.select("p.svelte-9c8w01").text();;
        String price = src.select("div.prc__active-price.svelte-t8m03u").first().text();
        Character currency = price.charAt(price.length() - 1);
        price = (currency + price).substring(0, price.length());
        String status = "open";
        String state = "new";
        Integer accountId = getAllBrandTracks().get(i).getAccountId();
        String searchTerm = getAllBrandTracks().get(i).getSearchTerm();

        return new MarketplaceDetectionItem(captureDate, marketplace, orderOnPage, title, description, url, imageUrl, price
                , String.valueOf(Boolean.FALSE), seller, status, state, accountId, searchTerm);
    }
}
