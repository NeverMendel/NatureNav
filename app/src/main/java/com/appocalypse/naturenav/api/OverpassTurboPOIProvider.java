package com.appocalypse.naturenav.api;


import android.content.Context;
import android.location.Address;

import com.appocalypse.naturenav.utility.AddressFinder;
import com.appocalypse.naturenav.utility.POITypes;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.osmdroid.util.GeoPoint;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class OverpassTurboPOIProvider {

    public static final String OVERPASS_POI_SERVICE = "https://overpass-api.de/api/interpreter";
    protected String mService;
    //protected String mUserAgent;

    private static String buildOverpassQuery(GeoPoint center, int maxResults, double maxDistance, String customQuery) {
        return String.format(Locale.US, "[out:json];\n" +
                        "node(around:%f,%f,%f)%s;\n" +
                        "out %d;\n" +
                        ">;\n" +
                        "out skel qt;",
                maxDistance, center.getLatitude(), center.getLongitude(), customQuery, maxResults);
    }

    private static String executeOverpassQuery(String query) {
        try {
            URL url = new URL(OVERPASS_POI_SERVICE);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // send the query to the server
            connection.getOutputStream().write(("data=" + query).getBytes());

            // read the result from the server
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<POI> extractPOIsFromResult(String result) {
        ArrayList<POI> poiList = new ArrayList<>();

        if (result != null) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
            JsonArray elements = jsonObject.getAsJsonArray("elements");

            for (JsonElement element : elements) {
                JsonObject poiObject = element.getAsJsonObject();
                POI poi = gson.fromJson(poiObject, POI.class);
                poi.address = AddressFinder.getAddress(poi.lat, poi.lon);
                poiList.add(poi);
            }
        }

        return poiList;
    }

    public void setService(String serviceUrl) {
        mService = serviceUrl;
    }

    public ArrayList<POI> getPOICloseTo(Context context, GeoPoint position, String query, int maxResults, double maxDistance) {
        String facility = query;
        for(Map.Entry<String, Integer> entry : POITypes.amenityToStringId.entrySet()){
            String value = context.getString(entry.getValue());
            if(value.equalsIgnoreCase(query)){
                facility = entry.getKey();
                break;
            }
        }
        String customQuery = String.format("[amenity=%s]", facility);
        String overpassQuery = buildOverpassQuery(position, maxResults, maxDistance, customQuery);
        String result = executeOverpassQuery(overpassQuery);
        return extractPOIsFromResult(result);
    }

}
