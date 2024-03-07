package tn.esprit.affariety.controllers;


import okhttp3.*;

import java.io.IOException;

public class SmsSender {

    public static void sendSms(String phoneNumber, String message) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"messages\":[{\"destinations\":[{\"to\":\"" + "216" +phoneNumber + "\"}],\"from\":\"AFARIYETI\",\"text\":\"" + message + "\"}]}");
        Request request = new Request.Builder()
                .url("https://vvgeqe.api.infobip.com/sms/2/text/advanced")
                .method("POST", body)
                .addHeader("Authorization", "App 5ecdcec5f83ac29b7fa9561b9650c3f4-122b8358-2199-4173-bdf6-507a8e568f04")
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println("Response code: " + response.code());
        System.out.println("Response body: " + response.body().string());
        System.out.println("sms sent");
    }

}
