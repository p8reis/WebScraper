package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.MarketplaceDetection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.appdetex.harvest.database.DatabaseReader.getAllBrandTracks;
import static com.appdetex.harvest.database.DatabaseWriter.i;

public class MercadoLivreBrHarvester extends AbstractHarvester {

    public MercadoLivreBrHarvester() throws IOException { super("https://lista.mercadolivre.com.br/%s"); }

    @Override
    protected Elements getListingElements(Document doc) {
        return doc.select("li.ui-search-layout__item");
    }

    @Override
    protected MarketplaceDetection createDetection(Element src, int idx) throws Exception {

        String captureDate = getCaptureDate();
        String marketplace = "MercadoLivreBR";
        String url = src.select("a").attr("href");
        String price = src.select("span.price-tag-symbol").first().text()
                + src.select("span.price-tag-fraction").first().text();
        String title = src.select("h2.ui-search-item__title.ui-search-item__group__element.shops__items-group-details.shops__item-title").text();
        if (title.isEmpty()) {
            title = src.select(".ui-search-item__title.shops__item-title").text();
        }
        String paidSearchFinder = src.select("div.ui-search-item__ad-container > span.ui-search-item__ad-label.ui-search-item__ad-label--blue").text();
        String paidSearchFinder2 = src.select(" div.ui-search-result__content-wrapper.shops__result-content-wrapper > div.ui-search-item__ad-container > a.ui-search-item__ad-link.ui-search-link > span.ui-search-item__ad-label.ui-search-item__ad-label--blue").text();
        String paidSearch = "false";
        if (!paidSearchFinder.isEmpty() || !paidSearchFinder2.isEmpty()) {
            paidSearch = "true";
        }
        String randomUserAgent = userAgent.getRandomUserAgent();
        src = Jsoup.connect(url).userAgent(randomUserAgent).get();
        String description = src.select("div.ui-pdp-description > p.ui-pdp-description__content").text()
                .replace(",", " ").replace("\"", "");
        String imageUrl = src.select("img.ui-pdp-image.ui-pdp-gallery__figure__image").attr("src");
        String sellerUrl = src.select("a.ui-pdp-media__action.ui-box-component__action").attr("href");
        String seller;
        if (!sellerUrl.isEmpty()) {
            src = Jsoup.connect(sellerUrl).userAgent(randomUserAgent).get();
            seller = src.select("div.store-info-title > h3.store-info__name").text();
        } else {
            seller = src.select("div.ui-seller-info > div.ui-pdp-seller__header.ui-pdp-seller__header--margin > div.ui-pdp-seller__header__info-container > div.ui-pdp-seller__header__title").text();
            if (!seller.isEmpty()) {
                seller = src.select("div.ui-vip-profile-info__info-container > div.ui-vip-profile-info__info-link > h3.ui-pdp-color--BLACK.ui-pdp-size--LARGE.ui-pdp-family--REGULAR").text();
            }
        }
        url = getShortUrl(url);
        String status = "open";
        String state = "new";
        Integer accountId = getAllBrandTracks().get(i).getAccountId();
        String searchTerm = getAllBrandTracks().get(i).getSearchTerm();

        return new MarketplaceDetectionItem(captureDate, marketplace, idx, title, description, url, imageUrl, price
                , paidSearch, seller, status, state, accountId, searchTerm);
    }

    private static String getShortUrl(String url) {
        Pattern pattern = Pattern.compile("(https://produto\\.mercadolivre\\.com\\.br/)?(MLB\\-)(\\w*)(?:\\-)(?:.*)/?");
        Matcher matcher = pattern.matcher(url);
        while (matcher.find()) {
            url = matcher.group(1) + matcher.group(2).replace("%2F", "/") + matcher.group(3);
        }
        return url;
    }
}

