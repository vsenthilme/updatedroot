package com.courier.overc360.api.midmile.service;

import com.courier.overc360.api.midmile.primary.model.maps.DistanceMatrix;
import com.courier.overc360.api.midmile.primary.model.maps.Location;
import com.courier.overc360.api.midmile.primary.repository.DistanceMatrixRepository;
import com.courier.overc360.api.midmile.primary.repository.LocationRepository;
import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TspService {

    private static final Logger logger = LoggerFactory.getLogger(TspService.class);

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private DistanceMatrixRepository distanceMatrixRepository;

    @Autowired
    private RestTemplate restTemplate;

    static {
        Loader.loadNativeLibraries();
    }

    public List<Location> solveTsp(List<String> cityAddresses) {
        try {
            logger.info("Starting TSP solution for cities: {}", cityAddresses);
            List<Location> cityList = locationRepository.findAllByAddressIn(cityAddresses);
            // Check if all input cities exist in the database
            List<String> foundCityNames = cityList.stream().map(Location::getAddress).collect(Collectors.toList());
            List<String> notFoundCityNames = cityAddresses.stream()
                    .filter(city -> !foundCityNames.contains(city))
                    .collect(Collectors.toList());

            if (!notFoundCityNames.isEmpty()) {
                logger.error("The following cities do not exist in the database: {}", notFoundCityNames);
                throw new IllegalArgumentException("Some cities in the input list do not exist in the database: " + notFoundCityNames);
            }

            int cityCount = cityList.size();
            logger.info("Number of cities: {}", cityCount);

            double[][] distanceMatrix = new double[cityCount][cityCount];

            for (int i = 0; i < cityCount; i++) {
                Location fromCity = cityList.get(i);
                List<DistanceMatrix> distanceList = distanceMatrixRepository.findByFromLocationId(fromCity);

                for (DistanceMatrix distance : distanceList) {
                    int toCityIndex = cityList.indexOf(distance.getToLocationId());
                    if (toCityIndex == -1) {
                        throw new IllegalArgumentException("Distance information is incomplete for some cities.");
                    }
                    distanceMatrix[i][toCityIndex] = distance.getDistance();
                }
            }

            logger.debug("Distance matrix: ");
            for (double[] row : distanceMatrix) {
                logger.debug(java.util.Arrays.toString(row));
            }

            RoutingIndexManager manager = new RoutingIndexManager(distanceMatrix.length, 1, 0);
            RoutingModel routing = new RoutingModel(manager);

            int transitCallbackIndex = routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                int fromNode = manager.indexToNode(fromIndex);
                int toNode = manager.indexToNode(toIndex);
                return (long) distanceMatrix[fromNode][toNode];
            });

            routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

            RoutingSearchParameters searchParameters = main.defaultRoutingSearchParameters().toBuilder()
                    .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                    .setLocalSearchMetaheuristic(LocalSearchMetaheuristic.Value.GUIDED_LOCAL_SEARCH)
                    .setTimeLimit(com.google.protobuf.Duration.newBuilder().setSeconds(30).build())
                    .build();

            Assignment solution = routing.solveWithParameters(searchParameters);

            if (solution == null) {
                throw new RuntimeException("No Solution Found");
            }

//            int[] route = new int[distanceMatrix.length];
//            long index = routing.start(0);
//            for (int i = 0; i < route.length; i++) {
//                route[i] = manager.indexToNode(index);
//                index = solution.value(routing.nextVar(index));
//            }

            // Create a list of locations representing the route
            List<Location> tspRoute = new ArrayList<>();
            long index = routing.start(0);
            for (int i = 0; i < cityList.size(); i++) {
                int cityIndex = manager.indexToNode(index);
                tspRoute.add(cityList.get(cityIndex));
                index = solution.value(routing.nextVar(index));
            }
            logger.info("TSP solution found with locations: {}", tspRoute);
//            logger.info("TSP solution found: {}", java.util.Arrays.toString(route));
            // After processing, delete all entries from distance_matrix table
            deleteDistanceMatrixEntries();
            deleteLocationEntries();
            return tspRoute;
        } catch (Exception e) {
            logger.error("Error solving TSP: ", e);
            throw new RuntimeException("Error solving TSP: " + e.getMessage(), e);
        }
    }

    public void deleteDistanceMatrixEntries() {
        distanceMatrixRepository.deleteAllEntries();
    }

    public void deleteLocationEntries() {
        locationRepository.deleteAllEntries();
    }

    /*-----------------------------------------ETA Calculation--------------------*/

    public List<TspRouteSolution> solveTspWithEta(List<String> cityAddresses) {
        try {
            logger.info("Starting TSP solution for cities: {}", cityAddresses);
            List<Location> cityList = locationRepository.findAllByAddressIn(cityAddresses);
            // Check if all input cities exist in the database
            List<String> foundCityNames = cityList.stream().map(Location::getAddress).collect(Collectors.toList());
            List<String> notFoundCityNames = cityAddresses.stream()
                    .filter(city -> !foundCityNames.contains(city))
                    .collect(Collectors.toList());

            if (!notFoundCityNames.isEmpty()) {
                logger.error("The following cities do not exist in the database: {}", notFoundCityNames);
                throw new IllegalArgumentException("Some cities in the input list do not exist in the database: " + notFoundCityNames);
            }

            int cityCount = cityList.size();
            logger.info("Number of cities: {}", cityCount);

            double[][] distanceMatrix = new double[cityCount][cityCount];
            List<Double> etaList = new ArrayList<>(); // List to store ETAs between points

            for (int i = 0; i < cityCount; i++) {
                Location fromCity = cityList.get(i);
                List<DistanceMatrix> distanceList = distanceMatrixRepository.findByFromLocationId(fromCity);

                for (DistanceMatrix distance : distanceList) {
                    int toCityIndex = cityList.indexOf(distance.getToLocationId());
                    if (toCityIndex == -1) {
                        throw new IllegalArgumentException("Distance information is incomplete for some cities.");
                    }
                    distanceMatrix[i][toCityIndex] = distance.getDistance();

                    // Fetch ETA between fromCity and toCity using an external API (Google Distance Matrix API)
                    double eta = fetchEta(fromCity, cityList.get(toCityIndex));  // Function to calculate ETA
                    etaList.add(eta);
                }
            }

            logger.debug("Distance matrix: ");
            for (double[] row : distanceMatrix) {
                logger.debug(java.util.Arrays.toString(row));
            }

            RoutingIndexManager manager = new RoutingIndexManager(distanceMatrix.length, 1, 0);
            RoutingModel routing = new RoutingModel(manager);

            int transitCallbackIndex = routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                int fromNode = manager.indexToNode(fromIndex);
                int toNode = manager.indexToNode(toIndex);
                return (long) distanceMatrix[fromNode][toNode];
            });

            routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

            RoutingSearchParameters searchParameters = main.defaultRoutingSearchParameters().toBuilder()
                    .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                    .setLocalSearchMetaheuristic(LocalSearchMetaheuristic.Value.GUIDED_LOCAL_SEARCH)
                    .setTimeLimit(com.google.protobuf.Duration.newBuilder().setSeconds(30).build())
                    .build();

            Assignment solution = routing.solveWithParameters(searchParameters);

            if (solution == null) {
                throw new RuntimeException("No Solution Found");
            }

            List<TspRouteSolution> routeSolutionList = new ArrayList<>();
            int[] route = new int[distanceMatrix.length];
            long index = routing.start(0);
            for (int i = 0; i < route.length; i++) {
                int currentNode = manager.indexToNode(index);
                route[i] = currentNode;

                // Build the solution object, adding ETA information
                Location fromLocation = cityList.get(currentNode);
                Location toLocation = cityList.get(manager.indexToNode(solution.value(routing.nextVar(index))));
                double eta = etaList.get(i);  // Assuming the i-th index corresponds to the current point-to-point ETA

                routeSolutionList.add(new TspRouteSolution(fromLocation, toLocation, eta));
                index = solution.value(routing.nextVar(index));
            }

            logger.info("TSP solution found with ETA: {}", routeSolutionList);

            deleteDistanceMatrixEntries();
            deleteLocationEntries();
            return routeSolutionList;
        } catch (Exception e) {
            logger.error("Error solving TSP: ", e);
            throw new RuntimeException("Error solving TSP: " + e.getMessage(), e);
        }
    }

    // Function to fetch ETA from an external API (e.g., Google Distance Matrix API)
    private double fetchEta(Location from, Location to) {
        String apiUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + from.getLatitude() + "," + from.getLongitude() +
                "&destinations=" + to.getLatitude() + "," + to.getLongitude() +
                "&key=AIzaSyCZYUdwkaU3oyOswFK7reHKsT9Ab_SXtuQ";

        DistanceMatrixApiResponse response = restTemplate.getForObject(apiUrl, DistanceMatrixApiResponse.class);
        if (response != null && !response.getRows().isEmpty()) {
            // Assuming the first element contains the ETA
            return response.getRows().get(0).getElements().get(0).getDuration().getValue();
        }
        return 0; // Return 0 if no ETA is found
    }

    // Inner class to represent the solution with ETA
    public static class TspRouteSolution {
        private Location fromLocation;
        private Location toLocation;
        private double eta;  // ETA in seconds or minutes (depends on API)

        // Constructor, getters, and setters
        public TspRouteSolution(Location fromLocation, Location toLocation, double eta) {
            this.fromLocation = fromLocation;
            this.toLocation = toLocation;
            this.eta = eta;
        }

        // Getters and setters
        public Location getFromLocation() {
            return fromLocation;
        }

        public Location getToLocation() {
            return toLocation;
        }

        public double getEta() {
            return eta;
        }
    }

    // Class representing the Google Distance Matrix API response
    @Data
    private static class DistanceMatrixApiResponse {
        private List<Row> rows;

        // Inner class to represent the rows of the response
        @Data
        public static class Row {
            private List<Element> elements;
        }

        @Data
        public static class Element {
            private Duration duration;

            @Data
            public static class Duration {
                private double value;  // Duration in seconds

                public double getValue() {
                    return value;
                }
            }
        }
    }

    /*----------------------------------------Duration and Distance TSP Solution------------*/

    public List<DistanceMatrix> solveTspWithDuration(List<String> cityAddresses) {
        try {
            logger.info("Starting TSP solution 1 for cities: {}", cityAddresses);
            List<Location> cityList = locationRepository.findAllByAddressIn(cityAddresses);

            // Check if all input cities exist in the database
            List<String> foundCityNames = cityList.stream().map(Location::getAddress).collect(Collectors.toList());
            List<String> notFoundCityNames = cityAddresses.stream()
                    .filter(city -> !foundCityNames.contains(city))
                    .collect(Collectors.toList());

            if (!notFoundCityNames.isEmpty()) {
                logger.error("The following cities do not exist in the database: {}", notFoundCityNames);
                throw new IllegalArgumentException("Some cities in the input list do not exist in the database: " + notFoundCityNames);
            }

            int cityCount = cityList.size();
            logger.info("Number of cities: {}", cityCount);

            double[][] distanceMatrix = new double[cityCount][cityCount];

            for (int i = 0; i < cityCount; i++) {
                Location fromCity = cityList.get(i);
                List<DistanceMatrix> distanceList = distanceMatrixRepository.findByFromLocationId(fromCity);

                for (DistanceMatrix distance : distanceList) {
                    int toCityIndex = cityList.indexOf(distance.getToLocationId());
                    if (toCityIndex == -1) {
                        throw new IllegalArgumentException("Distance information is incomplete for some cities.");
                    }
                    distanceMatrix[i][toCityIndex] = distance.getDistance();
                }
            }

            logger.debug("Distance matrix: ");
            for (double[] row : distanceMatrix) {
                logger.debug(java.util.Arrays.toString(row));
            }

            RoutingIndexManager manager = new RoutingIndexManager(distanceMatrix.length, 1, 0);
            RoutingModel routing = new RoutingModel(manager);

            int transitCallbackIndex = routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                int fromNode = manager.indexToNode(fromIndex);
                int toNode = manager.indexToNode(toIndex);
                return (long) distanceMatrix[fromNode][toNode];
            });

            routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

            RoutingSearchParameters searchParameters = main.defaultRoutingSearchParameters().toBuilder()
                    .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                    .setLocalSearchMetaheuristic(LocalSearchMetaheuristic.Value.GUIDED_LOCAL_SEARCH)
                    .setTimeLimit(com.google.protobuf.Duration.newBuilder().setSeconds(30).build())
                    .build();

            Assignment solution = routing.solveWithParameters(searchParameters);

            if (solution == null) {
                throw new RuntimeException("No Solution Found");
            }

            // Create a list of DistanceMatrix objects representing the route
            List<DistanceMatrix> tspRoute = new ArrayList<>();
            long index = routing.start(0);
            for (int i = 0; i < cityList.size(); i++) {
                int fromCityIndex = manager.indexToNode(index);
                Location fromCity = cityList.get(fromCityIndex);

                index = solution.value(routing.nextVar(index));
                int toCityIndex = manager.indexToNode(index);
                Location toCity = cityList.get(toCityIndex);

                // Find the DistanceMatrix entry for the from and to locations
                DistanceMatrix distanceMatrixEntry = distanceMatrixRepository
                        .findByFromLocationIdAndToLocationId(fromCity, toCity)
                        .orElseThrow(() -> new RuntimeException("Distance information not found for route from " + fromCity.getAddress() + " to " + toCity.getAddress()));

                tspRoute.add(distanceMatrixEntry);
            }

            logger.info("TSP solution found with routes: {}", tspRoute);

            // Sort the route based on 'pickupTimeSlotStart' in descending order
            List<DistanceMatrix> sortedTspRoute = sortRouteByPickupTimeSlot(tspRoute);

            // Optionally, delete all entries from distance_matrix and location tables after processing
            deleteDistanceMatrixEntries();
            deleteLocationEntries();

            return tspRoute;
        } catch (Exception e) {
            logger.error("Error solving TSP: ", e);
            throw new RuntimeException("Error solving TSP: " + e.getMessage(), e);
        }
    }

    // Method to sort the TSP route by 'pickupTimeSlotStart' and 'pickupTimeSlotEnd' without reverseOrder
    private List<DistanceMatrix> sortRouteByPickupTimeSlot(List<DistanceMatrix> tspRoute) {
        tspRoute.sort((entry1, entry2) -> {
            // Extract pickup time slots from the 'fromLocationId' (assuming you want to sort by pickup times of 'from' locations)
            Location fromLocation1 = entry1.getFromLocationId();
            Location fromLocation2 = entry2.getFromLocationId();

            // Compare pickupTimeSlotStart first (ascending order)
            int startComparison = fromLocation1.getPickupTimeSlotStart().compareTo(fromLocation2.getPickupTimeSlotStart());

            if (startComparison != 0) {
                return startComparison;
            }

            // If pickupTimeSlotStart is equal, compare pickupTimeSlotEnd (ascending order)
            return fromLocation1.getPickupTimeSlotEnd().compareTo(fromLocation2.getPickupTimeSlotEnd());
        });

        return tspRoute;
    }

    /*---------------------------------resequenceRouteWithTimeSlots--------------------*/

    public List<DistanceMatrix> solveTspWithDurationV2(List<String> cityAddresses) {
        try {

            logger.info("Starting TSP solution TimeSlots for cities: {}", cityAddresses);
            List<Location> cityList = locationRepository.findAllByAddressIn(cityAddresses);

            // Check if all input cities exist in the database
            List<String> foundCityNames = cityList.stream().map(Location::getAddress).collect(Collectors.toList());
            List<String> notFoundCityNames = cityAddresses.stream()
                    .filter(city -> !foundCityNames.contains(city))
                    .collect(Collectors.toList());

            if (!notFoundCityNames.isEmpty()) {
                logger.error("The following cities do not exist in the database: {}", notFoundCityNames);
                throw new IllegalArgumentException("Some cities in the input list do not exist in the database: " + notFoundCityNames);
            }

            // Initial OR-Tools based sequencing
            List<DistanceMatrix> tspRoute = calculateTspSequence(cityList);

            // Sort the route by 'pickupTimeSlotStart' and 'pickupTimeSlotEnd'
//            List<DistanceMatrix> finalRoute = resequenceRouteWithTimeSlots(tspRoute, cityList);

//            logger.info("Final TSP route after sorting with time slots: {}", finalRoute);

            return tspRoute;

        } catch (Exception e) {
            logger.error("Error solving TSP: ", e);
            throw new RuntimeException("Error solving TSP: " + e.getMessage(), e);
        }
    }

    private List<DistanceMatrix> calculateTspSequence(List<Location> cityList) {
        int cityCount = cityList.size();
        double[][] distanceMatrix = new double[cityCount][cityCount];

        // Populate the distance matrix from the database
        for (int i = 0; i < cityCount; i++) {
            Location fromCity = cityList.get(i);
            List<DistanceMatrix> distanceList = distanceMatrixRepository.findByFromLocationId(fromCity);

            for (DistanceMatrix distance : distanceList) {
                int toCityIndex = cityList.indexOf(distance.getToLocationId());
//                if (toCityIndex == -1) {
//                    throw new IllegalArgumentException("Distance information is incomplete for some cities.");
//                }
                distanceMatrix[i][toCityIndex] = distance.getDistance();
            }
        }

        // Solve using OR-Tools
        RoutingIndexManager manager = new RoutingIndexManager(distanceMatrix.length, 1, 0);
        RoutingModel routing = new RoutingModel(manager);

        int transitCallbackIndex = routing.registerTransitCallback((long fromIndex, long toIndex) -> {
            int fromNode = manager.indexToNode(fromIndex);
            int toNode = manager.indexToNode(toIndex);
            return (long) distanceMatrix[fromNode][toNode];
        });

        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        RoutingSearchParameters searchParameters = main.defaultRoutingSearchParameters().toBuilder()
                .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PARALLEL_CHEAPEST_INSERTION)
                .setLocalSearchMetaheuristic(LocalSearchMetaheuristic.Value.SIMULATED_ANNEALING)
                .setTimeLimit(com.google.protobuf.Duration.newBuilder().setSeconds(1).build())
                .build();

        Assignment solution = routing.solveWithParameters(searchParameters);

        if (solution == null) {
            throw new RuntimeException("No Solution Found");
        }

        // Create the sequence from the solution
        List<DistanceMatrix> tspRoute = new ArrayList<>();
        long index = routing.start(0);
        for (int i = 0; i < cityList.size(); i++) {
            int fromCityIndex = manager.indexToNode(index);
            Location fromCity = cityList.get(fromCityIndex);

            index = solution.value(routing.nextVar(index));
            int toCityIndex = manager.indexToNode(index);
            Location toCity = cityList.get(toCityIndex);

            DistanceMatrix distanceMatrixEntry = distanceMatrixRepository
                    .findByFromLocationIdAndToLocationId(fromCity, toCity)
                    .orElse(null);

            tspRoute.add(distanceMatrixEntry);
        }

        return tspRoute;
    }

    private List<DistanceMatrix> resequenceRouteWithTimeSlots(List<DistanceMatrix> tspRoute, List<Location> cityList) {
        List<DistanceMatrix> finalRoute = new ArrayList<>();
        List<Location> resequenceList = new ArrayList<>();

        for (DistanceMatrix entry : tspRoute) {
            Location fromLocation = entry.getFromLocationId();

            if (fromLocation.getPickupTimeSlotStart() != null && fromLocation.getPickupTimeSlotEnd() != null) {
                // Add to final route and reset resequence list
                if (!resequenceList.isEmpty()) {
                    List<DistanceMatrix> resequenced = calculateTspSequence(resequenceList);
                    finalRoute.addAll(resequenced);
                    resequenceList.clear();
                }
                finalRoute.add(entry);
            } else {
                // Add to resequence list
                resequenceList.add(fromLocation);
            }
        }

        // Resequence any remaining locations
        if (!resequenceList.isEmpty()) {
            List<DistanceMatrix> resequenced = calculateTspSequence(resequenceList);
            finalRoute.addAll(resequenced);
        }

        // Optionally, delete all entries from distance_matrix and location tables after processing
        deleteDistanceMatrixEntries();
        deleteLocationEntries();

        return finalRoute;
    }

}
