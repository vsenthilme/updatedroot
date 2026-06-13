package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.courierpartner.AddCourierPartner;
import com.courier.overc360.api.idmaster.primary.model.courierpartner.CourierPartner;
import com.courier.overc360.api.idmaster.primary.model.courierpartner.UpdateCourierPartner;
import com.courier.overc360.api.idmaster.replica.model.courierpartner.FindCourierPartner;
import com.courier.overc360.api.idmaster.replica.model.courierpartner.ReplicaCourierPartner;
import com.courier.overc360.api.idmaster.service.CourierPartnerService;
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
@Api(tags = {"CourierPartner"}, value = "CourierPartner Operations related to CourierPartnerController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "CourierPartner", description = "Operations related to CourierPartner")})
@RequestMapping("/courierPartner")
@RestController

public class CourierPartnerController {

    @Autowired
    private CourierPartnerService courierPartnerService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    //Create
    @ApiOperation(response = CourierPartner.class, value = "Create New CourierPartner")
    @PostMapping("")
    public ResponseEntity<?> createCourierPartner(@Valid @RequestBody AddCourierPartner addCourierPartner, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CourierPartner newCourierPartner = courierPartnerService.createCourierPartner(addCourierPartner, loginUserID);
        return new ResponseEntity<>(newCourierPartner, HttpStatus.OK);
    }

    ///Update
    @ApiOperation(response = CourierPartner.class, value = "Update CourierPartner")
    @PatchMapping("/{courierPartnerId}")
    public ResponseEntity<?> patchCourierPartner(@PathVariable String courierPartnerId, @RequestParam String companyId, @RequestParam String languageId,
                                                 @RequestParam String partnerId, @RequestParam String loginUserID, @RequestBody UpdateCourierPartner updateCourierPartner)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CourierPartner updatedCourierPartner = courierPartnerService.updateCourierPartner(companyId, languageId, courierPartnerId, partnerId,
                updateCourierPartner, loginUserID);
        return new ResponseEntity<>(updatedCourierPartner, HttpStatus.OK);
    }

    //Delete
    @ApiOperation(response = CourierPartner.class, value = "Delete CourierPartner")
    @DeleteMapping("/{courierPartnerId}")
    public ResponseEntity<?> deleteCourierPartner(@PathVariable String courierPartnerId, @RequestParam String companyId, @RequestParam String languageId,
                                                  @RequestParam String partnerId, @RequestParam String loginUserID) {
        courierPartnerService.deleteCourierPartner(companyId, languageId, courierPartnerId, partnerId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All CourierPartner Details
    @ApiOperation(response = ReplicaCourierPartner.class, value = "Get all CourierPartner Details")
    @GetMapping("")
    public ResponseEntity<?> getAllCourierPartnerDetails() {
        List<ReplicaCourierPartner> courierPartnerList = courierPartnerService.getAll();
        return new ResponseEntity<>(courierPartnerList, HttpStatus.OK);
    }

    //Get CourierPartner
    @ApiOperation(response = ReplicaCourierPartner.class, value = "Get a CourierPartner")
    @GetMapping("/{courierPartnerId}")
    public ResponseEntity<?> getCourierPartner(@PathVariable String courierPartnerId, @RequestParam String companyId,
                                               @RequestParam String partnerId, @RequestParam String languageId) {
        ReplicaCourierPartner dbCourierPartner = courierPartnerService.getReplicaCourierPartner(companyId, languageId, courierPartnerId, partnerId);
        return new ResponseEntity<>(dbCourierPartner, HttpStatus.OK);
    }

    //Find CourierPartner
    @ApiOperation(response = ReplicaCourierPartner.class, value = "Find CourierPartner")
    @PostMapping("/find")
    public ResponseEntity<?> findCourierPartner(@Valid @RequestBody FindCourierPartner findCourierPartner) throws Exception {
        List<ReplicaCourierPartner> courierPartnerList = courierPartnerService.findCourierPartner(findCourierPartner);
        return new ResponseEntity<>(courierPartnerList, HttpStatus.OK);
    }
}

