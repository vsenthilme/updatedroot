package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.primary.model.maps.DistanceMatrix;
import com.courier.overc360.api.midmile.primary.model.maps.DistanceMatrixRequest;
import com.courier.overc360.api.midmile.primary.model.maps.Location;
import com.courier.overc360.api.midmile.primary.model.maps.MissingLocation;
import com.courier.overc360.api.midmile.primary.repository.DistanceMatrixRepository;
import com.courier.overc360.api.midmile.primary.repository.LocationRepository;
import com.courier.overc360.api.midmile.primary.repository.MissingLocationRepository;
import com.courier.overc360.api.midmile.primary.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DistanceMatrixTspService {

    @Autowired
    private GeocodingService geocodingService;

    @Autowired
    private TspService tspService;

    @Autowired
    private DistanceMatrixService distanceMatrixService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private DistanceMatrixRepository distanceMatrixRepository;

    @Autowired
    private MissingLocationRepository missingLocationRepository;

//    public List<DistanceMatrix> calculateDistanceAndSolveTspWithDuration(List<DistanceMatrixRequest> distanceMatrixRequest) {
////        // Geocode all origins and store locations in the DB table
////        List<Location> locations = origins.stream()
////                .map(address -> geocodingService.getLatLngAndSave(address, distanceMatrixRequest))
////                .collect(Collectors.toList());
//
//        List<String> origins = new ArrayList<>();
//        List<String> destinations = new ArrayList<>();
//
//        for (DistanceMatrixRequest request : distanceMatrixRequest) {
//            origins.add(request.getAddress());
//            destinations.add(request.getDestinations());
//        }
//
////        List<Location> locations = origins.stream()
////                .flatMap(address -> geocodingService.getLatLngAndSave(address, distanceMatrixRequest).stream())
////                .collect(Collectors.toList());
//
////        List<Location> locations = geocodingService.getLatLngAndSave(distanceMatrixRequest);
//
//        distanceMatrixService.calculateAndSaveDistanceMatrix(origins, destinations, distanceMatrixRequest);
//
//        // Solve Tsp using stored Location distances
////        List<String> cityNames = locations.stream()
////                .map(Location::getAddress)
////                .collect(Collectors.toList());
//
////        return tspService.solveTspWithDuration(cityNames);
//    }

    public List<DistanceMatrix> calculateDistanceAndSolveTspWithDurationV2(List<DistanceMatrixRequest> distanceMatrixRequest) {

        distanceMatrixRepository.deleteAllEntries();
        locationRepository.deleteAllEntries();
        missingLocationRepository.deleteAllEntries();

        List<String> origins = new ArrayList<>();
        List<String> destinations = new ArrayList<>();

        String originAddress = "7X74+7H3, Al-Dajeej, Kuwait";

        origins.add(originAddress);
        destinations.add(originAddress);

        for (DistanceMatrixRequest request : distanceMatrixRequest) {
            origins.add(request.getAddress());
            destinations.add(request.getDestinations());
        }

        locationRepository.insertOriginEntry();  // inserting a initial entry so that it can be a starting point

//        List<Location> locations = origins.stream()
//                .flatMap(address -> geocodingService.getLatLngAndSave(address, distanceMatrixRequest).stream())
//                .collect(Collectors.toList());

//        List<Location> uniqueLocations = geocodingService.getLatLngAndSave(null, distanceMatrixRequest);

        // Retrieve the Hub address entry from the database
        Location hubLocation = locationRepository.findByAddress(originAddress).orElse(null);
        if (hubLocation == null) {
            throw new IllegalStateException("Hub location not found in the database.");
        }

        // Get unique locations including the Hub and other addresses
        List<Location> uniqueLocations = new ArrayList<>();
        List<Location> allLocations = new ArrayList<>();
        uniqueLocations.add(hubLocation); // Ensure Hub is the first in the list
        allLocations.add(hubLocation);
        List<DistanceMatrixRequest> duplicateLocation = new ArrayList<>();
        geocodingService.getLatLngAndSave(null, distanceMatrixRequest, uniqueLocations, duplicateLocation);
        uniqueLocations.addAll(uniqueLocations);
        // Calculate and save unique distances
        List<String> uniqueAddresses = uniqueLocations.stream().map(Location::getAddress).collect(Collectors.toList());
//        List<String> allAddresses = distanceMatrixRequest.stream().map(DistanceMatrixRequest::getAddress).collect(Collectors.toList());

        distanceMatrixService.calculateAndSaveDistanceMatrix(origins, destinations, distanceMatrixRequest);

        // Solve Tsp using stored Location distances
        List<String> cityNames = uniqueLocations.stream()
                .map(Location::getAddress)
                .collect(Collectors.toList());

        // Solve TSP and obtain ordered sequence
        List<DistanceMatrix> tspSequence = tspService.solveTspWithDurationV2(uniqueAddresses);

        if (tspSequence.size() == 1) {
            // Insert the record
            distanceMatrixRepository.insertRecord();

            // Retrieve the inserted record and add it to the tspSequence list
            distanceMatrixRepository.findInsertedRecord().ifPresent(tspSequence::add);
        }

        // Process each duplicate by finding the corresponding distance and duration in tspSequence
        List<DistanceMatrix> processedDuplicates = new ArrayList<>();
        for (DistanceMatrixRequest duplicateRequest : duplicateLocation) {
            String duplicateAddress = duplicateRequest.getAddress();

            // Find the matching entry in tspSequence by `toAddress`
            DistanceMatrix matchingEntry = tspSequence.stream()
                    .filter(dm -> dm.getToAddress().equals(duplicateAddress))
                    .findFirst()
                    .orElse(null);

            if (matchingEntry != null) {
                // Create a new DistanceMatrix entry for the duplicate with copied distance and duration
                DistanceMatrix duplicateDistanceMatrix = new DistanceMatrix();
                duplicateDistanceMatrix.setFromAddress(matchingEntry.getFromAddress());
                duplicateDistanceMatrix.setToAddress(matchingEntry.getToAddress());
                duplicateDistanceMatrix.setDistance(matchingEntry.getDistance());
                duplicateDistanceMatrix.setDuration(matchingEntry.getDuration());

                // Copy properties from `duplicateRequest` to `duplicateDistanceMatrix`
                Location location = new Location();
                BeanUtils.copyProperties(duplicateRequest, location, CommonUtils.getNullPropertyNames(duplicateRequest));
                duplicateDistanceMatrix.setPickupId(location.getPickupId());
                duplicateDistanceMatrix.setHouseAirwayBill(location.getHouseAirwayBill());
                duplicateDistanceMatrix.setDeliveryId(location.getDeliveryId());

                // Add to processed duplicates
                processedDuplicates.add(duplicateDistanceMatrix);
            }
        }

        // Add processed duplicates to tspSequence
        tspSequence.addAll(processedDuplicates);

        // Retrieve missing addresses from the database and add them to the final sequence
        List<MissingLocation> missingLocations = missingLocationRepository.findAll();
        for (MissingLocation missing : missingLocations) {
            DistanceMatrix missingEntry = new DistanceMatrix();
            missingEntry.setFromMissLocationId(missing);
            missingEntry.setToMissLocationId(missing);
            missingEntry.setPickupId(missingEntry.getFromMissLocationId().getPickupId());
            missingEntry.setDeliveryId(missingEntry.getFromMissLocationId().getDeliveryId());
            missingEntry.setHouseAirwayBill(missingEntry.getFromMissLocationId().getHouseAirwayBill());
            missingEntry.setFromAddress(missing.getAddress());
            missingEntry.setToAddress(missing.getAddress());
            tspSequence.add(missingEntry);
        }

        tspSequence = tspSequence.stream().filter(Objects::nonNull).collect(Collectors.toList());

        return tspSequence;
    }
}
