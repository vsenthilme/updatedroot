package com.courier.overc360.api.controller;

import com.courier.overc360.api.model.transaction.*;
import com.courier.overc360.api.service.CommonService;
import com.courier.overc360.api.service.MidMileService;
import com.google.api.Http;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/overc-midmile-service")
@Api(tags = {"MidMile Service"}, value = "MidMile Service Operations") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "User", description = "Operations related to MidMile Service")})
public class MidMileController {


    @Autowired
    MidMileService midMileService;

    @Autowired
    CommonService commonService;


    //GetAllConsignment
    @ApiOperation(response = ConsignmentEntity.class, value = "Get All Consignment") // label for swagger
    @GetMapping("/consignment")
    public ResponseEntity<?> getAllConsignment(@RequestParam String authToken) {
        ConsignmentEntity[] consignmentEntities = midMileService.getAllConsignment(authToken);
        return new ResponseEntity<>(consignmentEntities, HttpStatus.OK);
    }


    // Create new Consignment
    @ApiOperation(response = AddConsignment.class, value = "Create new Consignment") // label for swagger
    @PostMapping("/consignment")
    public ResponseEntity<?> postConsignment(@Valid @RequestBody AddConsignment[] addConsignment,
                                             @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ConsignmentEntity[] createConsignment = midMileService.createConsignment(addConsignment, loginUserID, authToken);
        return new ResponseEntity<>(createConsignment, HttpStatus.OK);
    }


    // Update Consignment
    @ApiOperation(response = ConsignmentEntity.class, value = "Update Consignment") // label for swagger
    @PatchMapping("/consignment")
    public ResponseEntity<?> updateConsignment(@RequestParam String loginUserID, @Valid @RequestBody List<UpdateConsignment> updateConsignment,
                                               @RequestParam String authToken) {
        ConsignmentEntity[] consignment = midMileService.updateConsignment( updateConsignment, loginUserID, authToken);
        return new ResponseEntity<>(consignment, HttpStatus.OK);
    }

    // Find Consignment
    @ApiOperation(response = ConsignmentEntity[].class, value = "Find Consignment") //label for swagger
    @PostMapping("/consignment/find")
    public ConsignmentEntity[] findConsignment(@Valid @RequestBody FindConsignment findConsignment, @RequestParam String authToken) throws Exception {
        return midMileService.findConsignmentEntity(findConsignment, authToken);
    }

    // Find IConsignmentEntity - null validation column preAlertValidationIndicator
    @ApiOperation(response = IConsignment[].class, value = "Find Consignment preAlertValidationIndicator") //label for swagger
    @PostMapping("/consignment/find/v2")
    public IConsignment[] findIConsignment(@Valid @RequestBody FindIConsignment findConsignment, @RequestParam String authToken) throws Exception {
        return midMileService.findIConsignmentEntity(findConsignment, authToken);
    }

    // Find ConsignmentEntity - cassandra
    @ApiOperation(response = ConsignmentEntity[].class, value = "Find Consignment cassandra") //label for swagger
    @PostMapping("/consignment/find/v3")
    public ConsignmentEntity[] findConsignmentV3(@Valid @RequestBody FindCassandraConsignment findConsignment) throws Exception {
        return commonService.findConsignment(findConsignment);
    }

    // Find PreAlert Manifest - based on consignment details
    @ApiOperation(response = ConsignmentEntity[].class, value = "Find PreAlert Manifest - consignment details based") //label for swagger
    @PostMapping("/consignment/findPreAlertManifest")
    public ConsignmentEntity[] findPreAlertManifest(@Valid @RequestBody FindPreAlertManifest findPreAlertManifest, @RequestParam String authToken) throws Exception {
        return midMileService.findPreAlertManifest(findPreAlertManifest, authToken);
    }

