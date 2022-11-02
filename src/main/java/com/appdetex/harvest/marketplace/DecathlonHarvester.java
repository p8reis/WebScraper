package com.appdetex.harvest.marketplace;

import com.appdetex.harvest.api.Main;
import com.appdetex.harvest.api.MarketplaceDetection;
import com.appdetex.harvest.api.MarketplaceHarvester;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class DecathlonHarvester implements MarketplaceHarvester {

    @Override
    public ArrayList<MarketplaceDetection> parseTarget(String term, int numItems) throws IOException {
        ArrayList<MarketplaceDetection> detections = new ArrayList<>();

        String websiteUrl = String.format("https://www.decathlon.pt/search?Ntt=%s", term);
        String userAgent = getUserAgent();
        Document scrap = Jsoup.connect(websiteUrl).userAgent(userAgent).get();

        // getting all the info from each product (except price)
        Elements productsBlock = scrap.select(".vtmn-w-full.vtmn-flex.vtmn-flex-col.vtmn-items-center");
        //getting the product prices (in the same order as the products above)
        Elements priceDiv = scrap.getElementsByClass("prc__cartridge");

        // Getting the scrapping date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        // Limit to the first numItems results
        int MaxSize = (Math.min(productsBlock.size(), numItems));

        // Getting the info
        for (int i = 0; i < MaxSize; i++) {

            String productTitle = productsBlock.get(i).select(".vtmn-p-0.vtmn-m-0.vtmn-text-sm.vtmn-font-normal.vtmn-overflow-hidden.vtmn-text-ellipsis.svelte-1l3biyf").text();

            Element prodURL = productsBlock.get(i).select("a").first();
            String productUrl = prodURL.attr("abs:href");

            String productDescription = getProductDescription(productUrl);

            String prodImageUrl = getProductImageUrl(productUrl);

            // Get the currency and the price
            ArrayList<String> prodPriceCurrency = new ArrayList<>(getPriceCurrency(priceDiv.get(i)));
            Double productPrice = Double.parseDouble(prodPriceCurrency.get(1));
            String currency = prodPriceCurrency.get(0);

            int orderOnPage = i + 1;

            // In this page there isn't any paid search
            boolean paidSearch = false;

            String productSeller = productsBlock.get(i).getElementsByClass("svelte-ht6pwr").text();
            productSeller = productSeller.replace("Vendido e expedido por ","");

            LocalDateTime today = LocalDateTime.now();
            String todayDate = dtf.format(today);

            detections.add(new MarketplaceDetectionItem(todayDate,productTitle,productDescription,productUrl,prodImageUrl,orderOnPage,paidSearch,productPrice,currency,productSeller));

            // variable to show on screen the progress of the process
            double progressStatus = (i+1)/(float)MaxSize*100;
            System.out.printf("Scraping for %s... %.1f %% complete\n", term,progressStatus);
        }

        return detections;
    }

    public static String getUserAgent() throws IOException {
        //  Returns a random user agent

        ArrayList<String> userAgentsList = new ArrayList<>();

        // Get user agents list from an external file
        InputStream inStream = Main.class.getClassLoader().getResourceAsStream("userAgents.txt");
        if (inStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
            String usersTxt = reader.readLine();
            userAgentsList.add(usersTxt);
        }

        // returns a random user agent from the list
        int randomIndex = ThreadLocalRandom.current().nextInt(0, userAgentsList.size());
        return userAgentsList.get(randomIndex);
    }

    public static String getProductDescription(String url) {

        String prodDescription = "";

        try {
            String userAgent = getUserAgent();
            Document scrap = Jsoup.connect(url).userAgent(userAgent).get();
            prodDescription = scrap.select(".svelte-1uw9j0x").text();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (prodDescription.length() == 0) {
            prodDescription = null;
        }

        return prodDescription;
    }

    public static String getProductImageUrl(String prodUrl) {

        String imgLink = "";

        try {
            String userAgent = getUserAgent();
            Document scrap = Jsoup.connect(prodUrl).userAgent(userAgent).get();
            imgLink = scrap.select(".svelte-w1lrdd").attr("abs:src");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return imgLink;
    }

    public static ArrayList getPriceCurrency(Element priceDiv) {
        ArrayList<String> priceCurrency = new ArrayList<>();

        String prodPrice = priceDiv.select(".prc__active-price.svelte-t8m03u").text();
        String currency = String.valueOf(prodPrice.charAt(prodPrice.length()-1));
        String productPrice = prodPrice.replace(currency, "").replace(",", ".");

        priceCurrency.add(currency);
        priceCurrency.add(productPrice);

        return priceCurrency;

    }
}
