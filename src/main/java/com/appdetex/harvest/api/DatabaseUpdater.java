package com.appdetex.harvest.api;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatabaseUpdater {

    String baseURL ="http://localhost:8081/api/";

    public void updateMarketplaceDetection(int analystID, int detectionToChange, String parameterToChange, String contentToChange) throws IOException, InterruptedException, URISyntaxException, IOException, URISyntaxException {

        String requestBody = "";

        if (detectionToChange > 0 && analystID > 0) {
            if (parameterToChange.equals("status")) {
                requestBody = String.format("{\"id\":%d,\"status\":\"%s\"}", detectionToChange, contentToChange);
            } else if (parameterToChange.equals("state")) {
                requestBody = String.format("{\"id\":%d,\"state\":\"%s\"}", detectionToChange, contentToChange);
            } else if (parameterToChange.equals("reason code")) {
                requestBody = String.format("{\"id\":%d,\"reason_code\":\"%s\"}", detectionToChange, contentToChange);
            }
        }

        if (requestBody.length() > 0) {

            HttpRequest putDetectionRequest = HttpRequest.newBuilder()
                    .uri(new URI(baseURL + "marketplacedetections/update"))
                    .headers("charset", "UTF-8", "Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> putDetectionResponse = HttpClient.newBuilder()
                    .build()
                    .send(putDetectionRequest, HttpResponse.BodyHandlers.ofString());

            // adding the audit record
            if (parameterToChange.equals("status") || parameterToChange.equals("state")) {

                LocalDateTime datetimeObj = LocalDateTime.now();
                DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern("dd-mm-yyyy HH:mm:ss");
                String datetime = datetimeFormatter.format(datetimeObj);

                String auditPostBody = String.format("{\"date_time\":\"%s\",\"parameter\":\"%s\",\"marketplace_detections_id\":\"%s\"," +
                        "\"analysts_id\":\"%s\"}", datetime, parameterToChange, detectionToChange, analystID);

                HttpRequest postAuditRequest = HttpRequest.newBuilder()
                        .uri(new URI(baseURL + "audit/create"))
                        .headers("charset", "UTF-8", "Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(auditPostBody))
                        .build();

                HttpResponse<String> postAuditResponse = HttpClient.newBuilder()
                        .build()
                        .send(postAuditRequest, HttpResponse.BodyHandlers.ofString());


                System.out.println(putDetectionResponse.body());
            }
        }

    }
}