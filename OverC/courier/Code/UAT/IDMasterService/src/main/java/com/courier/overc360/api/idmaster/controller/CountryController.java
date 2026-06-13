package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.replica.model.country.ReplicaCountry;
import com.opencsv.exceptions.CsvException;
import com.courier.overc360.api.idmaster.primary.model.country.AddCountry;
import com.courier.overc360.api.idmaster.primary.model.country.Country;
import com.courier.overc360.api.idmaster.primary.model.country.UpdateCountry;
import com.courier.overc360.api.idmaster.replica.model.country.FindCountry;
import com.courier.overc360.api.idmaster.service.CountryService;
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
@Api(tags = {"Country"}, value = "Country country related to CountryController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Country", description = "Operations related to Country")})
@RequestMapping("/country")
@RestController
public class CountryController {

    @Autowired
    CountryService countryService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create Country
    @ApiOperation(response = Country.class, value = "Create new Country") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postCountry(@Valid @RequestBody AddCountry addCountry, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Country newCountry = countryService.createCountry(addCountry, loginUserID);
        return new ResponseEntity<>(newCountry, HttpStatus.OK);
    }

    // Update Country
    @ApiOperation(response = Country.class, value = "Update Country") // label for swagger
    @PatchMapping("/{countryId}")
    public ResponseEntity<?> patchCountry(@PathVariable String countryId, @RequestParam String languageId, @RequestParam String companyId,
                                          @RequestParam String loginUserID, @RequestBody UpdateCountry updateCountry)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Country updatedCountry = countryService.updateCountry(languageId, companyId, countryId, updateCountry, loginUserID);
        return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
    }

    // Delete Country
    @ApiOperation(response = Country.class, value = "Delete Country") // label for swagger
    @DeleteMapping("/{countryId}")
    public ResponseEntity<?> deleteCountry(@PathVariable String countryId, @RequestParam String languageId,
                                           @RequestParam String companyId, @RequestParam String loginUserID) {
        countryService.deleteCountry(languageId, companyId, countryId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All Country Details
    @ApiOperation(response = ReplicaCountry.class, value = "Get all Country Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllCountryDetails() {
        List<ReplicaCountry> countryList = countryService.getAllCountries();
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

    // Get Country
    @ApiOperation(response = ReplicaCountry.class, value = "Get a Country") // label for swagger
    @GetMapping("/{countryId}")
    public ResponseEntity<?> getCountry(@PathVariable String countryId, @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaCountry dbCountry = countryService.replicaGetCountry(languageId, companyId, countryId);
        return new ResponseEntity<>(dbCountry, HttpStatus.OK);
    }

    // Find Country
    @ApiOperation(response = ReplicaCountry.class, value = "Find Country") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findCountry(@Valid @RequestBody FindCountry findCountry) throws Exception {
        List<ReplicaCountry> countryList = countryService.findCountries(findCountry);
        return new ResponseEntity<>(countryList, HttpStatus.OK);
    }

}
