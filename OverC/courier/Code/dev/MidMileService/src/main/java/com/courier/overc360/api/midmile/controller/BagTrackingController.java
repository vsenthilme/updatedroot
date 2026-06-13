package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.bagtracking.AddBagTracking;
import com.courier.overc360.api.midmile.primary.model.bagtracking.BagTracking;
import com.courier.overc360.api.midmile.primary.model.bagtracking.UpdateBagTracking;
import com.courier.overc360.api.midmile.primary.model.consignment.ConsignmentEntity;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupEntity;
import com.courier.overc360.api.midmile.replica.model.bagtracking.FindBagTracking;
import com.courier.overc360.api.midmile.replica.model.bagtracking.ReplicaBagTracking;
import com.courier.overc360.api.midmile.service.BagTrackingService;
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
@Api(tags = {"BagTracking"}, value = "BagTracking  Operations related to BagTrackingController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "BagTracking", description = "Operations related to BagTracking")})
@RequestMapping("/bagTracking")
@RestController
public class BagTrackingController {


    @Autowired
    BagTrackingService bagTrackingService;

    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/
    // Create BagTracking
    @ApiOperation(response = BagTracking.class, value = "Create BagTracking") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postBagTracking(@Valid @RequestBody List<AddBagTracking> addBagTracking, @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<BagTracking> bagTracking = bagTrackingService.createBagTracking(addBagTracking, loginUserID);
        return new ResponseEntity<>(bagTracking, HttpStatus.OK);
    }

    // Update BagTracking
    @ApiOperation(response = BagTracking.class, value = "Update BagTracking") // label for swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchBagTracking(@RequestParam String loginUserID,
                                              @RequestBody List<UpdateBagTracking> updateBagTracking)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<BagTracking> updatedBagTracking =
                bagTrackingService.updateBagTracking(loginUserID, updateBagTracking);
        return new ResponseEntity<>(updatedBagTracking, HttpStatus.OK);
    }

    // Delete BagTracking
    @ApiOperation(response = BagTracking.class, value = "Delete BagTracking") // label for swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteBagTracking(@RequestBody List<BagTracking> deleteInput, @RequestParam String loginUserID) {
        bagTrackingService.deleteBagTracking(deleteInput, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/
    // Get All BagTracking Details
    @ApiOperation(response = ReplicaBagTracking.class, value = "Get all ReplicaBagTracking details")
    // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllBagTrackings() {
        List<ReplicaBagTracking> bagTrackingList = bagTrackingService.getAllBagTrackings();
        return new ResponseEntity<>(bagTrackingList, HttpStatus.OK);
    }

    // Get BagTracking
    @ApiOperation(response = ReplicaBagTracking.class, value = "Get a ReplicaBagTracking") // label for swagger
    @GetMapping("/{bagId}")
    public ResponseEntity<?> getBagTracking(@PathVariable Long consignmentBagId, @RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String partnerId,  @RequestParam String houseAirwayBill) {
        ReplicaBagTracking dbBagTracking = bagTrackingService.getReplicaBagTracking(languageId, companyId, partnerId, consignmentBagId, houseAirwayBill);
        return new ResponseEntity<>(dbBagTracking, HttpStatus.OK);
    }

    // Find BagTracking
    @ApiOperation(response = ReplicaBagTracking.class, value = "Find ReplicaBagTracking") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findBagTrackings(@Valid @RequestBody FindBagTracking findBagTracking) throws Exception {
        List<ReplicaBagTracking> bagTrackingList = bagTrackingService.findBagTrackings(findBagTracking);
        return new ResponseEntity<>(bagTrackingList, HttpStatus.OK);
    }

    // Consignment to BagTracking Create
    @ApiOperation(response = BagTracking.class, value = "Consignment to BagTracking Create")
    @PostMapping("/consignmentBagTrack")
    public ResponseEntity<?> createConsignmentBagTrack(@Valid @RequestBody List<ConsignmentEntity> consignmentEntities,
                                                                @RequestParam String loginUserID) throws Exception {
        List<BagTracking> dbBagTracking = bagTrackingService.createConsignmentToBagTrack(consignmentEntities, loginUserID);
        return new ResponseEntity<>(dbBagTracking, HttpStatus.OK);
    }

    //Create BagTracking from Pickup
    @ApiOperation(response = BagTracking.class, value = "Create BagTracking From Pickup")
    @PostMapping("/create/bagtracking/pickup")
    public ResponseEntity<?> postBagTrackingFromPickup(@RequestBody List<PickupEntity> pickupEntities, @RequestParam String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {

        List<BagTracking> bagTrackings = bagTrackingService.createBagFromPickupRequest(pickupEntities, loginUserID);
        return new ResponseEntity<>(bagTrackings, HttpStatus.OK);
    }

}
