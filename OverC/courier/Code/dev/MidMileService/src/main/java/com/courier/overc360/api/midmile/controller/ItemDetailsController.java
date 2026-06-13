package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.consignment.FindConsignment;
import com.courier.overc360.api.midmile.primary.model.itemdetails.ItemDetails;
import com.courier.overc360.api.midmile.primary.model.itemdetails.UpdateItemDetails;
import com.courier.overc360.api.midmile.replica.model.dto.FindPreAlertManifest;
import com.courier.overc360.api.midmile.replica.model.dto.PreAlertManifestImpl;
import com.courier.overc360.api.midmile.replica.model.itemdetails.ReplicaItemDetails;
import com.courier.overc360.api.midmile.service.ItemDetailsService;
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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"ItemDetails"}, value = "ItemDetails Operations related to ItemDetailsController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "ItemDetails", description = "Operations related to ItemDetails")})
@RequestMapping("/itemDetails")
@RestController
public class ItemDetailsController {
    @Autowired
    ItemDetailsService itemDetailsService;
    /*-------------------------------------------PRIMARY-------------------------------------------------------------------*/

    // Create ItemDetails
//    @ApiOperation(response = ItemDetails.class, value = "Create ItemDetails") // label for swagger
//    @PostMapping("")
//    public ResponseEntity<?> postItemDetails(@Valid @RequestBody AddItemDetails newItemDetails, @RequestParam String loginUserID)
//            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
//        ItemDetails createdItemDetails = itemDetailsService.createItemDetails(newItemDetails, loginUserID);
//        return new ResponseEntity<>(createdItemDetails, HttpStatus.OK);
//    }

    // Update ItemDetails
    @ApiOperation(response = ItemDetails.class, value = "Update ItemDetails") // label for swagger
    @PatchMapping("/{pieceItemId}")
    public ResponseEntity<?> patchItemDetails(@PathVariable String pieceItemId, @RequestParam String languageId, @RequestParam String companyId,
                                              @RequestParam String partnerId, @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill, @RequestParam String pieceId,
                                              @RequestParam String loginUserID, @RequestBody UpdateItemDetails updateItemDetails)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ItemDetails updatedItemDetails = itemDetailsService.updateItemDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, loginUserID, updateItemDetails);
        return new ResponseEntity<>(updatedItemDetails, HttpStatus.OK);
    }

    // Delete ItemDetails
    @ApiOperation(response = ItemDetails.class, value = "Delete ItemDetails") // label for swagger
    @DeleteMapping("/{pieceItemId}")
    public ResponseEntity<?> deleteItemDetails(@PathVariable String pieceItemId, @RequestParam String languageId, @RequestParam String companyId,
                                               @RequestParam String partnerId, @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill,
                                               @RequestParam String pieceId, @RequestParam String loginUserID) {
        itemDetailsService.deleteItemDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*-----------------------------------------------REPLICA---------------------------------------------------------------*/

    // Get All ItemDetails Details
    @ApiOperation(response = ReplicaItemDetails.class, value = "Get all ItemDetails details") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getAllItemDetails() {
        List<ReplicaItemDetails> itemDetailsList = itemDetailsService.getAllItemDetails();
        return new ResponseEntity<>(itemDetailsList, HttpStatus.OK);
    }

    // Get ReplicaItemDetails
    @ApiOperation(response = ReplicaItemDetails.class, value = "Get a ReplicaItemDetails") // label for swagger
    @GetMapping("/{pieceItemId}")
    public ResponseEntity<?> getItemDetails(@PathVariable String pieceItemId, @RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String partnerId, @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill, @RequestParam String pieceId) {

        ReplicaItemDetails itemDetails = itemDetailsService.replicaGetItemDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId);
        return new ResponseEntity<>(itemDetails, HttpStatus.OK);
    }
    // Find ItemDetails
    @ApiOperation(response = ReplicaItemDetails.class, value = "Find ItemDetails") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findItemDetails(@RequestBody FindConsignment findItemDetails) throws Exception {
        List<ReplicaItemDetails> createdItemDetails = itemDetailsService.findItemDetails(findItemDetails);
        return new ResponseEntity<>(createdItemDetails, HttpStatus.OK);
    }

    // Find PreAlertManifest changed from consignment to Itemdetails
    @ApiOperation(response = PreAlertManifestImpl.class, value = "Find PreAlertManifest") // label for swagger
    @PostMapping("/findPreAlertManifest")
    public ResponseEntity<?> findPreAlertManifest(@RequestBody FindPreAlertManifest findPreAlertManifest) throws Exception {
        List<PreAlertManifestImpl> preAlertManifestList = itemDetailsService.findPreAlertManifest(findPreAlertManifest);
        return new ResponseEntity<>(preAlertManifestList, HttpStatus.OK);
    }

}

