package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.countryMapping.AddCountryMapping;
import com.courier.overc360.api.idmaster.primary.model.countryMapping.CountryMapping;
import com.courier.overc360.api.idmaster.primary.model.countryMapping.UpdateCountryMapping;
import com.courier.overc360.api.idmaster.replica.model.countryMapping.FindCountryMapping;
import com.courier.overc360.api.idmaster.replica.model.countryMapping.ReplicaCountryMapping;
import com.courier.overc360.api.idmaster.service.CountryMappingService;
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
@Api(tags = {"CountryMapping"}, value = "CountryMapping Operations related to CountryController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "CountryMapping", description = "Operations related to CountryMapping")})
@RequestMapping("/countryMapping")
@RestController
public class CountryMappingController {
    @Autowired
    CountryMappingService countryMappingService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create CountryMapping
    @ApiOperation(response = CountryMapping.class, value = "Create new CountryMapping") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postCountryMapping(@Valid @RequestBody AddCountryMapping addCountryMapping, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CountryMapping countryMapping = countryMappingService.createCountryMapping(addCountryMapping, loginUserID);
        return new ResponseEntity<>(countryMapping, HttpStatus.OK);
    }

    // Update CountryMapping
    @ApiOperation(response = CountryMapping.class, value = "Update CountryMapping") // label for swagger
    @PatchMapping("/{partnerId}")
    public ResponseEntity<?> patchCountryMapping(@PathVariable String partnerId, @RequestParam String countryId, @RequestParam String languageId,
                                                  @RequestParam String companyId, @RequestParam String loginUserID, @RequestBody UpdateCountryMapping updateCountryMapping)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CountryMapping countryMapping = countryMappingService.updateCountryMapping(languageId, companyId, countryId, partnerId, updateCountryMapping, loginUserID);
        return new ResponseEntity<>(countryMapping, HttpStatus.OK);
    }

    // Delete CountryMapping
    @ApiOperation(response = CountryMapping.class, value = "Delete CountryMapping") // label for swagger
    @DeleteMapping("/{partnerId}")
    public ResponseEntity<?> deleteCountryMapping(@PathVariable String partnerId, @RequestParam String countryId, @RequestParam String companyId,
                                                   @RequestParam String languageId, @RequestParam String loginUserID) {
        countryMappingService.deleteCountryMapping(languageId, companyId, countryId, partnerId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All CountryMapping Details
    @ApiOperation(response = ReplicaCountryMapping.class, value = "Get all CountryMapping Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllCountryMappingDetails() {
        List<ReplicaCountryMapping> countryMappingList = countryMappingService.getAllCountryMappings();
        return new ResponseEntity<>(countryMappingList, HttpStatus.OK);
    }

    // Get CountryMapping
    @ApiOperation(response = ReplicaCountryMapping.class, value = "Get a CountryMapping") // label for swagger
    @GetMapping("/{partnerId}")
    public ResponseEntity<?> getCountryMapping(@PathVariable String partnerId, @RequestParam String countryId,
                                               @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaCountryMapping countryMapping = countryMappingService.replicaGetCountryMapping(languageId, companyId, countryId, partnerId);
        return new ResponseEntity<>(countryMapping, HttpStatus.OK);
    }

    // Find CountryMapping
    @ApiOperation(response = CountryMapping.class, value = "Find CountryMapping") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findCountryMapping(@Valid @RequestBody FindCountryMapping findCountryMapping) throws Exception {
        List<ReplicaCountryMapping> countryMappingList = countryMappingService.findCountryMappings(findCountryMapping);
        return new ResponseEntity<>(countryMappingList, HttpStatus.OK);
    }

}
