package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;
import static com.appdetex.harvest.database.DatabaseWriter.postToDatabase;

import java.io.IOException;
import java.util.regex.*;

import com.appdetex.harvest.database.UserAgents;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AmazonEsHarvester extends AbstractHarvester {

    public AmazonEsHarvester() throws IOException { super("https://www.amazon.es/s?k=%s"); }

    @Override
    protected Elements getListingElements(Document doc) {
        return doc.select("div[data-component-type=\"s-search-result\"]");
    }

    @Override
    protected MarketplaceDetection createDetection(Element src, int idx) throws Exception {

        String captureDate = getCaptureDate();
        String marketplace = "AmazonES";
        String url = src.select("a").attr("href");
        if (!url.startsWith("https://www.amazon.es")) { url = "https://www.amazon.es" + url; }
        String imageUrl = src.select("img.s-image").attr("src");
        String price = src.select("span.a-price-symbol").text() + src.select("span.a-price-whole").text();
        String title = src.select("span.a-size-base-plus").text();
        String paidSearch = String.valueOf(!("".equals(src.select("a.s-sponsored-label-text").text())));
        src = Jsoup.connect(url).userAgent(userAgent.getRandomUserAgent()).get();
        String description = src.select("ul.a-unordered-list.a-vertical.a-spacing-mini").text();
        String seller = src.select("div.tabular-buybox-text span.a-size-small a").text();
        if (seller.isEmpty()) {
            seller = src.select("div#bylineInfo_feature_div.celwidget").text()
                    .replace("Visita la Store de ", "")
                    .replace("Marca: ", "");
        }
        url = getShortUrl(url);

        postToDatabase(captureDate, marketplace, idx, title, description, url, imageUrl, price, seller, paidSearch);

        return new MarketplaceDetectionItem(captureDate, marketplace, idx, title, description, url, imageUrl, price, paidSearch, seller);
    }

    private static String getShortUrl(String url) {
        Pattern pattern = Pattern.compile("(https://www\\.amazon\\.es)?(?:.*)((?:/|%2F)dp(?:/|%2F))(\\w*)(?:/|%2F)(?:.*)/?");
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            url = matcher.group(1) + matcher.group(2).replace("%2F", "/") + matcher.group(3);
        }
        return url;
    }

}