package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.iata.AddIata;
import com.courier.overc360.api.idmaster.primary.model.iata.Iata;
import com.courier.overc360.api.idmaster.primary.model.iata.UpdateIata;
import com.courier.overc360.api.idmaster.replica.model.iata.FindIata;
import com.courier.overc360.api.idmaster.replica.model.iata.ReplicaIata;
import com.courier.overc360.api.idmaster.service.IataService;
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
import java.text.ParseException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"Iata"}, value = "Iata operations related to IataController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Iata", description = "Operations related to Iata")})
@RequestMapping("/iata")
@RestController

public class IataController {
    @Autowired
    IataService iataService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/
    // Create Iata
    @ApiOperation(response = Iata.class, value = "Create new Iata") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postIata(@Valid @RequestBody AddIata addIata, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Iata newIata = iataService.createIata(addIata, loginUserID);
        return new ResponseEntity<>(newIata, HttpStatus.OK);
    }

    // Update Iata
    @ApiOperation(response = Iata.class, value = "Update Iata") // label for swagger
    @PatchMapping("/{originCode}")
    public ResponseEntity<?> patchIata(@PathVariable String originCode, @RequestParam String companyId, @RequestParam String languageId, @RequestParam String origin,
                                       @RequestParam String loginUserID, @RequestBody UpdateIata updateIata)
            throws IllegalAccessException, InvocationTargetException, ParseException, IOException, CsvException {
        Iata updatedIata = iataService.updateIata(companyId, languageId, origin, originCode, updateIata, loginUserID);
        return new ResponseEntity<>(updatedIata, HttpStatus.OK);
    }

    // Delete Iata
    @ApiOperation(response = Iata.class, value = "Delete Iata") // label for swagger
    @DeleteMapping("/{originCode}")
    public ResponseEntity<?> deleteIata(@PathVariable String originCode, @RequestParam String companyId, @RequestParam String languageId, @RequestParam String origin,
                                        @RequestParam String loginUserID) {
        iataService.deleteIata( companyId, languageId, origin, originCode, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------*/
    // Get All Iata Details
    @ApiOperation(response = ReplicaIata.class, value = "Get all Iata Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllIataDetails() {
        List<ReplicaIata> iataList = iataService.getAllIata();
        return new ResponseEntity<>(iataList, HttpStatus.OK);
    }

    // Get Iata
    @ApiOperation(response = ReplicaIata.class, value = "Get a Iata") // label for swagger
    @GetMapping("/{originCode}")
    public ResponseEntity<?> getIata(@PathVariable String originCode, @RequestParam String companyId, @RequestParam String languageId, @RequestParam String origin) {
        ReplicaIata dbIata = iataService.replicaGetIata( companyId, languageId, origin, originCode);
        return new ResponseEntity<>(dbIata, HttpStatus.OK);
    }

    // Find Iata
    @ApiOperation(response = ReplicaIata.class, value = "Find Iata") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findIata(@Valid @RequestBody FindIata findIata) throws Exception {
        List<ReplicaIata> iataList = iataService.findIata(findIata);
        return new ResponseEntity<>(iataList, HttpStatus.OK);
    }

}
