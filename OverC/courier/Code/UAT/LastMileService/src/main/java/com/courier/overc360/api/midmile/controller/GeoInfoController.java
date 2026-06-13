package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.maps.GeoInfo;
import com.courier.overc360.api.midmile.service.GeoInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geoinfo")
public class GeoInfoController {

    @Autowired
    private GeoInfoService geoInfoService;

    @PostMapping("/get")
    public ResponseEntity<?> getGeoInfo(@RequestBody GeoInfo geoInfo) {

        // Handle Geocoding (Address to lat & lng)
        if (geoInfo.getAddress() != null) {
            GeoInfo response = geoInfoService.geoCode(geoInfo.getAddress());
            return ResponseEntity.ok(response);
        }

        // Handle Reverse Geocoding (lat & lng to Address)
        if (geoInfo.getLongitude() != null && geoInfo.getLatitude()!= null) {
            GeoInfo response = geoInfoService.reverseGeocode(geoInfo.getLatitude(), geoInfo.getLongitude());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(400).body("Please provide either an address or both latitude and longitude.");
    }
}
