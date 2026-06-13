package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.maps.DistanceMatrix;
import com.courier.overc360.api.midmile.primary.model.maps.DistanceMatrixRequest;
import com.courier.overc360.api.midmile.primary.model.maps.Location;
import com.courier.overc360.api.midmile.service.DistanceMatrixService;
import com.courier.overc360.api.midmile.service.DistanceMatrixTspService;
import com.courier.overc360.api.midmile.service.TspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/distanceMatrix")
public class DistanceMatrixTspController {

    @Autowired
    private DistanceMatrixService distanceMatrix2Service;

    @Autowired
    private DistanceMatrixTspService distanceMatrixTsp2Service;

//    @PostMapping("/solve-tsp-with-eta")
//    public ResponseEntity<?> solveTspWithDistanceMatrix(@RequestBody DistanceMatrixRequest distanceMatrixRequest) {
//
//        //distanceMatrix2Service.calculateAndSaveDistanceMatrix(distanceMatrixRequest.getOrigins(), distanceMatrixRequest.getDestinations());
//
//        List<TspService.TspRouteSolution> solution = distanceMatrixTsp2Service.calculateDistanceAndSolveTsp(distanceMatrixRequest.getOrigins(), distanceMatrixRequest.getDestinations());
//        return ResponseEntity.ok(solution);
//    }
//
//    @PostMapping("/solve-tsp")
//    public ResponseEntity<?> solveTspWithDistanceMatrixWithoutEta(@RequestBody DistanceMatrixRequest distanceMatrixRequest) {
//
//        distanceMatrix2Service.calculateAndSaveDistanceMatrix(distanceMatrixRequest.getOrigins(), distanceMatrixRequest.getDestinations());
//
//        List<Location> solution = distanceMatrixTsp2Service.calculateDistanceAndSolveTspWithoutEta(distanceMatrixRequest.getOrigins());
//        return ResponseEntity.ok(solution);
//    }

//    @PostMapping("/solve-tsp-with-duration")
//    public ResponseEntity<?> solveTspWithDistanceMatrixWithDuration(@RequestBody List<DistanceMatrixRequest> distanceMatrixRequest) {
//
////        // Assuming you're dealing with multiple DistanceMatrixRequest objects in the list
////        List<String> origins = new ArrayList<>();
////        List<String> destinations = new ArrayList<>();
////
////        // Extracting origins and destinations from each DistanceMatrixRequest in the list
////        for (DistanceMatrixRequest request : distanceMatrixRequest) {
////            origins.addAll(request.getOrigins());
////            destinations.addAll(request.getDestinations());
////        }
//
//        List<DistanceMatrix> solution = distanceMatrixTsp2Service.calculateDistanceAndSolveTspWithDuration(distanceMatrixRequest);
//        return ResponseEntity.ok(solution);
//    }

    @PostMapping("/solve-tsp-with-duration-v2")
    public ResponseEntity<?> solveTspWithDistanceMatrixWithDurationV2(@RequestBody List<DistanceMatrixRequest> distanceMatrixRequest) {

        List<DistanceMatrix> solution = distanceMatrixTsp2Service.calculateDistanceAndSolveTspWithDurationV2(distanceMatrixRequest);
        return ResponseEntity.ok(solution);
    }
}
