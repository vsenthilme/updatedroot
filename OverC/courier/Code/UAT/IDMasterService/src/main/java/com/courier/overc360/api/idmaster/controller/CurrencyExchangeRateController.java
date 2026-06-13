package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.currencyExchangeRate.AddCurrencyExchangeRate;
import com.courier.overc360.api.idmaster.primary.model.currencyExchangeRate.CurrenyExchangeRate;
import com.courier.overc360.api.idmaster.primary.model.currencyExchangeRate.UpdateCurrencyExchangeRate;
import com.courier.overc360.api.idmaster.replica.model.currencyExchangeRate.FindCurrencyExchangeRate;
import com.courier.overc360.api.idmaster.replica.model.currencyExchangeRate.ReplicaCurrencyExchangeRate;
import com.courier.overc360.api.idmaster.service.CurrencyExchangeRateService;
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
@Api(tags = {"CurrencyExchangeRate"}, value = "CurrencyExchangeRate  related to CurrencyExchangeRateController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "CurrencyExchangeRate", description = "Operations related to CurrencyExchangeRate")})
@RequestMapping("/currencyExchangeRate")
@RestController
public class CurrencyExchangeRateController {

    @Autowired
    CurrencyExchangeRateService currencyExchangeRateService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create CurrencyExchangeRate
    @ApiOperation(response = CurrenyExchangeRate.class, value = "Create new CurrencyExchangeRate") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postCurrencyExchangeRate(@Valid @RequestBody AddCurrencyExchangeRate addCurrencyExchangeRate, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CurrenyExchangeRate newCurrencyExchangeRate = currencyExchangeRateService.createCurrencyExchangeRate(addCurrencyExchangeRate, loginUserID);
        return new ResponseEntity<>(newCurrencyExchangeRate, HttpStatus.OK);
    }

    // Update CurrencyExchangeRate
    @ApiOperation(response = CurrenyExchangeRate.class, value = "Update CurrencyExchangeRate") // label for swagger
    @PatchMapping("/{fromCurrencyId}")
    public ResponseEntity<?> patchCurrencyExchangeRate(@PathVariable String fromCurrencyId, @RequestParam String languageId, @RequestParam String companyId,
                                                       @RequestParam String toCurrencyId, @RequestBody UpdateCurrencyExchangeRate updateCurrencyExchangeRate, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        CurrenyExchangeRate updatedCurrencyExchangeRate = currencyExchangeRateService.updateCurrencyExchangeRate
                (languageId, companyId, fromCurrencyId, toCurrencyId, updateCurrencyExchangeRate, loginUserID);
        return new ResponseEntity<>(updatedCurrencyExchangeRate, HttpStatus.OK);
    }

    // Delete CurrencyExchangeRate
    @ApiOperation(response = CurrenyExchangeRate.class, value = "Delete CurrencyExchangeRate") // label for swagger
    @DeleteMapping("/{fromCurrencyId}")
    public ResponseEntity<?> deleteCurrencyExchangeRate(@PathVariable String fromCurrencyId, @RequestParam String languageId,
                                                        @RequestParam String companyId, @RequestParam String toCurrencyId, @RequestParam String loginUserID) {
        currencyExchangeRateService.deleteCurrencyExchangeRate(languageId, companyId, fromCurrencyId, toCurrencyId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /*-----------------------------------------------REPLICA---------------------------------------------------------*/

    // Get All CurrencyExchangeRate Details
    @ApiOperation(response = ReplicaCurrencyExchangeRate.class, value = "Get all CurrencyExchangeRate Details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllCurrencyExchangeRateDetails() {
        List<ReplicaCurrencyExchangeRate> currencyExchangeRateList = currencyExchangeRateService.getAllCurrencyExchangeRate();
        return new ResponseEntity<>(currencyExchangeRateList, HttpStatus.OK);
    }

    // Get CurrencyExchangeRate
    @ApiOperation(response = ReplicaCurrencyExchangeRate.class, value = "Get a CurrencyExchangeRate")
    // label for swagger
    @GetMapping("/{fromCurrencyId}")
    public ResponseEntity<?> getCurrencyExchangeRate(@PathVariable String fromCurrencyId, @RequestParam String languageId,
                                                     @RequestParam String companyId, @RequestParam String toCurrencyId) {
        ReplicaCurrencyExchangeRate dbCurrencyExchangeRate = currencyExchangeRateService.replicaGetCurrencyExchangeRate
                (languageId, companyId, fromCurrencyId, toCurrencyId);
        return new ResponseEntity<>(dbCurrencyExchangeRate, HttpStatus.OK);
    }

    // Find CurrencyExchangeRates
    @ApiOperation(response = ReplicaCurrencyExchangeRate.class, value = "Find CurrencyExchangeRate")
    // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findCurrencyExchangeRate(@Valid @RequestBody FindCurrencyExchangeRate findCurrencyExchangeRate) throws Exception {
        List<ReplicaCurrencyExchangeRate> currencyExchangeRateList = currencyExchangeRateService.findCurrencyExchangeRate(findCurrencyExchangeRate);
        return new ResponseEntity<>(currencyExchangeRateList, HttpStatus.OK);
    }

}
