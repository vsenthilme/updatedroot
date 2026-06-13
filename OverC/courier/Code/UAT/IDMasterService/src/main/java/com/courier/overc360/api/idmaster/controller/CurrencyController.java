package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.currency.AddCurrency;
import com.courier.overc360.api.idmaster.primary.model.currency.Currency;
import com.courier.overc360.api.idmaster.primary.model.currency.UpdateCurrency;
import com.courier.overc360.api.idmaster.replica.model.currency.FindCurrency;
import com.courier.overc360.api.idmaster.replica.model.currency.ReplicaCurrency;
import com.courier.overc360.api.idmaster.service.CurrencyService;
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
@Api(tags = {"Currency"}, value = "Currency operations related to CurrencyController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Currency", description = "Operations related to Currency")})
@RequestMapping("/currency")
@RestController
public class CurrencyController {

    @Autowired
    CurrencyService currencyService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create Currency
    @ApiOperation(response = Currency.class, value = "Create new Currency") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postCurrency(@Valid @RequestBody AddCurrency addCurrency, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Currency newCurrency = currencyService.createCurrency(addCurrency, loginUserID);
        return new ResponseEntity<>(newCurrency, HttpStatus.OK);
    }

    // Update Currency
    @ApiOperation(response = Currency.class, value = "Update Currency") // label for swagger
    @PatchMapping("/{currencyId}")
    public ResponseEntity<?> patchCurrency(@PathVariable String currencyId, @RequestParam String loginUserID,
                                           @RequestBody UpdateCurrency updateCurrency)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Currency updatedCurrency = currencyService.updateCurrency(currencyId, updateCurrency, loginUserID);
        return new ResponseEntity<>(updatedCurrency, HttpStatus.OK);
    }

    // Delete Currency
    @ApiOperation(response = Currency.class, value = "Delete Currency") // label for swagger
    @DeleteMapping("/{currencyId}")
    public ResponseEntity<?> deleteCurrency(@PathVariable String currencyId, @RequestParam String loginUserID) {
        currencyService.deleteCurrency(currencyId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------*/
    // Get All Currency Details
    @ApiOperation(response = ReplicaCurrency.class, value = "Get all Currency Details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllCurrencyDetails() {
        List<ReplicaCurrency> currencyList = currencyService.getAllCurrencies();
        return new ResponseEntity<>(currencyList, HttpStatus.OK);
    }

    // Get Currency
    @ApiOperation(response = ReplicaCurrency.class, value = "Get a Currency") // label for swagger
    @GetMapping("/{currencyId}")
    public ResponseEntity<?> getCurrency(@PathVariable String currencyId) {
        ReplicaCurrency dbCurrency = currencyService.replicaGetCurrency(currencyId);
        return new ResponseEntity<>(dbCurrency, HttpStatus.OK);
    }


    // Find Currency
    @ApiOperation(response = ReplicaCurrency.class, value = "Find Currency") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findCurrency(@Valid @RequestBody FindCurrency findCurrency) throws Exception {
        List<ReplicaCurrency> currencyList = currencyService.findCurrency(findCurrency);
        return new ResponseEntity<>(currencyList, HttpStatus.OK);
    }

}

