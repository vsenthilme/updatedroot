package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.districtMapping.AddDistrictMapping;
import com.courier.overc360.api.idmaster.primary.model.districtMapping.DistrictMapping;
import com.courier.overc360.api.idmaster.primary.model.districtMapping.UpdateDistrictMapping;
import com.courier.overc360.api.idmaster.replica.model.districtMapping.FindDistrictMapping;
import com.courier.overc360.api.idmaster.replica.model.districtMapping.ReplicaDistrictMapping;
import com.courier.overc360.api.idmaster.service.DistrictMappingService;
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
@Api(tags = {"DistrictMapping"}, value = "DistrictMapping Operations related to DistrictController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "DistrictMapping", description = "Operations related to DistrictMapping")})
@RequestMapping("/districtMapping")
@RestController
public class DistrictMappingController {

    @Autowired
    DistrictMappingService districtMappingService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create DistrictMapping
    @ApiOperation(response = DistrictMapping.class, value = "Create new DistrictMapping") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postDistrictMapping(@Valid @RequestBody AddDistrictMapping addDistrictMapping, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        DistrictMapping districtMapping = districtMappingService.createDistrictMapping(addDistrictMapping, loginUserID);
        return new ResponseEntity<>(districtMapping, HttpStatus.OK);
    }

    // Update DistrictMapping
    @ApiOperation(response = DistrictMapping.class, value = "Update DistrictMapping") // label for swagger
    @PatchMapping("/{partnerId}")
    public ResponseEntity<?> patchDistrictMapping(@PathVariable String partnerId,@RequestParam String languageId,
                                                  @RequestParam String companyId, @RequestParam String districtId, @RequestParam String loginUserID, @RequestBody UpdateDistrictMapping updateDistrictMapping)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        DistrictMapping districtMapping = districtMappingService.updateDistrictMapping(languageId, companyId, partnerId, districtId, updateDistrictMapping, loginUserID);
        return new ResponseEntity<>(districtMapping, HttpStatus.OK);
    }

    // Delete DistrictMapping
    @ApiOperation(response = DistrictMapping.class, value = "Delete DistrictMapping") // label for swagger
    @DeleteMapping("/{partnerId}")
    public ResponseEntity<?> deleteDistrictMapping(@PathVariable String partnerId,  @RequestParam String companyId,
                                                   @RequestParam String languageId, @RequestParam String districtId, @RequestParam String loginUserID) {
        districtMappingService.deleteDistrictMapping(languageId, companyId,  partnerId,  districtId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All DistrictMapping Details
    @ApiOperation(response = ReplicaDistrictMapping.class, value = "Get all DistrictMapping Details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllDistrictMappingDetails() {
        List<ReplicaDistrictMapping> districtMappingList = districtMappingService.getAllDistrictMappings();
        return new ResponseEntity<>(districtMappingList, HttpStatus.OK);
    }

    // Get DistrictMapping
    @ApiOperation(response = ReplicaDistrictMapping.class, value = "Get a DistrictMapping") // label for swagger
    @GetMapping("/{partnerId}")
    public ResponseEntity<?> getDistrictMapping(@PathVariable String partnerId, @RequestParam String languageId, @RequestParam String companyId,
                                               @RequestParam String districtId) {
        ReplicaDistrictMapping districtMapping = districtMappingService.replicaGetDistrictMapping(languageId, companyId, partnerId, districtId);
        return new ResponseEntity<>(districtMapping, HttpStatus.OK);
    }

    // Find DistrictMapping
    @ApiOperation(response = DistrictMapping.class, value = "Find DistrictMapping") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findDistrictMapping(@Valid @RequestBody FindDistrictMapping findDistrictMapping) throws Exception {
        List<ReplicaDistrictMapping> districtMappingList = districtMappingService.findDistrictMappings(findDistrictMapping);
        return new ResponseEntity<>(districtMappingList, HttpStatus.OK);
    }


}
