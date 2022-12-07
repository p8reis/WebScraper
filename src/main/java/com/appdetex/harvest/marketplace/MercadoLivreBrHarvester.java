package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static com.appdetex.harvest.database.DatabaseWriter.postToDatabase;

public class MercadoLivreBrHarvester extends AbstractHarvester {

    public MercadoLivreBrHarvester() { super("https://lista.mercadolivre.com.br/%s"); }

    @Override
    protected Elements getListingElements(Document doc) {
        return doc.select("li.ui-search-layout__item");
    }

    @Override
    protected MarketplaceDetection createDetection(Element src, int idx) throws Exception {

        String captureDate = getCaptureDate();
        String marketplace = "MercadoLivreBR";
        String url = src.select(".ui-search-result__content.ui-search-link").first().attr("href");
        String price = src.select("span.price-tag-symbol").first().text()
                + src.select("span.price-tag-fraction").first().text();
        String title = src.select("h2.ui-search-item__title.ui-search-item__group__element.shops__items-group-details.shops__item-title").text().replace(",", " ");
        String paidSearch = String.valueOf(url.contains("is_advertising=true"));

        src = Jsoup.connect(url).userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31").get();
        String description = src.select("div.ui-pdp-description > p.ui-pdp-description__content").text()
                .replace(",", " ").replace("\"", "");
        String imageUrl = src.select("img.ui-pdp-image.ui-pdp-gallery__figure__image").attr("src");
        String sellerUrl = src.select("a.ui-pdp-media__action.ui-box-component__action").first().attr("href");
        src = Jsoup.connect(sellerUrl).get();
        String seller = src.select("div.store-info-title > h3.store-info__name").text();

        postToDatabase(captureDate, marketplace, idx, title, description, url, imageUrl, price, seller, paidSearch);

        return new MarketplaceDetectionItem(captureDate, marketplace, idx, title, description, url, imageUrl, price, paidSearch, seller);
    }
}