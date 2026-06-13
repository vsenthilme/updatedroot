package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.bondedmanifest.AddBondedManifest;
import com.courier.overc360.api.midmile.primary.model.bondedmanifest.BondedManifest;
import com.courier.overc360.api.midmile.primary.model.bondedmanifest.BondedManifestDeleteInput;
import com.courier.overc360.api.midmile.primary.model.bondedmanifest.UpdateBondedManifest;
import com.courier.overc360.api.midmile.primary.model.prealert.PreAlert;
import com.courier.overc360.api.midmile.replica.model.bondedmanifest.FindBondedManifest;
import com.courier.overc360.api.midmile.replica.model.bondedmanifest.ReplicaBondedManifest;
import com.courier.overc360.api.midmile.service.BondedManifestService;
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
@Api(tags = {"BondedManifest"}, value = "BondedManifest Operations related to BondedManifestController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "BondedManifest", description = "Operations related to BondedManifest")})
@RequestMapping("/bondedManifest")
@RestController
public class BondedManifestController {

    @Autowired
    BondedManifestService bondedManifestService;

    /*---------------------------------------------------PRIMARY-----------------------------------------------------*/

    // Create new BondedManifest
    @ApiOperation(response = BondedManifest.class, value = "Create new BondedManifest") // label for swagger
    @PostMapping("/create/list")
    public ResponseEntity<?> postBondedManifest(@Valid @RequestBody List<AddBondedManifest> addBondedManifestList,
                                                @RequestParam String loginUserID)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        List<BondedManifest> bondedManifest = bondedManifestService.createBondedManifest(addBondedManifestList, loginUserID);
        return new ResponseEntity<>(bondedManifest, HttpStatus.OK);
    }

    // CreateBondedManifest based on ConsinmentInput
//    @ApiOperation(response = BondedManifest.class, value = "Create BondedManifest Based on ConsignmentInput")
//    @PostMapping("/bondedmanifest/create")
//    public ResponseEntity<?> createBondedManifest(@Valid @RequestBody List<AddConsignment> addConsignment, @RequestParam String loginUserID)
//            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
//        List<BondedManifest> createBonded = bondedManifestService.createBondedManifestBasedOnConsignmentInput(addConsignment, loginUserID);
//        return new ResponseEntity<>(createBonded, HttpStatus.OK);
//    }

    // Create new BondedManifests based on PreAlert Input
    @ApiOperation(response = BondedManifest.class, value = "Create new BondedManifests based On PreAlert Input")
    @PostMapping("/create/preAlert")
    public ResponseEntity<?> postBondedManifestsFromPreAlert(@Valid @RequestBody List<PreAlert> preAlertList, @RequestParam String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        List<BondedManifest> bondedManifests = bondedManifestService.createBondedManifestListsOnPreAlertInput(preAlertList, loginUserID);
        return new ResponseEntity<>(bondedManifests, HttpStatus.OK);
    }

    // Update BondedManifest
    @ApiOperation(response = BondedManifest.class, value = "Update BondedManifest") // label for Swagger
    @PatchMapping("/update/list")
    public ResponseEntity<?> patchBondedManifest(@Valid @RequestBody List<UpdateBondedManifest> updateBondedManifestList,
                                                 @RequestParam String loginUserID)
            throws InvocationTargetException, IllegalAccessException, IOException, CsvException {
        List<BondedManifest> bondedManifest = bondedManifestService.updateBondedManifest(updateBondedManifestList, loginUserID);
        return new ResponseEntity<>(bondedManifest, HttpStatus.OK);
    }

    // Delete BondedManifest
    @ApiOperation(response = BondedManifest.class, value = "Delete BondedManifest") // label for Swagger
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteBondedManifest(@Valid @RequestBody List<BondedManifestDeleteInput> bondedManifestDeleteInputs,
                                                  @RequestParam String loginUserID) throws IOException, CsvException {
        bondedManifestService.deleteBondedManifest(bondedManifestDeleteInputs, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*---------------------------------------------------REPLICA-----------------------------------------------------*/

    // Get All BondedManifest Details
    @ApiOperation(response = ReplicaBondedManifest.class, value = "Get all BondedManifest Details")
    // label for swagger
    @GetMapping(" ")
    public ResponseEntity<?> getAllBondedManifest() {
        List<ReplicaBondedManifest> bondedManifest = bondedManifestService.getAllBondedManifest();
        return new ResponseEntity<>(bondedManifest, HttpStatus.OK);
    }
//    @ApiOperation(response = BondedManifestHeader.class, value = "Get all BondedManifestHeader Details")
//    // label for swagger
//    @GetMapping("/allHeaders")
//    public ResponseEntity<?> getAllBondedManifestHeaders() {
//        List<BondedManifestHeader> bondedManifestHeaders = bondedManifestHeaderService.getAllBondedManifestHeaders();
//        return new ResponseEntity<>(bondedManifestHeaders, HttpStatus.OK);
//    }


    // Get BondedManifest
    @ApiOperation(response = ReplicaBondedManifest.class, value = "Get a BondedManifest")
    @GetMapping("/{bondedId}")
    public ResponseEntity<?> getBondedManifest(@PathVariable String bondedId, @RequestParam String languageId,
                                               @RequestParam String companyId, @RequestParam String partnerId,
                                               @RequestParam String partnerMasterAirwayBill, @RequestParam String partnerHouseAirwayBill) {
        ReplicaBondedManifest bondedManifest = bondedManifestService.getBondedManifestReplica(
                languageId, companyId, partnerId, partnerMasterAirwayBill, partnerHouseAirwayBill, bondedId);
        return new ResponseEntity<>(bondedManifest, HttpStatus.OK);
    }
//    @ApiOperation(response = BondedManifestHeader.class, value = "Get a BondedManifestHeader")
//    @GetMapping("/{bondedId}")
//    public ResponseEntity<?> getBondedManifestHeader(@PathVariable String bondedId, @RequestParam String languageId,
//                                                     @RequestParam String companyId, @RequestParam String partnerId,
//                                                     @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill) {
//        BondedManifestHeader bondedManifestHeader = bondedManifestHeaderService.getBondedManifestHeaderReplica(
//                languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, bondedId);
//        return new ResponseEntity<>(bondedManifestHeader, HttpStatus.OK);
//    }

    // Find BondedManifest
    @ApiOperation(response = ReplicaBondedManifest.class, value = "Find BondedManifest") // label for swagger
    @PostMapping("/findBondedManifest")
    public ResponseEntity<?> findBondedManifestHeaders(@Valid @RequestBody FindBondedManifest findBondedManifest) throws Exception {
        List<ReplicaBondedManifest> bondedManifestList = bondedManifestService.findBondedManifest(findBondedManifest);
        return new ResponseEntity<>(bondedManifestList, HttpStatus.OK);
    }

}
