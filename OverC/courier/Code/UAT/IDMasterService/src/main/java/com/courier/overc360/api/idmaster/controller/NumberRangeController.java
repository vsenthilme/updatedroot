package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.numberange.AddNumberRange;
import com.courier.overc360.api.idmaster.primary.model.numberange.NumberRange;
import com.courier.overc360.api.idmaster.primary.model.numberange.UpdateNumberRange;
import com.courier.overc360.api.idmaster.replica.model.numberrange.FindNumberRange;
import com.courier.overc360.api.idmaster.replica.model.numberrange.ReplicaNumberRange;
import com.courier.overc360.api.idmaster.service.NumberRangeService;
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
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@Validated
@CrossOrigin(origins = "*")
@Api(tags = {"NumberRange"}, value = "NumberRange Operations related to NumberRangeController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "NumberRange", description = "Operations related to NumberRange")})
@RequestMapping("/numberRange")
@RestController
public class NumberRangeController {

    @Autowired
    NumberRangeService numberRangeService;
    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/


    // Create new NumberRangeCode
    @ApiOperation(response = NumberRange.class, value = "Create new NumberRange") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postNumberRange(@Valid @RequestBody AddNumberRange newNumberRange,
                                             @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException {
        NumberRange createdNumberRange = numberRangeService.createNumberRange(newNumberRange, loginUserID);
        return new ResponseEntity<>(createdNumberRange, HttpStatus.OK);
    }

    // Get Current Number Range Value
    @ApiOperation(response = NumberRange.class, value = "Get Number Range Current") // label for swagger
    @GetMapping("/nextNumberRange")
    public ResponseEntity<?> getNumberRangeId(@RequestParam String numberRangeObject) {
        String nextRangeValue = numberRangeService.getNextNumberRange(numberRangeObject);
        return new ResponseEntity<>(nextRangeValue, HttpStatus.OK);
    }

    // Update NumberRangeCode
    @ApiOperation(response = NumberRange.class, value = "Update NumberRange") // label for swagger
    @PatchMapping("/{numberRangeCode}")
    public ResponseEntity<?> patchNumberRange(@PathVariable Long numberRangeCode, @RequestParam String languageId,
                                              @RequestParam String loginUserID, @RequestParam String numberRangeObject,
                                              @Valid @RequestBody UpdateNumberRange updateNumberRange)
            throws IllegalAccessException, InvocationTargetException {
        NumberRange updatedNumberRange = numberRangeService.updateNumberRange(numberRangeCode, languageId,
                numberRangeObject, loginUserID, updateNumberRange);
        return new ResponseEntity<>(updatedNumberRange, HttpStatus.OK);
    }

    // Delete NumberRangeCode
    @ApiOperation(response = NumberRange.class, value = "Delete NumberRange") // label for swagger
    @DeleteMapping("/{numberRangeCode}")
    public ResponseEntity<?> deleteNumberRange(@PathVariable Long numberRangeCode, @RequestParam String languageId,
                                               @RequestParam String numberRangeObject, @RequestParam String loginUserID) {
        numberRangeService.deleteNumberRange(numberRangeCode, languageId, numberRangeObject, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*===================================================REPLICA========================================================*/


    // Find NumberRangeCode
    @ApiOperation(response = ReplicaNumberRange.class, value = "Find NumberRange") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findNumberRange(@Valid @RequestBody FindNumberRange findNumberRange) throws Exception {
        List<ReplicaNumberRange> numberRangeList = numberRangeService.findNumberRange(findNumberRange);
        return new ResponseEntity<>(numberRangeList, HttpStatus.OK);
    }


    // Get All NumberRangeCode Details
    @ApiOperation(response = ReplicaNumberRange.class, value = "Get all NumberRange details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllNumberRangeDetails() {
        List<ReplicaNumberRange> numberRangeList = numberRangeService.getNumberRanges();
        return new ResponseEntity<>(numberRangeList, HttpStatus.OK);
    }

    // Get a NumberRangeCode
    @ApiOperation(response = ReplicaNumberRange.class, value = "Get a NumberRange") // label for swagger
    @GetMapping("/{numberRangeCode}")
    public ResponseEntity<?> getNumberRange(@PathVariable Long numberRangeCode, @RequestParam String languageId,
                                            @RequestParam String numberRangeObject) {
        ReplicaNumberRange numberRange = numberRangeService.replicaGetNumberRange(languageId, numberRangeCode, numberRangeObject);
        return new ResponseEntity<>(numberRange, HttpStatus.OK);
    }
}