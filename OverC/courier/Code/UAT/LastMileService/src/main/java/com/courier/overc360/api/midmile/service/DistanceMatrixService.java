package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.primary.model.maps.DistanceMatrix;
import com.courier.overc360.api.midmile.primary.model.maps.DistanceMatrixRequest;
import com.courier.overc360.api.midmile.primary.model.maps.Location;
import com.courier.overc360.api.midmile.primary.repository.DistanceMatrixRepository;
import com.courier.overc360.api.midmile.primary.repository.LocationRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DistanceMatrixService {

    private final LocationRepository locationRepository;
    private final DistanceMatrixRepository distanceMatrixRepository;
    private final RestTemplate restTemplate;

    private static final String API_KEY = "AIzaSyCvxMnfexAPtGOZQ9cvi5oxQhMVgc1PRHo";

    public void calculateAndSaveDistanceMatrix(List<String> origins, List<String> destinations, List<DistanceMatrixRequest> distanceMatrixRequest) {
        List<Location> originLocations = locationRepository.findAllByAddressIn(origins);
        List<Location> destinationLocations = locationRepository.findAllByAddressIn(destinations);

        for (Location origin : originLocations) {
            for (Location destination: destinationLocations) {
                if (origin.equals(destination)) continue;

                // Check if the distance between these addresses already exist
                if (distanceMatrixRepository.existsByFromAddressAndToAddress(origin.getAddress(), destination.getAddress())) {
                    continue;
                }

                String apiUrl = String.format(
                        "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%f,%f&destinations=%f,%f&key=%s",
                        origin.getLatitude(), origin.getLongitude(), destination.getLatitude(), destination.getLongitude(), API_KEY
                );

                try {
                    DistanceMatrixService.DistanceMatrixResponse response = restTemplate.getForObject(apiUrl, DistanceMatrixService.DistanceMatrixResponse.class);

                    if (response != null && response.getRows() != null && response.getRows().length > 0) {
                        DistanceMatrixService.DistanceMatrixElement element = response.getRows()[0].getElements()[0];

                        DistanceMatrix distance = new DistanceMatrix();

                        distance.setPickupId(origin.getPickupId() != null ? origin.getPickupId() : null);

                        distance.setDeliveryId(origin.getDeliveryId() != null ? origin.getDeliveryId() : null);

                        distance.setHouseAirwayBill(origin.getHouseAirwayBill() != null ? origin.getHouseAirwayBill() : null);

                        distance.setFromLocationId(origin);
                        distance.setToLocationId(destination);
                        distance.setFromAddress(origin.getAddress());
                        distance.setToAddress(destination.getAddress());
                        distance.setDistance(element.getDistance().getValue()); // distance in meters
                        distance.setDuration(element.getDuration().getText()); // e.g., "10 mins"

                        distanceMatrixRepository.save(distance);
                    }

                } catch (RestClientException e) {
                    throw new RuntimeException("Error calling Distance Matrix API", e);
                }
            }
        }
    }

    // Inner classes for handling Distance Matrix API responses
    private static class DistanceMatrixResponse {
        private DistanceMatrixService.DistanceMatrixRow[] rows;
        private String status;

        public DistanceMatrixService.DistanceMatrixRow[] getRows() {
            return rows;
        }
    }

    private static class DistanceMatrixRow {
        private DistanceMatrixService.DistanceMatrixElement[] elements;

        public DistanceMatrixService.DistanceMatrixElement[] getElements() {
            return elements;
        }
    }

    private static class DistanceMatrixElement {
        private DistanceMatrixService.DistanceMatrixValue distance;
        private DistanceMatrixService.DistanceMatrixValue duration;

        public DistanceMatrixService.DistanceMatrixValue getDistance() {
            return distance;
        }

        public DistanceMatrixService.DistanceMatrixValue getDuration() {
            return duration;
        }
    }

    private static class DistanceMatrixValue {
        private String text;
        private Double value;

        public String getText() {
            return text;
        }

        public Double getValue() {
            return value;
        }
    }
}
