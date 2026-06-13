package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.airportcode.AddAirportCode;
import com.courier.overc360.api.idmaster.primary.model.airportcode.AirportCode;
import com.courier.overc360.api.idmaster.primary.model.airportcode.UpdateAirportCode;
import com.courier.overc360.api.idmaster.replica.model.airportcode.FindAirportCode;
import com.courier.overc360.api.idmaster.replica.model.airportcode.ReplicaAirportCode;
import com.courier.overc360.api.idmaster.service.AirportCodeService;
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
@Api(tags = {"AirportCode"}, value = "AirportCode  Operations related to AirportCodeController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "AirportCode", description = "Operations related to AirportCode")})
@RequestMapping("/airportCode")
@RestController
public class AirportCodeController {

    @Autowired
    AirportCodeService airportCodeService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/
    // Create AirportCode
    @ApiOperation(response = AirportCode.class, value = "Create AirportCode") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postAirportCode(@Valid @RequestBody AddAirportCode addAirportCode, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        AirportCode airportCode = airportCodeService.createAirportCode(addAirportCode, loginUserID);
        return new ResponseEntity<>(airportCode, HttpStatus.OK);
    }

    // Update AirportCode
    @ApiOperation(response = AirportCode.class, value = "Update AirportCode") // label for swagger
    @PatchMapping("/{airportCode}")
    public ResponseEntity<?> patchAirportCode(@PathVariable String airportCode, @RequestParam String companyId, @RequestParam String languageId,
                                              @RequestParam String loginUserID, @RequestBody UpdateAirportCode updateAirportCode)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        AirportCode updatedAirportCode =
                airportCodeService.updateAirportCode(companyId, languageId, airportCode, loginUserID, updateAirportCode);
        return new ResponseEntity<>(updatedAirportCode, HttpStatus.OK);
    }

    // Delete AirportCode
    @ApiOperation(response = AirportCode.class, value = "Delete AirportCode") // label for swagger
    @DeleteMapping("/{airportCode}")
    public ResponseEntity<?> deleteAirportCode(@PathVariable String airportCode, @RequestParam String languageId, @RequestParam String companyId,
                                               @RequestParam String loginUserID) {
        airportCodeService.deleteAirportCode(companyId, languageId, airportCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/
    // Get All AirportCode Details
    @ApiOperation(response = ReplicaAirportCode.class, value = "Get all ReplicaAirportCode details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllAirportCodes() {
        List<ReplicaAirportCode> airportCodeList = airportCodeService.getAllAirportCodes();
        return new ResponseEntity<>(airportCodeList, HttpStatus.OK);
    }

    // Get AirportCode
    @ApiOperation(response = ReplicaAirportCode.class, value = "Get a ReplicaAirportCode") // label for swagger
    @GetMapping("/{airportCode}")
    public ResponseEntity<?> getAirportCode(@PathVariable String airportCode, @RequestParam String languageId, @RequestParam String companyId) {
        ReplicaAirportCode dbAirportCode = airportCodeService.getReplicaAirportCode(companyId, languageId, airportCode);
        return new ResponseEntity<>(dbAirportCode, HttpStatus.OK);
    }

    // Find AirportCodes
    @ApiOperation(response = ReplicaAirportCode.class, value = "Find ReplicaAirportCode") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findAirportCodes(@Valid @RequestBody FindAirportCode findAirportCode) throws Exception {
        List<ReplicaAirportCode> airportCodeList = airportCodeService.findAirportCodes(findAirportCode);
        return new ResponseEntity<>(airportCodeList, HttpStatus.OK);
    }

}
