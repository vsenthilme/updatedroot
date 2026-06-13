package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.primary.model.maps.DistanceMatrix;
import com.courier.overc360.api.midmile.primary.model.maps.DistanceMatrixRequest;
import com.courier.overc360.api.midmile.primary.model.maps.Location;
import com.courier.overc360.api.midmile.primary.model.maps.MissingLocation;
import com.courier.overc360.api.midmile.primary.repository.LocationRepository;
import com.courier.overc360.api.midmile.primary.repository.MissingLocationRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class GeocodingService {

    private final LocationRepository locationRepository;
    private final MissingLocationRepository missingLocationRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public GeocodingService(LocationRepository locationRepository, MissingLocationRepository missingLocationRepository, RestTemplate restTemplate) {
        this.locationRepository = locationRepository;
        this.missingLocationRepository = missingLocationRepository;
        this.restTemplate = restTemplate;
    }

//    public List<Location> getLatLngAndSave(String address, List<DistanceMatrixRequest> distanceMatrixRequestList) {
//
//        // Check weather the address is already present in the database
//        Optional<Location> existingLocation = locationRepository.findByAddress(address);
//        if (existingLocation.isPresent()) {
//            return Collections.singletonList((existingLocation.get()));
//        }
//
//        List<Location> locations = new ArrayList<>();
//
//        for (DistanceMatrixRequest request : distanceMatrixRequestList) {
//
//            Location location = new Location();
//
//            String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address=" + request.getAddress() + "&key=AIzaSyCvxMnfexAPtGOZQ9cvi5oxQhMVgc1PRHo";
//
//            // Fetch the response from the Geocoding Api
//            GeocodingApiResponse response = restTemplate.getForObject(apiUrl, GeocodingApiResponse.class);
//
//            if (response != null && "OK".equals(response.getStatus()) && !response.getResults().isEmpty()) {
//                // Assume the first result is the most accurate
//                GeocodingResult geocodingResult = response.getResults().get(0);
//                double latitude = geocodingResult.getGeometry().getLocation().getLat();
//                double longitude = geocodingResult.getGeometry().getLocation().getLng();
//
//                // Check if the latitude and longitude are already present in the Database or not
//                Optional<Location> locationWithSameLatAndLng = locationRepository.findByLatitudeAndLongitude(latitude, longitude);
//                if (locationWithSameLatAndLng.isPresent()) {
//                    return Collections.singletonList((locationWithSameLatAndLng.get()));
//                }
//
//                // Copy properties from each DistanceMatrixRequest to a new Location
//                BeanUtils.copyProperties(request, location, CommonUtils.getNullPropertyNames(request));
//
////                    location.setAddress(address);  // Set additional fields if necessary
//                location.setLatitude(latitude);
//                location.setLongitude(longitude);
//
//                locationRepository.save(location);
//                locations.add(location);
//            }
//        }
//
//        return locations;
//
//    }

    public void getLatLngAndSave(String address, List<DistanceMatrixRequest> distanceMatrixRequestList, List<Location> validLocation, List<DistanceMatrixRequest> duplicateLocation ) {
        Map<String, List<Location>> duplicateLocationsMap = new HashMap<>();
        List<String> missingAddresses = new ArrayList<>(); // Track addresses that couldn't be geocoded

        for (DistanceMatrixRequest request : distanceMatrixRequestList) {
            String currentAddress = request.getAddress();

            Optional<Location> existingLocation = locationRepository.findByAddress(currentAddress);
            if (existingLocation.isPresent()) {
//                duplicateLocationsMap
//                        .computeIfAbsent(currentAddress, k -> new ArrayList<>())
//                        .add(existingLocation.get());
                duplicateLocation.add(request);
            } else {
                Location location = new Location();
                String apiUrl = "https://maps.googleapis.com/maps/api/geocode/json?address="
                        + currentAddress + "&key=AIzaSyCvxMnfexAPtGOZQ9cvi5oxQhMVgc1PRHo";

                GeocodingApiResponse response = restTemplate.getForObject(apiUrl, GeocodingApiResponse.class);
                if (response != null && "OK".equals(response.getStatus()) && !response.getResults().isEmpty()) {
                    GeocodingResult geocodingResult = response.getResults().get(0);
                    double latitude = geocodingResult.getGeometry().getLocation().getLat();
                    double longitude = geocodingResult.getGeometry().getLocation().getLng();

                    // Copy properties from each DistanceMatrixRequest to a new Location
                    BeanUtils.copyProperties(request, location, CommonUtils.getNullPropertyNames(request));

                    location.setLatitude(latitude);
                    location.setLongitude(longitude);
//                    location.setAddress(currentAddress);
                    locationRepository.save(location);
                    validLocation.add(location);
                } else {
                    // Address couldn't be geocoded, save it to the missing locations table
                    missingAddresses.add(currentAddress);
                    MissingLocation missingLocation = new MissingLocation();

                    // Copy properties from each DistanceMatrixRequest to a new Location
                    BeanUtils.copyProperties(request, missingLocation, CommonUtils.getNullPropertyNames(request));

                    missingLocation.setAddress(currentAddress);
                    missingLocationRepository.save(missingLocation);
                }
            }
        }

//        return uniqueLocations;
    }

    // Inner classes to handle the Google Geocoding API response
    @Data
    public static class GeocodingApiResponse {
        private String status;
        private List<GeocodingResult> results;
    }

    @Data
    public static class GeocodingResult {
        private Geometry geometry;
    }

    @Data
    public static class Geometry {
        private LocationDetails location;
    }

    @Data
    public static class LocationDetails {
        private double lat;
        private double lng;
    }
}
