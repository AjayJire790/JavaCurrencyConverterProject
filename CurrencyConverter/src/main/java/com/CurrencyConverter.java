package com;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    private static Map<String, BigDecimal> favoriteCurrencies = new HashMap<>(); // Store favorite currencies
    private static final Scanner in = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("Options:");
            System.out.println("1. Convert Currency (Converts currency based on user input)");
            System.out.println("2. View Favorite Currencies (Displays a list of favorite currencies)");
            System.out.println("3. Add Favorite Currency (Adds a currency to the list of favorites)");
            System.out.println("4. Exit (Exits the program)");
            System.out.print("Select an option: ");
            int choice = in.nextInt();
            in.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    convertCurrency();
                    break;
                case 2:
                    viewFavoriteCurrencies();
                    break;
                case 3:
                    addFavoriteCurrency();
                    break;
                case 4:
                    System.out.println("Exiting the program.");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please select a valid option.");
            }
        }
    }

    private static void convertCurrency() throws IOException {
        System.out.println("Type convert from");
        String convertFrom = in.nextLine();
        System.out.println("Type convert to");
        String convertTo = in.nextLine();
        System.out.println("Enter quantity to convert");
        BigDecimal quantity = in.nextBigDecimal();

        String urlString = "https://api.exchangerate.host/latest?base=" + convertFrom.toUpperCase();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlString)
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String stringResponse = response.body().string();
        JSONObject jsonObject = new JSONObject(stringResponse);
        JSONObject ratesObject = jsonObject.getJSONObject("rates");
        BigDecimal rate = ratesObject.getBigDecimal(convertTo.toUpperCase());

        BigDecimal result = rate.multiply(quantity);
        System.out.println("Result: " + result);
    }

    private static void viewFavoriteCurrencies() {
        System.out.println("Favorite Currencies:");
        for (Map.Entry<String, BigDecimal> entry : favoriteCurrencies.entrySet()) {
            System.out.println(entry.getKey());
        }
    }

    private static void addFavoriteCurrency() {
        System.out.println("Type currency code to add to favorites (e.g., USD):");
        String currencyToAdd = in.nextLine().toUpperCase();
        favoriteCurrencies.put(currencyToAdd, BigDecimal.ZERO); // Placeholder for conversion rate
        System.out.println(currencyToAdd + " added to favorites.");
    }
}
