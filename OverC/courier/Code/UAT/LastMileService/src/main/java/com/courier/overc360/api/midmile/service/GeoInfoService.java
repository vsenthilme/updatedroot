package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.primary.model.maps.GeoInfo;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@Slf4j
public class GeoInfoService {

    private static final String API_KEY = "AIzaSyCvxMnfexAPtGOZQ9cvi5oxQhMVgc1PRHo";

    private final RestTemplate restTemplate;

    public GeoInfoService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Convert a single address to lat and lng
    public GeoInfo geoCode(String address) {
        String url = buildGeocodingUrl(address);
        log.info("Geocoding request URL: {}", url);

        // Check for a valid response and handle errors
        try {
            String response = restTemplate.getForObject(url, String.class);
            log.info("Geocoding API response: {}", response);
            return parseGeoCodeResponse(response);
        } catch (Exception e) {
            log.error("Error in geocoding request: ", e);
//            throw new RuntimeException("Failed to geocode address: " + address);\
            return null;
        }

    }

    private String buildGeocodingUrl(String address) {
        return UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
                .queryParam("address", address)
                .queryParam("key", API_KEY)
                .toUriString();
    }

    // Convert lat and lng to a single address
    public GeoInfo reverseGeocode(double lat, double lng) {
        String url = buildReverseGeocodingUrl(lat, lng);
        log.info("Reverse geocoding request URL: {}", url);

        // Check for a valid response and handle errors
        try {
            String response = restTemplate.getForObject(url, String.class);
            log.info("Reverse geocoding API response: {}", response);
            return parseReverseGeoCodeResponse(response);
        } catch (Exception e) {
            log.error("Error in reverse geocoding request: ", e);
            throw new RuntimeException("Failed to reverse geocode lat/lng: " + lat + "," + lng);
        }
    }

    private String buildReverseGeocodingUrl(double lat, double lng) {
        return UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/geocode/json")
                .queryParam("latlng", lat + "," + lng)
                .queryParam("key", API_KEY)
                .toUriString();
    }

    // Method to parse the response from the Geocoding API
    private GeoInfo parseGeoCodeResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        String status = jsonResponse.getString("status");

        if (!"OK".equals(status)) {
            throw new RuntimeException("Geocoding failed: " + jsonResponse.getString("error_message"));
        }

        JSONObject location = jsonResponse.getJSONArray("results")
                .getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONObject("location");

        GeoInfo geoInfo = new GeoInfo();
        geoInfo.setLatitude(location.getDouble("lat"));
        geoInfo.setLongitude(location.getDouble("lng"));
        geoInfo.setAddress(jsonResponse.getJSONArray("results").getJSONObject(0).getString("formatted_address"));
        return geoInfo;
    }

    // Method to parse the response from the Reverse Geocoding API
    private GeoInfo parseReverseGeoCodeResponse(String response) {
        JSONObject jsonResponse = new JSONObject(response);
        String status = jsonResponse.getString("status");

        if (!"OK".equals(status)) {
            throw new RuntimeException("Reverse Geocoding failed: " + jsonResponse.getString("error_message"));
        }

        JSONObject result = jsonResponse.getJSONArray("results").getJSONObject(0);
        GeoInfo geoInfo = new GeoInfo();
        geoInfo.setAddress(result.getString("formatted_address"));

        JSONObject location = result.getJSONObject("geometry").getJSONObject("location");
        geoInfo.setLatitude(location.getDouble("lat"));
        geoInfo.setLongitude(location.getDouble("lng"));
        return geoInfo;
    }
}
