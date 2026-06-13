package com.courier.overc360.api.idmaster.controller;


import com.courier.overc360.api.idmaster.primary.model.paymentType.AddPaymentType;
import com.courier.overc360.api.idmaster.primary.model.paymentType.PaymentType;
import com.courier.overc360.api.idmaster.primary.model.timeslot.AddTimeSlot;
import com.courier.overc360.api.idmaster.primary.model.timeslot.TimeSlot;
import com.courier.overc360.api.idmaster.replica.model.paymenttype.FindPaymentType;
import com.courier.overc360.api.idmaster.replica.model.paymenttype.ReplicaPaymentType;
import com.courier.overc360.api.idmaster.replica.model.timeslot.ReplicaTimeSlot;
import com.courier.overc360.api.idmaster.service.PaymentTypeService;
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
@Api(tags = {"PaymentType"}, value = "PaymentType operations related to PaymentTypeController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "PaymentType", description = "Operations related to PaymentType")})
@RequestMapping("/paymentType")
@RestController
public class PaymentTypeController {


    @Autowired
    private PaymentTypeService paymentTypeService;



    // Create PaymentType
    @ApiOperation(response = PaymentType.class, value = "Create PaymentType") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postPaymentType(@Valid @RequestBody List<AddPaymentType> addPaymentTypes, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<PaymentType> createPaymentType = paymentTypeService.createPaymentType(addPaymentTypes, loginUserID);
        return new ResponseEntity<>(createPaymentType, HttpStatus.OK);
    }

    // Update PaymentType List
    @ApiOperation(response = PaymentType.class, value = "Update PaymentType List") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchPaymentTypeList(@RequestParam String loginUserID, @RequestBody List<AddPaymentType> updatePaymentType)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<PaymentType> updatedPaymentType = paymentTypeService.updatePaymentType(updatePaymentType, loginUserID);
        return new ResponseEntity<>(updatedPaymentType, HttpStatus.OK);
    }

    // Delete PaymentType List
    @ApiOperation(response = PaymentType.class, value = "Delete PaymentType") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deletePaymentTypeList(@RequestBody List<PaymentType> deletePaymentList, @RequestParam String loginUserID) {
        paymentTypeService.deletePaymentTypeList(deletePaymentList, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All PaymentType Details
    @ApiOperation(response = ReplicaPaymentType.class, value = "Get all PaymentType details") // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPaymentType() {
        List<ReplicaPaymentType> replicaPaymentTypes = paymentTypeService.getAllPaymentTypes();
        return new ResponseEntity<>(replicaPaymentTypes, HttpStatus.OK);
    }


    // Get PaymentType
    @ApiOperation(response = ReplicaPaymentType.class, value = "Get a PaymentType") // label for swagger
    @GetMapping("/get")
    public ResponseEntity<?> getPaymentType(@RequestParam String languageId, @RequestParam String companyId,
                                         @RequestParam String paymentTypeId) {

        ReplicaPaymentType replicaPaymentType = paymentTypeService.getPaymentTypeid(languageId, companyId, paymentTypeId);
        return new ResponseEntity<>(replicaPaymentType, HttpStatus.OK);
    }

    // Find PaymentType
    @ApiOperation(response = ReplicaPaymentType.class, value = "Find PaymentType") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findPaymentType(@RequestBody FindPaymentType findPaymentType) throws Exception {
        List<ReplicaPaymentType> findPaymentTypes = paymentTypeService.findPaymentType(findPaymentType);
        return new ResponseEntity<>(findPaymentTypes, HttpStatus.OK);
    }






}
