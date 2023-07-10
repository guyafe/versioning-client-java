package com.guy_sw.versioning.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;
import com.guy_sw.versioning.common_models.WeatherStatus;

public class Main {

  public static void main(String[] args) throws IOException, InterruptedException {
    System.out.println("Welcome To Versioning Project Weather Client!");
    printUsage();
    boolean shouldRun = true;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while (shouldRun) {
      String userCommand = br.readLine();
      String[] commandTokens = userCommand.split(" +");
      switch (commandTokens[0]) {
        case "exit" -> shouldRun = false;
        case "health" -> sendHealthRequest();
        case "weather" -> sendCityWeatherRequest(commandTokens);
        default -> printUsage();
      }

    }
  }

  private static void sendCityWeatherRequest(String[] tokens) throws IOException, InterruptedException {
    if(tokens.length != 2) {
      printUsage();
    } else {
      String city = tokens[1];
      HttpRequest request = HttpRequest.newBuilder()
                                       .uri(URI.create("http://localhost:61285/weather/" + city))
                                       .GET()
                                       .build();
      HttpClient client = HttpClient.newHttpClient();
      String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
      WeatherStatus weatherStatus = new Gson().fromJson(response, WeatherStatus.class);
      System.out.println("Weather status for " + city + ": ");
      System.out.println("\tTemperature: " + weatherStatus.getTemperatureCelsius() + " Celsius");
      System.out.println("\tWind direction: " + weatherStatus.getWindDirection());
    }
  }

  private static void sendHealthRequest() throws IOException, InterruptedException {
    HttpRequest request = HttpRequest.newBuilder()
                                     .uri(URI.create("http://localhost:61285/weather"))
                                     .GET()
                                     .build();
    HttpClient client = HttpClient.newHttpClient();
    String response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    System.out.println("Server health status: " + response);
  }

  private static void printUsage() {
    System.out.println("Usage:");
    System.out.println("\thealth - Shows server health status");
    System.out.println("\tweather <city> - return the weather for the next day in the given city");
    System.out.println("\texit - exits application");
    System.out.println("Please enter your command:");
    System.out.println();
  }
}
