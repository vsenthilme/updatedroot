package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.city.AddCity;
import com.courier.overc360.api.idmaster.primary.model.city.UpdateCity;
import com.courier.overc360.api.idmaster.primary.model.city.City;
import com.courier.overc360.api.idmaster.replica.model.city.FindCity;
import com.courier.overc360.api.idmaster.replica.model.city.ReplicaCity;
import com.courier.overc360.api.idmaster.service.CityService;
import com.opencsv.exceptions.CsvException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"City"}, value = "City Operations related to CityController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "City", description = "Operations related to City")})
@RequestMapping("/city")
@RestController
public class CityController {

    @Autowired
    CityService cityService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create City
    @ApiOperation(response = City.class, value = "Create City") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postCity(@Valid @RequestBody AddCity newCity, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        City createdCity = cityService.createCity(newCity, loginUserID);
        return new ResponseEntity<>(createdCity, HttpStatus.OK);
    }

    // Update City
    @ApiOperation(response = City.class, value = "Update City") // label for swagger
    @PatchMapping("/{cityId}")
    public ResponseEntity<?> patchCity(@PathVariable String cityId, @RequestParam String languageId, @RequestParam String companyId,
                                       @RequestParam String countryId, @RequestParam String provinceId, @RequestParam String districtId,
                                       @RequestParam String loginUserID, @RequestBody UpdateCity updateCity)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        City updatedCity = cityService.updateCity(languageId, companyId, countryId, provinceId, districtId, cityId, loginUserID, updateCity);
        return new ResponseEntity<>(updatedCity, HttpStatus.OK);
    }

    // Delete City
    @ApiOperation(response = City.class, value = "Delete City") // label for swagger
    @DeleteMapping("/{cityId}")
    public ResponseEntity<?> deleteCity(@PathVariable String cityId, @RequestParam String languageId, @RequestParam String companyId,
                                        @RequestParam String countryId, @RequestParam String provinceId, @RequestParam String districtId, @RequestParam String loginUserID) {
        cityService.deleteCity(languageId, companyId, countryId, provinceId, districtId, cityId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All City Details
    @ApiOperation(response = ReplicaCity.class, value = "Get all City details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllCities() {
        List<ReplicaCity> cityList = cityService.getAllCities();
        return new ResponseEntity<>(cityList, HttpStatus.OK);
    }

    // Get City
    @ApiOperation(response = ReplicaCity.class, value = "Get a City") // label for swagger
    @GetMapping("/{cityId}")
    public ResponseEntity<?> getCity(@PathVariable String cityId, @RequestParam String languageId, @RequestParam String companyId,
                                     @RequestParam String countryId, @RequestParam String provinceId, @RequestParam String districtId) {

        ReplicaCity city = cityService.replicaGetCity(languageId, companyId, countryId, provinceId, districtId, cityId);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }
    // Find City
    @ApiOperation(response = ReplicaCity.class, value = "Find City") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findCity(@RequestBody FindCity findCity) throws Exception {
        List<ReplicaCity> createdCity = cityService.findCity(findCity);
        return new ResponseEntity<>(createdCity, HttpStatus.OK);
    }

}
