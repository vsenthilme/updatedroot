package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.citymapping.AddCityMapping;
import com.courier.overc360.api.idmaster.primary.model.citymapping.CityMapping;
import com.courier.overc360.api.idmaster.primary.model.citymapping.UpdateCityMapping;
import com.courier.overc360.api.idmaster.replica.model.citymapping.FindCityMapping;
import com.courier.overc360.api.idmaster.replica.model.citymapping.ReplicaCityMapping;
import com.courier.overc360.api.idmaster.service.CityMappingService;
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
@Api(tags = {"CityMapping"}, value = "CityMapping Operations related to CityController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "CityMapping", description = "Operations related to CityMapping")})
@RequestMapping("/cityMapping")
@RestController
public class CityMappingController {

    @Autowired
    CityMappingService cityMappingService;

    // Create CityMapping
    @ApiOperation(response = CityMapping.class, value = "Create new CityMapping") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postCityMapping(@Valid @RequestBody AddCityMapping addCityMapping, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CityMapping cityMapping = cityMappingService.createCityMapping(addCityMapping, loginUserID);
        return new ResponseEntity<>(cityMapping, HttpStatus.OK);
    }

    // Update CityMapping
    @ApiOperation(response = CityMapping.class, value = "Update CityMapping") // label for swagger
    @PatchMapping("/{partnerId}")
    public ResponseEntity<?> patchCityMapping(@PathVariable String partnerId, @RequestParam String cityId, @RequestParam String languageId,
                                                  @RequestParam String companyId, @RequestParam String loginUserID, @RequestBody UpdateCityMapping updateCityMapping)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CityMapping cityMapping = cityMappingService.updateCityMapping(languageId, companyId, cityId, partnerId, updateCityMapping, loginUserID);
        return new ResponseEntity<>(cityMapping, HttpStatus.OK);
    }

    // Delete CityMapping
    @ApiOperation(response = CityMapping.class, value = "Delete CityMapping") // label for swagger
    @DeleteMapping("/{partnerId}")
    public ResponseEntity<?> deleteCityMapping(@PathVariable String partnerId, @RequestParam String cityId, @RequestParam String companyId,
                                                   @RequestParam String languageId, @RequestParam String loginUserID) {
        cityMappingService.deleteCityMapping(languageId, companyId, cityId, partnerId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get All CityMapping Details
    @ApiOperation(response = ReplicaCityMapping.class, value = "Get all CityMapping Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllCityMappingDetails() {
        List<ReplicaCityMapping> cityMappingList = cityMappingService.getAllCityMappings();
        return new ResponseEntity<>(cityMappingList, HttpStatus.OK);
    }

    // Get CityMapping
    @ApiOperation(response = ReplicaCityMapping.class, value = "Get a CityMapping") // label for swagger
    @GetMapping("/{partnerId}")
    public ResponseEntity<?> getCityMapping(@PathVariable String partnerId, @RequestParam String cityId,
                                            @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaCityMapping cityMapping = cityMappingService.replicaGetCityMapping(languageId, companyId, cityId, partnerId);
        return new ResponseEntity<>(cityMapping, HttpStatus.OK);
    }

    // Find CityMapping
    @ApiOperation(response = ReplicaCityMapping.class, value = "Find CityMapping") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findCityMapping(@Valid @RequestBody FindCityMapping findCityMapping) throws Exception {
        List<ReplicaCityMapping> cityMappingList = cityMappingService.findCityMappings(findCityMapping);
        return new ResponseEntity<>(cityMappingList, HttpStatus.OK);
    }

}
