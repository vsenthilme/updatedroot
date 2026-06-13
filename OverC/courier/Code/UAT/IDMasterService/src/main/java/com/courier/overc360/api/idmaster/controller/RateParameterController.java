package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.rateparameter.AddRateParameter;
import com.courier.overc360.api.idmaster.primary.model.rateparameter.RateParameter;
import com.courier.overc360.api.idmaster.primary.model.rateparameter.UpdateRateParameter;
import com.courier.overc360.api.idmaster.replica.model.rateparameter.FindRateParameter;
import com.courier.overc360.api.idmaster.replica.model.rateparameter.ReplicaRateParameter;
import com.courier.overc360.api.idmaster.service.RateParameterService;
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
@Api(tags = {"RateParameter"}, value = "RateParameter operations related to RateParameterController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "RateParameter", description = "Operations related to RateParameter")})
@RequestMapping("/rateParameter")
@RestController
public class RateParameterController {

    @Autowired
    RateParameterService rateParameterService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create RateParameter
    @ApiOperation(response = RateParameter.class, value = "Create new RateParameter") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postRateParameter(@Valid @RequestBody AddRateParameter addRateParameter, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        RateParameter newRateParameter = rateParameterService.createRateParameter(addRateParameter, loginUserID);
        return new ResponseEntity<>(newRateParameter, HttpStatus.OK);
    }

    // Update RateParameter
    @ApiOperation(response = RateParameter.class, value = "Update RateParameter") // label for swagger
    @PatchMapping("/{rateParameterId}")
    public ResponseEntity<?> patchRateParameter(@PathVariable String rateParameterId, @RequestParam String companyId, @RequestParam String languageId,
                                                @RequestParam String loginUserID, @RequestBody UpdateRateParameter updateRateParameter)
            throws IllegalAccessException, InvocationTargetException, ParseException, IOException, CsvException {
        RateParameter updatedRateParameter = rateParameterService.updateRateParameter(companyId, rateParameterId, languageId, updateRateParameter, loginUserID);
        return new ResponseEntity<>(updatedRateParameter, HttpStatus.OK);
    }

    // Delete RateParameter
    @ApiOperation(response = RateParameter.class, value = "Delete RateParameter") // label for swagger
    @DeleteMapping("/{rateParameterId}")
    public ResponseEntity<?> deleteRateParameter(@PathVariable String rateParameterId, @RequestParam String companyId, @RequestParam String languageId,
                                                 @RequestParam String loginUserID) {
        rateParameterService.deleteRateParameter(rateParameterId, companyId, languageId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------*/

    // Get All RateParameter Details
    @ApiOperation(response = ReplicaRateParameter.class, value = "Get all RateParameter Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllRateParameterDetails() {
        List<ReplicaRateParameter> rateParameterList = rateParameterService.getAllRateParameter();
        return new ResponseEntity<>(rateParameterList, HttpStatus.OK);
    }

    // Get RateParameter
    @ApiOperation(response = ReplicaRateParameter.class, value = "Get a RateParameter") // label for swagger
    @GetMapping("/{rateParameterId}")
    public ResponseEntity<?> getRateParameter(@PathVariable String rateParameterId, @RequestParam String companyId, @RequestParam String languageId) {
        ReplicaRateParameter dbRateParameter = rateParameterService.replicaGetRateParameter(rateParameterId, companyId, languageId);
        return new ResponseEntity<>(dbRateParameter, HttpStatus.OK);
    }

    // Find RateParameter
    @ApiOperation(response = ReplicaRateParameter.class, value = "Find RateParameter") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findRateParameter(@Valid @RequestBody FindRateParameter findRateParameter) throws Exception {
        List<ReplicaRateParameter> rateParameterList = rateParameterService.findRateParameter(findRateParameter);
        return new ResponseEntity<>(rateParameterList, HttpStatus.OK);
    }

}
