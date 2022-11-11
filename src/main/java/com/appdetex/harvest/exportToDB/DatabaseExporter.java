package com.appdetex.harvest.exportToDB;

import com.appdetex.harvest.api.MarketplaceDetection;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class DatabaseExporter {

    private String marketplace;

    private List<MarketplaceDetection> marketplaceDetections;

    public String exportToDb(List<MarketplaceDetection> marketplaceDetections, String marketplace) throws URISyntaxException, IOException, InterruptedException {

        String baseURL = "http://localhost:8081/api/";

        // ----------- ANALYSTS --------------

        // GET
        HttpRequest getAnalystRequest = HttpRequest.newBuilder()
                .uri(new URI(baseURL + "analysts/getAll"))
                .GET()
                .build();

        HttpResponse<String> getAnalystResponse = HttpClient.newBuilder()
                .build()
                .send(getAnalystRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(getAnalystResponse.body());


        // POST
        HttpRequest postAnalystRequest = HttpRequest.newBuilder()
                .uri(new URI(baseURL + "analysts/create"))
                .headers("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"name\":\"Charles Jacintoh\", \"email\":\"HelloIt'sMe@hotmail.com\",\"password\":\"passrte50\"}"))
                .build();

        HttpResponse<String> postAnalystResponse = HttpClient.newBuilder()
                .build()
                .send(postAnalystRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(postAnalystResponse.body());


        // PUT
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(new URI(baseURL + "analysts/update"))
                .headers("Content-Type","application/json")
                .PUT(HttpRequest.BodyPublishers.ofString("{\"id\":1,\"name\":\"Carolina Barão\",\"password\":\"bombasticfantastic\"}"))
                .build();

        HttpResponse<String> putResponse = HttpClient.newBuilder()
                .build()
                .send(putRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(putResponse.body());


        // DELETE
        int idToDelete = 0;
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(new URI(baseURL + "analysts/delete/"+ idToDelete))
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = HttpClient.newBuilder()
                .build()
                .send(deleteRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(deleteResponse.body());







        return String.format("Export to external database of %s detections was successful",marketplace);

    }
}