    // Find PreAlert Manifest - based on Item details
    @ApiOperation(response = PreAlertManifest[].class, value = "Find PreAlert Manifest - Item Details Based") //label for swagger
    @PostMapping("/itemDetails/findPreAlertManifest")
    public PreAlertManifest[] findPreAlertManifestV2(@Valid @RequestBody FindPreAlertManifest findPreAlertManifest, @RequestParam String authToken) throws Exception {
        return midMileService.findPreAlertManifestV2(findPreAlertManifest, authToken);
    }

    //Delete Consignment
    @ApiOperation(response = ConsignmentEntity.class, value = "Delete Consignment")
    @DeleteMapping("/consignment")
    public ResponseEntity<?> deleteConsignment(@RequestParam String companyId, @RequestParam String languageId, @RequestParam String partnerId,
                                               @RequestParam String houseAirwayBill, @RequestParam String masterAirwayBill,
                                               @RequestParam(required = false) String pieceId, @RequestParam(required = false) String pieceItemId,
                                               @RequestParam(required = false) String imageRefId, @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteConsignment(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, loginUserID, imageRefId, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Delete Consignment
    @ApiOperation(response = ConsignmentEntity.class, value = "Delete Consignment")
    @PostMapping("/consignment/delete/list")
    public ResponseEntity<?> deleteConsignment( @Valid @RequestBody List<ConsignmentDelete> consignmentDeletes, @RequestParam String loginUserID,
                                                @RequestParam String authToken) {
        midMileService.deleteConsignmentMultiple(consignmentDeletes, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //===============================================ImageReference========================================================
    // Get all ImageReference Details
    @ApiOperation(response = ImageReference.class, value = "Get All ImageReference Details") // label for swagger
    @GetMapping("/imageReference")
    public ResponseEntity<?> getAllImageReferenceDetails(@RequestParam String authToken) {
        ImageReference[] imageReferences = midMileService.getAllImageReferences(authToken);
        return new ResponseEntity<>(imageReferences, HttpStatus.OK);
    }

    // Get ImageReference
    @ApiOperation(response = ImageReference.class, value = "Get ImageReference") // label for swagger
    @GetMapping("/imageReference/{imageRefId}")
    public ResponseEntity<?> getImageReference(@PathVariable String imageRefId, @RequestParam String companyId,
                                               @RequestParam String languageId, @RequestParam String partnerId,
                                               @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill, @RequestParam String pieceId,
                                               @RequestParam String pieceItemId, @RequestParam String authToken) {
        ImageReference imageReference = midMileService.getImageReference(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, imageRefId, authToken);
        return new ResponseEntity<>(imageReference, HttpStatus.OK);
    }

    // Create ImageReference
    @ApiOperation(response = ImageReference.class, value = "Create new ImageReference") // label for swagger
    @PostMapping("/imageReference")
    public ResponseEntity<?> createImageReference(@RequestBody AddImageReference addImageReference, @RequestParam String loginUserID, @RequestParam String authToken) {
        ImageReference imageReference = midMileService.createImageReference(addImageReference, loginUserID, authToken);
        return new ResponseEntity<>(imageReference, HttpStatus.OK);
    }

    // Update ImageReference
    @ApiOperation(response = ImageReference.class, value = "Update ImageReference") // label for swagger
    @PatchMapping("/imageReference/{imageRefId}")
    public ResponseEntity<?> updateImageReference(@PathVariable String imageRefId, @RequestParam String languageId, @RequestParam String partnerId, @RequestParam String masterAirwayBill,
                                                  @RequestParam String houseAirwayBill, @RequestParam String pieceId, @RequestParam String pieceItemId,
                                                  @RequestParam String loginUserID, @RequestBody UpdateImageReference updateImageReference,
                                                  @RequestParam String companyId, @RequestParam String authToken) {
        ImageReference imageReference = midMileService.updateImageReference(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, imageRefId, updateImageReference, loginUserID, authToken);
        return new ResponseEntity<>(imageReference, HttpStatus.OK);
    }

    // Delete ImageReference
    @ApiOperation(response = ImageReference.class, value = "Delete ImageReference") // label for swagger
    @DeleteMapping("/imageReference/{imageRefId}")
    public ResponseEntity<?> deleteImageReference(@PathVariable String imageRefId, @RequestParam String languageId, @RequestParam String partnerId,
                                                  @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill, @RequestParam String pieceId,
                                                  @RequestParam String pieceItemId, @RequestParam String loginUserID,
                                                  @RequestParam String companyId, @RequestParam String authToken) {
        midMileService.deleteImageReference(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, imageRefId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find ImageReference
    @ApiOperation(response = ImageReference[].class, value = "Find ImageReference") //label for swagger
    @PostMapping("/imageReference/find")
    public ImageReference[] findImageReferences(@RequestBody FindImageReference findImageReference, @RequestParam String authToken) throws Exception {
        return midMileService.findImageReference(findImageReference, authToken);
    }

    //==================================================ItemDetails====================================================
    // Get All ItemDetails Details
    @ApiOperation(response = ItemDetails[].class, value = "Get all ItemDetails details")
    // label for swagger
    @GetMapping("/itemDetails")
    public ResponseEntity<?> getAllItemDetails(@RequestParam String authToken) {
        ItemDetails[] itemDetails = midMileService.getAllItemDetails(authToken);
        return new ResponseEntity<>(itemDetails, HttpStatus.OK);
    }

    // Get ItemDetails
    @ApiOperation(response = ItemDetails.class, value = "Get ItemDetails") // label for swagger
    @GetMapping("/itemDetails/{pieceItemId}")
    public ResponseEntity<?> getItemDetails(@PathVariable String pieceItemId, @RequestParam String languageId, @RequestParam String companyId,
                                            @RequestParam String partnerId, @RequestParam String masterAirwayBill,
                                            @RequestParam String houseAirwayBill, @RequestParam String pieceId, @RequestParam String authToken) {
        ItemDetails dbItemDetails = midMileService.getItemDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, authToken);
        return new ResponseEntity<>(dbItemDetails, HttpStatus.OK);
    }


    // Create ItemDetails
    @ApiOperation(response = ItemDetails.class, value = "Create new ItemDetails") // label for swagger
    @PostMapping("/itemDetails")
    public ResponseEntity<?> postItemDetails(@RequestBody AddItemDetails addItemDetails, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ItemDetails createdItemDetails = midMileService.createItemDetails(addItemDetails, loginUserID, authToken);
        return new ResponseEntity<>(createdItemDetails, HttpStatus.OK);
    }

    // Update ItemDetails
    @ApiOperation(response = ItemDetails.class, value = "Update ItemDetails") // label for swagger
    @PatchMapping("/itemDetails/{pieceItemId}")
    public ResponseEntity<?> patchItemDetails(@PathVariable String pieceItemId, @RequestParam String languageId, @RequestParam String companyId,
                                              @RequestParam String partnerId, @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill,
                                              @RequestParam String pieceId, @RequestBody UpdateItemDetails updateItemDetails, @RequestParam String loginUserID,
                                              @RequestParam String authToken) throws IllegalAccessException, InvocationTargetException {
        ItemDetails updatedItemDetails = midMileService.updateItemDetails
                (languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, updateItemDetails, loginUserID, authToken);
        return new ResponseEntity<>(updatedItemDetails, HttpStatus.OK);
    }

    // Delete ItemDetails
    @ApiOperation(response = ItemDetails.class, value = "Delete ItemDetails") // label for swagger
    @DeleteMapping("/itemDetails/{pieceItemId}")
    public ResponseEntity<?> deleteItemDetails(@PathVariable String pieceItemId, @RequestParam String languageId, @RequestParam String companyId,
                                               @RequestParam String partnerId, @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill,
                                               @RequestParam String pieceId, @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteItemDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, pieceItemId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find ItemDetails
    @ApiOperation(response = ItemDetails[].class, value = "Find ItemDetails")//label for swagger
    @PostMapping("/itemDetails/find")
    public ItemDetails[] findItemDetails(@RequestBody FindItemDetails findItemDetails,
                                         @RequestParam String authToken) throws Exception {
        return midMileService.findItemDetails(findItemDetails, authToken);
    }

    //==================================================PieceDetails===================================================
    // Get All PieceDetails Details
    @ApiOperation(response = PieceDetails[].class, value = "Get all PieceDetails details")
    // label for swagger
    @GetMapping("/piecedetails")
    public ResponseEntity<?> getAllPieceDetails(@RequestParam String authToken) {
        PieceDetails[] pieceDetails = midMileService.getAllPieceDetails(authToken);
        return new ResponseEntity<>(pieceDetails, HttpStatus.OK);
    }

    // Get PieceDetails
    @ApiOperation(response = PieceDetails.class, value = "Get PieceDetails") // label for swagger
    @GetMapping("/piecedetails/{pieceId}")
    public ResponseEntity<?> getPieceDetails(@PathVariable String pieceId, @RequestParam String languageId, @RequestParam String companyId,
                                             @RequestParam String partnerId, @RequestParam String masterAirwayBill,
                                             @RequestParam String houseAirwayBill, @RequestParam String authToken) {
        PieceDetails dbPieceDetails = midMileService.getPieceDetails
                (languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, authToken);
        return new ResponseEntity<>(dbPieceDetails, HttpStatus.OK);
    }

    // Create PieceDetails
    @ApiOperation(response = PieceDetails.class, value = "Create new PieceDetails") // label for swagger
    @PostMapping("/piecedetails")
    public ResponseEntity<?> postPieceDetails(@RequestBody AddPieceDetails addPieceDetails, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        PieceDetails createdPieceDetails = midMileService.createPieceDetails
                (addPieceDetails, loginUserID, authToken);
        return new ResponseEntity<>(createdPieceDetails, HttpStatus.OK);
    }

    // Update PieceDetails
    @ApiOperation(response = PieceDetails.class, value = "Update PieceDetails") // label for swagger
    @PatchMapping("/piecedetails/{pieceId}")
    public ResponseEntity<?> patchPieceDetails(@PathVariable String pieceId, @RequestParam String languageId, @RequestParam String companyId,
                                               @RequestParam String partnerId, @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill,
                                               @RequestBody UpdatePieceDetails updatePieceDetails, @RequestParam String loginUserID,
                                               @RequestParam String authToken) throws IllegalAccessException, InvocationTargetException {
        PieceDetails updatedPieceDetails = midMileService.updatePieceDetails
                (languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, updatePieceDetails, loginUserID, authToken);
        return new ResponseEntity<>(updatedPieceDetails, HttpStatus.OK);
    }

    // Delete PieceDetails
    @ApiOperation(response = PieceDetails.class, value = "Delete PieceDetails") // label for swagger
    @DeleteMapping("/piecedetails/{pieceId}")
    public ResponseEntity<?> deletePieceDetails(@PathVariable String pieceId, @RequestParam String languageId, @RequestParam String companyId,
                                                @RequestParam String partnerId, @RequestParam String masterAirwayBill,
                                                @RequestParam String houseAirwayBill, @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deletePieceDetails(languageId, companyId, partnerId, masterAirwayBill, houseAirwayBill, pieceId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find PieceDetails
    @ApiOperation(response = PieceDetails[].class, value = "Find PieceDetails")
    @PostMapping("/piecedetails/find}")
    public ResponseEntity<?> findPieceDetails(@Valid @RequestBody FindPieceDetails findPieceDetails, @RequestParam String authToken) throws Exception {
        PieceDetails[] pieceDetailsList = midMileService.findPieceDetails(findPieceDetails, authToken);
        return new ResponseEntity<>(pieceDetailsList, HttpStatus.OK);
    }

    //=========================================BondedManifestHeader====================================================
    // Get All BondedManifest Details
    @ApiOperation(response = BondedManifest[].class, value = "Get all BondedManifest Details")
    @GetMapping("/bondedManifest")
    public ResponseEntity<?> getAllBondedManifestDetails(@RequestParam String authToken) {
        BondedManifest[] bondedManifest = midMileService.getAllBondedManifest(authToken);
        return new ResponseEntity<>(bondedManifest, HttpStatus.OK);
    }

    // Get BondedManifest
    @ApiOperation(response = BondedManifest.class, value = "Get BondedManifest") // label for swagger
    @GetMapping("/bondedManifest/{bondedId}")
    public ResponseEntity<?> getBondedManifest(@PathVariable String bondedId, @RequestParam String languageId,
                                                     @RequestParam String companyId, @RequestParam String partnerId,
                                                     @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill,
                                                     @RequestParam String authToken) {
        BondedManifest bondedManifest = midMileService.getBondedManifest(languageId, companyId, partnerId,
                masterAirwayBill, houseAirwayBill, bondedId, authToken);
        return new ResponseEntity<>(bondedManifest, HttpStatus.OK);
    }

    // Create new BondedManifest
    @ApiOperation(response = BondedManifest.class, value = "Create new BondedManifest") // label for swagger
    @PostMapping("/bondedManifest/create/list")
    public ResponseEntity<?> postBondedManifest(@Valid @RequestBody List<AddBondedManifest> addBondedManifest,
                                                       @RequestParam String loginUserID, @RequestParam String authToken) {
        BondedManifest[] bondedManifest = midMileService.createBondedManifest(addBondedManifest, loginUserID, authToken);
        return new ResponseEntity<>(bondedManifest, HttpStatus.OK);
    }

    // Create new BondedManifest Based on ConsignmentInput
    @ApiOperation(response = BondedManifest.class, value = "Create new BondedManifest Based On ConsignmentInput") // label for swagger
    @PostMapping("/bondedManifest/create")
    public ResponseEntity<?> postBondedManifestPost(@Valid @RequestBody List<ConsignmentEntity> addConsignments,
                                                      @RequestParam String loginUserID, @RequestParam String authToken) {
        BondedManifest[] bondedManifest = midMileService.createBondedManifestBasedOnConsignment(addConsignments, loginUserID, authToken);
        return new ResponseEntity<>(bondedManifest, HttpStatus.OK);
    }

    // Update BondedManifest
    @ApiOperation(response = BondedManifest.class, value = "Update BondedManifest") // label for swagger
    @PatchMapping("/bondedManifest/update/list")
    public ResponseEntity<?> patchBondedManifest(@Valid @RequestBody List<UpdateBondedManifest> updateBondedManifest,
                                                       @RequestParam String loginUserID, @RequestParam String authToken) {
        BondedManifest[] bondedManifest = midMileService.updateBondedManifest(updateBondedManifest, loginUserID, authToken);
        return new ResponseEntity<>(bondedManifest, HttpStatus.OK);
    }

    // Delete BondedManifest
    @ApiOperation(response = BondedManifest.class, value = "Delete BondedManifest") // label for Swagger
    @PostMapping("/bondedManifest/delete/list")
    public ResponseEntity<?> deleteBondedManifest(@Valid @RequestBody List<BondedManifestDeleteInput> deleteInputList,
                                                        @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteBondedManifest(deleteInputList, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find BondedManifest
    @ApiOperation(response = BondedManifest[].class, value = "Find BondedManifest") // label for swagger
    @PostMapping("/bondedManifest/findBondedManifest")
    public ResponseEntity<?> findBondedManifest(@RequestBody FindBondedManifest findBondedManifest,
                                                       @RequestParam String authToken) throws Exception {
        BondedManifest[] bondedManifest = midMileService.findBondedManifest(findBondedManifest, authToken);
        return new ResponseEntity<>(bondedManifest, HttpStatus.OK);
    }

    //=========================================CcrHeader====================================================
    // Get All Ccr Details
    @ApiOperation(response = Ccr[].class, value = "Get all Ccr Details")
    @GetMapping("/ccr")
    public ResponseEntity<?> getAllCcrDetails(@RequestParam String authToken) {
        Ccr[] ccr = midMileService.getAllCcr(authToken);
        return new ResponseEntity<>(ccr, HttpStatus.OK);
    }

    // Get Ccr
    @ApiOperation(response = Ccr.class, value = "Get Ccr") // label for swagger
    @GetMapping("/ccr/{ccrId}")
    public ResponseEntity<?> getCcr(@PathVariable String ccrId, @RequestParam String languageId,
                                    @RequestParam String companyId, @RequestParam String partnerId,
                                    @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill,
                                    @RequestParam String pieceId, @RequestParam String pieceItemId,
                                    @RequestParam String consoleId, @RequestParam String authToken) {
        Ccr ccr = midMileService.getCcr(languageId, companyId, partnerId,
                masterAirwayBill, houseAirwayBill, consoleId, ccrId, pieceId, pieceItemId, authToken);
        return new ResponseEntity<>(ccr, HttpStatus.OK);
    }

    // Create new Ccr
    @ApiOperation(response = Ccr.class, value = "Create new Ccr") // label for swagger
    @PostMapping("/ccr/create/list")
    public ResponseEntity<?> postCcr(@RequestBody List<AddCcr> addCcr,
                                     @RequestParam String loginUserID, @RequestParam String authToken) {
        Ccr[] ccr = midMileService.createCcr(addCcr, loginUserID, authToken);
        return new ResponseEntity<>(ccr, HttpStatus.OK);
    }

    // Update Ccr
    @ApiOperation(response = Ccr.class, value = "Update Ccr") // label for swagger
    @PatchMapping("/ccr/update/list")
    public ResponseEntity<?> patchCcr(@RequestBody List<UpdateCcr> updateCcr,
                                      @RequestParam String loginUserID, @RequestParam String authToken) {
        Ccr[] ccr = midMileService.updateCcr(updateCcr, loginUserID, authToken);
        return new ResponseEntity<>(ccr, HttpStatus.OK);
    }

    // Delete Ccr
    @ApiOperation(response = Ccr.class, value = "Delete Ccr") // label for Swagger
    @PostMapping("/ccr/delete/list")
    public ResponseEntity<?> deleteCcr(@RequestBody List<CcrDeleteInput> deleteInputList,
                                       @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteCcr(deleteInputList, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Ccr
    @ApiOperation(response = Ccr[].class, value = "Find Ccr") // label for swagger
    @PostMapping("/ccr/findCcr")
    public ResponseEntity<?> findCcrHeaders(@RequestBody FindCcr findCcr,
                                                     @RequestParam String authToken) throws Exception {
        Ccr[] ccr= midMileService.findCcr(findCcr, authToken);
        return new ResponseEntity<>(ccr, HttpStatus.OK);
    }


    //=========================================Console====================================================
    // Get All Console Details
    @ApiOperation(response = Console[].class, value = "Get all Console Details")
    @GetMapping("/console")
    public ResponseEntity<?> getAllConsoleDetails(@RequestParam String authToken) {
        Console[] console = midMileService.getAllConsole(authToken);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Get Console
    @ApiOperation(response = Console.class, value = "Get Console") // label for swagger
    @GetMapping("/Console/{consoleId}")
    public ResponseEntity<?> getConsole(@PathVariable String consoleId, @RequestParam String languageId,
                                        @RequestParam String companyId, @RequestParam String partnerId,
                                        @RequestParam String masterAirwayBill, @RequestParam String houseAirwayBill,
                                        @RequestParam String authToken) {
        Console console = midMileService.getConsole(languageId, companyId, partnerId,
                masterAirwayBill, houseAirwayBill, consoleId, authToken);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Create new Console
    @ApiOperation(response = Console.class, value = "Create new Console") // label for swagger
    @PostMapping("/console/create/list")
    public ResponseEntity<?> postConsole(@RequestBody List<AddConsole> addConsole,
                                         @RequestParam String loginUserID, @RequestParam String authToken) {
        Console[] console = midMileService.createConsole(addConsole, loginUserID, authToken);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Update Console
    @ApiOperation(response = Console.class, value = "Update Console") // label for swagger
    @PatchMapping("/console/update/list")
    public ResponseEntity<?> patchConsole(@RequestBody List<UpdateConsole> updateConsole,
                                          @RequestParam String loginUserID, @RequestParam String authToken) {
        Console[] console = midMileService.updateConsole(updateConsole, loginUserID, authToken);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Delete Console
    @ApiOperation(response = Console.class, value = "Delete Console") // label for Swagger
    @PostMapping("/console/delete/list")
    public ResponseEntity<?> deleteConsole(@RequestBody List<ConsoleDeleteInput> deleteInputList,
                                           @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteConsole(deleteInputList, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Console
    @ApiOperation(response = Console[].class, value = "Find Console") // label for swagger
    @PostMapping("/console/findConsole")
    public ResponseEntity<?> findConsole(@RequestBody FindConsole findConsole,
                                         @RequestParam String authToken) throws Exception {
        Console[] console = midMileService.findConsole(findConsole, authToken);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Console Create consignmentResponse
    @ApiOperation(response = Console[].class, value = "Create Console based on Consignment Input")
    @PostMapping("/console/consignment")
    public ResponseEntity<?> createConsoleBasedOnConInput(@Valid @RequestBody List<ConsignmentEntity> addConsignment, @RequestParam String loginUserID,
                                                          @RequestParam String authToken) {
        Console[] createConsole = midMileService.createConsoleConsignmentInput(addConsignment, loginUserID, authToken);
        return new ResponseEntity<>(createConsole, HttpStatus.OK);
    }

    //Console Transfer
    @ApiOperation(response = Console[].class, value = "Console Transfer")
    @PostMapping("/console/transfer")
    public ResponseEntity<?>consoleTransfer(@Valid @RequestBody List<TransferConsole> transferConsole, @RequestParam String loginUserID,
                                            @RequestParam String authToken) {
        Console[] dbtransferConsole = midMileService.transferConsole(transferConsole, loginUserID, authToken);
        return new ResponseEntity<>(dbtransferConsole, HttpStatus.OK);
    }

    //==================================================ConsignmentStatus====================================================

    // Get All ConsignmentStatus Details
    @ApiOperation(response = ConsignmentStatus[].class, value = "Get all ConsignmentStatus details")
    // label for swagger
    @GetMapping("/consignmentStatus")
    public ResponseEntity<?> getAllConsignmentStatus(@RequestParam String authToken) {
        ConsignmentStatus[] consignmentStatus = midMileService.getAllConsignmentStatus(authToken);
        return new ResponseEntity<>(consignmentStatus, HttpStatus.OK);
    }

    // Get ConsignmentStatus
    @ApiOperation(response = ConsignmentStatus.class, value = "Get ConsignmentStatus") // label for swagger
    @GetMapping("/consignmentStatus/{statusId}")
    public ResponseEntity<?> getConsignmentStatus(@PathVariable String statusId, @RequestParam String languageId, @RequestParam String companyId,
                                                  @RequestParam String houseAirwayBill, @RequestParam String pieceId, @RequestParam String eventCode, @RequestParam String authToken) {
        ConsignmentStatus dbConsignmentStatus = midMileService.getConsignmentStatus(languageId, companyId, houseAirwayBill, pieceId, statusId, eventCode, authToken);
        return new ResponseEntity<>(dbConsignmentStatus, HttpStatus.OK);
    }


    // Create ConsignmentStatus
    @ApiOperation(response = ConsignmentStatus.class, value = "Create new ConsignmentStatus") // label for swagger
    @PostMapping("/consignmentStatus")
    public ResponseEntity<?> postConsignmentStatus(@RequestBody AddConsignmentStatus addConsignmentStatus, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ConsignmentStatus createdConsignmentStatus = midMileService.createConsignmentStatus(addConsignmentStatus, loginUserID, authToken);
        return new ResponseEntity<>(createdConsignmentStatus, HttpStatus.OK);
    }

    // Update ConsignmentStatus
    @ApiOperation(response = ConsignmentStatus.class, value = "Update ConsignmentStatus") // label for swagger
    @PatchMapping("/consignmentStatus/{statusId}")
    public ResponseEntity<?> patchConsignmentStatus(@PathVariable String statusId, @RequestParam String languageId, @RequestParam String companyId,
                                                    @RequestParam String houseAirwayBill, @RequestParam String pieceId, @RequestParam String eventCode, @RequestBody UpdateConsignmentStatus updateConsignmentStatus, @RequestParam String loginUserID,
                                                    @RequestParam String authToken) throws IllegalAccessException, InvocationTargetException {
        ConsignmentStatus updatedConsignmentStatus = midMileService.updateConsignmentStatus
                (languageId, companyId, houseAirwayBill, pieceId, statusId, eventCode, updateConsignmentStatus, loginUserID, authToken);
        return new ResponseEntity<>(updatedConsignmentStatus, HttpStatus.OK);
    }

    // Delete ConsignmentStatus
    @ApiOperation(response = ConsignmentStatus.class, value = "Delete ConsignmentStatus") // label for swagger
    @DeleteMapping("/consignmentStatus/{statusId}")
    public ResponseEntity<?> deleteConsignmentStatus(@PathVariable String statusId, @RequestParam String languageId, @RequestParam String companyId,
                                                     @RequestParam String houseAirwayBill, @RequestParam String pieceId, @RequestParam String eventCode, @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteConsignmentStatus(languageId, companyId, houseAirwayBill, pieceId, statusId, eventCode, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find ConsignmentStatus
    @ApiOperation(response = ConsignmentStatus[].class, value = "Find ConsignmentStatus")//label for swagger
    @PostMapping("/consignmentStatus/find")
    public ConsignmentStatus[] findConsignmentStatus(@RequestBody FindConsignmentStatus findConsignmentStatus,
                                                     @RequestParam String authToken) throws Exception {
        return midMileService.findConsignmentStatus(findConsignmentStatus, authToken);
    }

    // getPdfLabelForm
    @ApiOperation(response = PdfLabelFormOutput[].class, value = "get PdfLabelFormOutput")//label for swagger
    @PostMapping("/pdf/Label")
    public ResponseEntity<?> getPdfLabelFormOutput(@RequestBody LabelFormInput labelFormInput,
                                                   @RequestParam String authToken) throws Exception {
        PdfLabelFormOutput[] pdfLabelFormOutput = midMileService.getPdfLabelForm(labelFormInput, authToken);
        return new ResponseEntity<>(pdfLabelFormOutput, HttpStatus.OK);
    }

//    // Find ConsignmentInvoice
//    @ApiOperation(response = ConsignmentInvoice[].class, value = "Find ConsignmentInvoice") //label for swagger
//    @PostMapping("/consignment/findConsignmentInvoice")
//    public ConsignmentInvoice[] findConsignmentInvoice(@Valid @RequestBody FindConsignmentInvoice findConsignmentInvoice, @RequestParam String authToken) throws Exception {
//        return midMileService.findConsignmentInvoice(findConsignmentInvoice, authToken);
//    }

    // Find ConsignmentInvoice
    @ApiOperation(response = InvoiceForm[].class, value = "Find ConsignmentInvoice") //label for swagger
    @PostMapping("/consignment/findConsignmentInvoice")
    public InvoiceForm[] findConsignmentInvoice(@Valid @RequestBody FindConsignmentInvoice findConsignmentInvoice, @RequestParam String authToken) throws Exception {
        return midMileService.findConsignmentInvoice(findConsignmentInvoice, authToken);
    }
}
