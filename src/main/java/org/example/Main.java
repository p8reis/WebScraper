package org.example;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.owasp.encoder.Encode;


public class Main {

    static String jacuzziDescription;

    public static void main(String[] args) {
        try {
            // Here we create a document object and use JSoup to fetch the website
            Document doc = Jsoup.connect("https://lista.mercadolivre.com.br/jacuzzi#D[A:jacuzzi]").get();

            // With the document fetched, we use JSoup's title() method to fetch the title
            System.out.printf("Title: %s\n", doc.title());


            Elements jacuzzi=doc.select("li.ui-search-layout__item");

            int orderInPage=0;


            for (int i=0; i<10;i++){
                orderInPage++;


                String jacuzziName = GetProductName(jacuzzi.get(i));
                System.out.println(jacuzziName);

                String jacuzziPriceProcessed =GetProductPrice(jacuzzi.get(i));

                String jacuzziListing = GetProductUrl(jacuzzi.get(i));

                String jacuzziImage = GetProductImageUrl(jacuzzi.get(i));

                //String jacuzziDescription = GetProductDescription(jacuzzi.get(i));

                String paidSponsor = GetSponsor(jacuzzi.get(i));

                String stringDate= GetDateOfDetection(jacuzzi.get(i));




                Document jacuzziPage = Jsoup.connect(jacuzziListing).get();
                Thread.sleep(1000);
                jacuzziDescription = jacuzziPage.select(" div.ui-pdp-description > p.ui-pdp-description__content").text();




                ArrayList<String> productInfo = new ArrayList<>();
                productInfo.add(jacuzziName);
                productInfo.add(jacuzziPriceProcessed);
                productInfo.add(jacuzziListing);
                productInfo.add(jacuzziImage);
                productInfo.add(jacuzziDescription);
                productInfo.add(paidSponsor);
                productInfo.add(String.valueOf(orderInPage));
                productInfo.add(stringDate);





                //System.out.println("Product name: " + jacuzziTitle );
                System.out.println("\t" + "Price: " + jacuzziPriceProcessed + " BRL");
                System.out.println("\t" + "Listing URL: " + jacuzziListing);
                System.out.println("\t" + "Image URL: " + jacuzziImage);
                System.out.println("\t"+ "Description: " + jacuzziDescription);
                System.out.println("\t" + "Sponsored: " + paidSponsor);
                System.out.println("\t" + "Order In Page:" + orderInPage);
                System.out.println("\n");

                CsvExporter(productInfo);

            }

            // In case of any IO errors, we want the messages written to the console
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    static String GetProductName(Element jacuzzi){
        String jacuzziName = jacuzzi.select("h2.ui-search-item__title.ui-search-item__group__element.shops__items-group-details.shops__item-title").text();
        //System.out.println("Product name: " + jacuzziTitle );
        return jacuzziName;
    }


    static String GetProductPrice(Element jacuzzi){
        String jacuzziPrice = jacuzzi.select("div.ui-search-price__second-line.shops__price-second-line")
                .select("span.price-tag-text-sr-only")
                .text();
        int index = jacuzziPrice.indexOf(" ");
        String jacuzziPriceProcessed= jacuzziPrice.substring(0, index);
        return jacuzziPriceProcessed;
    }


    static String GetProductImageUrl(Element jacuzzi){
        Element jacuzziImageURL = jacuzzi.select("div.slick-slide.slick-active")
                .select("img.ui-search-result-image__element.shops__image-element").first();
        String jacuzziImage = jacuzziImageURL.attr("data-src");
        return jacuzziImage;

    }


    static String GetProductUrl(Element jacuzzi){
        Element jacuzziListingURL = jacuzzi.select(".ui-search-result__content.ui-search-link").first();
        String jacuzziListing = Encode.forJava(jacuzziListingURL.attr("href"));
        return jacuzziListing;
    }

    /*
    static String GetProductDescription(Element jacuzzi) throws IOException {
        Element jacuzziListingURL = jacuzzi.select(".ui-search-result__content.ui-search-link").first();
        String jacuzziListing = jacuzziListingURL.attr("href");


        Document jacuzziPage = Jsoup.connect(jacuzziListing).get();
        String jacuzziDescription =jacuzziPage.select(" div.ui-pdp-description > p.ui-pdp-description__content").text();
        return jacuzziDescription;
    }

     */


    static String GetSponsor(Element jacuzzi){
        Element paidSearch = jacuzzi.select("div.ui-search-item__ad-container > span.ui-search-item__ad-label.ui-search-item__ad-label--blue").first();

        String paidSponsor = "";


        if (paidSearch != null){

            paidSponsor = "Paid Search";

        }
        else {

            paidSponsor="No Paid Search";
        }
        return paidSponsor;

    }

    public static String GetDateOfDetection(Element jacuzzi){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDate = dateFormat.format(date);
        return stringDate;
    }

    public static void CsvExporter(ArrayList<String> productInfo){
        /*
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringDate = dateFormat.format(date);
         */

        //System.out.println(productInfo);


        try {
            Path path = Paths.get("/tmp/csvFilesWebScraping/csvFile.csv");
            if(!Files.exists(path)) {
                String[] top = {"Product name", "price", "listing url", "image url", "Description", "sponsored", "order in page", "date"};

                FileWriter csvFile = new FileWriter("/tmp/csvFilesWebScraping/csvFile.csv");
                CSVWriter write = new CSVWriter(csvFile);
                write.writeNext(top);
                write.close();
            }

            FileWriter csvFile2 = new FileWriter("/tmp/csvFilesWebScraping/csvFile.csv",true);
            CSVWriter write2 = new CSVWriter(csvFile2);
            String[] csvExported = productInfo.toArray(new String[0]);
            write2.writeNext(csvExported);
            write2.close();





        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


    }
}
