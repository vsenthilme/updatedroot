package com.courier.overc360.api.common.controller;

import com.courier.overc360.api.common.model.consignment.AddConsignment;
import com.courier.overc360.api.common.model.consignment.Consignment;
import com.courier.overc360.api.common.model.consignment.FindConsignment;
import com.courier.overc360.api.common.model.consignment.v2.AddConsignmentV2;
import com.courier.overc360.api.common.model.consignment.v2.ConsignmentOutput;
import com.courier.overc360.api.common.model.consignment.v2.ConsignmentV2;
import com.courier.overc360.api.common.service.ConsignmentService;
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
import java.text.ParseException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"Consignment"}, value = "Consignment Operations related to ConsignmentController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Consignment", description = "Operations related to Consignment")})
@RequestMapping("/consignment")
@RestController
public class ConsignmentController {

    @Autowired
    ConsignmentService consignmentService;

    // Create Consignment - raw data
    @ApiOperation(response = AddConsignment.class, value = "Create new Consignment") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postConsignment(@Valid @RequestBody List<AddConsignment> addConsignment, @RequestParam String loginUserId, @RequestParam String masterAirwayBill) {
        consignmentService.createConsignment(addConsignment, loginUserId, masterAirwayBill);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // Create Consignment - processed data
    @ApiOperation(response = AddConsignmentV2.class, value = "Create new Consignment V2") // label for swagger
    @PostMapping("/v2")
    public ResponseEntity<?> postConsignment(@Valid @RequestBody List<AddConsignmentV2> addConsignment, @RequestParam String masterAirwayBill) {
        consignmentService.postConsignment(addConsignment, masterAirwayBill);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @ApiOperation(response = AddConsignment.class, value = "Find Consignment") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findConsignment(@Valid @RequestBody FindConsignment findConsignment) throws ParseException {
        List<AddConsignment> consignmentList = consignmentService.findConsignment(findConsignment);
        return new ResponseEntity<>(consignmentList, HttpStatus.OK);
    }

    @ApiOperation(response = ConsignmentV2.class, value = "Find Consignment V2") // label for swagger
    @PostMapping("/find/v2")
    public ResponseEntity<?> findConsignmentV2(@Valid @RequestBody FindConsignment findConsignment) throws Exception {
        List<ConsignmentV2> consignmentList = consignmentService.findConsignmentV3(findConsignment);
        return new ResponseEntity<>(consignmentList, HttpStatus.OK);
    }

}