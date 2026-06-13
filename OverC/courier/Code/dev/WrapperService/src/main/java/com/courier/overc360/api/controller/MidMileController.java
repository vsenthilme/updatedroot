package com.courier.overc360.api.controller;

import com.courier.overc360.api.model.b2b.Consignment;
import com.courier.overc360.api.model.idmaster.NotificationMessage;
import com.courier.overc360.api.model.lastmile.*;
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
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Slf4j
@Validated
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
        ConsignmentEntity[] consignment = midMileService.updateConsignment(updateConsignment, loginUserID, authToken);
        return new ResponseEntity<>(consignment, HttpStatus.OK);
    }

    // Find Consignment
    @ApiOperation(response = ConsignmentEntity[].class, value = "Find Consignment") //label for swagger
    @PostMapping("/consignment/find")
    public ConsignmentEntity[] findConsignment(@Valid @RequestBody FindConsignment findConsignment,
                                               @RequestParam String authToken) throws Exception {
        return midMileService.findConsignmentEntity(findConsignment, authToken);
    }

    // Find Consignments - MobileApp
    @ApiOperation(response = ConsignmentEntity[].class, value = "Find Consignments - Mobile App") //label for swagger
    @PostMapping("/consignment/find/mobileApp")
    public ConsignmentEntity[] findConsignmentMobileApp(@Valid @RequestBody List<FindConsignmentMobileApp> findConsignments,
                                                        @RequestParam String authToken) throws Exception {
        return midMileService.findConsignmentMobileApp(findConsignments, authToken);
    }

    // Find Consignments - MobileApp OutScan
    @ApiOperation(response = FindConsignmentOutScanResponse[].class, value = "Find Consignments - Mobile App") //label for swagger
    @PostMapping("/consignment/find/storage/mobileApp")
    public FindConsignmentOutScanResponse[] findConsignmentMobileApp(@Valid @RequestBody FindConsignmentOutScanMobApp findConsignments,
                                                        @RequestParam String authToken) throws Exception {
        return midMileService.findConsignmentOutScanResponses(findConsignments, authToken);
    }

    // Find Consignment Inscan List
    @ApiOperation(response = FindInscanConsignment[].class, value = "Find Consignment Inscan List")
    @PostMapping("/consignment/find/consignmentInScanList")
    public ResponseEntity<?> findInscanConsignment(@RequestParam String languageId,@RequestParam String companyId,@RequestParam String authToken){
        FindInscanConsignment[] findInscanConsignments = midMileService.findInscanConsignments(languageId,companyId,authToken);
        return new ResponseEntity<>(findInscanConsignments,HttpStatus.OK);
    }

    // Update Consignment Inscan Status
    @ApiOperation(response = ConsignmentEntity[].class, value = "Update Consignment Inscan Status")
    @PatchMapping("/consignment/update/hubOps/mobileApp")
    public ResponseEntity<?> updateInscanStatus(@RequestBody List<FindConsignmentScan> findConsignmentScans,@RequestParam String loginUserID,@RequestParam String authToken){
        ConsignmentEntity[] consignment = midMileService.updateConsignmentInscanStatus(findConsignmentScans,loginUserID, authToken);
        return new ResponseEntity<>(consignment, HttpStatus.OK);
    }

    @ApiOperation(response = UpdateInvoice[].class, value = "Update InvoiceUrl")
    @PatchMapping("/consignment/update/invoiceurl")
    public ResponseEntity<?> updateInvoiceUrl(@RequestBody List<UpdateInvoice> updateInvoiceList, @RequestParam String loginUserID, @RequestParam String authToken) {
        UploadApiResponse[] responses = midMileService.updateInvoiceUrl(updateInvoiceList, loginUserID, authToken);

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    // Find Consignment Receiving List
    @ApiOperation(response = FindConsignmentMobileResponse.class, value = "Find Consignment Receiving List")
    @PostMapping("/consignment/find/receiving/mobileApp")
    public ResponseEntity<?> findReceivingConsignment(@Valid @RequestBody List<FindConsignmentMobileApp> findConsignments,@RequestParam String authToken){
        FindConsignmentMobileResponse[] findReceivingConsignments = midMileService.findReceivingConsignments(findConsignments,authToken);
        return new ResponseEntity<>(findReceivingConsignments,HttpStatus.OK);
    }

    // Update MobileApp Receiving Status
    @ApiOperation(response = ConsignmentEntity.class, value = "Update MobileApp Receiving Status")
    @PatchMapping("/consignment/update/receivingStatus/mobileApp")
    public ResponseEntity<?> updateReceivingStatus(@RequestParam String scanId, @RequestParam String statusId ,@RequestParam String storageTypeId,@RequestParam String loginUserID,@RequestParam String authToken){
        ConsignmentEntity updateStatus = midMileService.updateReceivingStatus(scanId,statusId,storageTypeId,loginUserID,authToken);
        return new ResponseEntity<>(updateStatus,HttpStatus.OK);
    }

    // Find Consignment OutScan List
    @ApiOperation(response = FindConsignmentMobileResponse.class, value = "Find Consignment OutScan List")
    @PostMapping("/consignment/find/outscan/mobileApp")
    public ResponseEntity<?> findConsignmentOutscanMobileApp(@Valid @RequestBody List<FindConsignmentMobileApp> findConsignments,@RequestParam String authToken) throws Exception {
        FindConsignmentMobileResponse[] consignmentEntityList = midMileService.findConsignmentHubOpsMobileApp(findConsignments,authToken);
        return new ResponseEntity<>(consignmentEntityList, HttpStatus.OK);
    }

    // Update MobileApp OutScan Status
    @ApiOperation(response = ConsignmentEntity.class, value = "Update MobileApp OutScan Status")
    @PatchMapping("/consignment/update/outscanStatus/mobileApp")
    public ResponseEntity<?> updateOutscanStatus(@RequestParam String scanId, @RequestParam String statusId ,@RequestParam String loginUserID,String authToken){
        ConsignmentEntity updateStatus = midMileService.updateOutScanStatus(scanId,statusId,loginUserID,authToken);
        return new ResponseEntity<>(updateStatus,HttpStatus.OK);
    }

    // Find Consignment Piece List
    @ApiOperation(response = FindPieceRes.class, value = "Find Consignment Piece List")
    @PostMapping("/consignment/find/piece")
    public ResponseEntity<?> findPieceList(@Valid @RequestBody FindPieceReq findPieceReq,@RequestParam String authToken){
        FindPieceRes[] pieceDetails = midMileService.findConsignmentPieceDetails(findPieceReq,authToken);
        return new ResponseEntity<>(pieceDetails,HttpStatus.OK);
    }

    // Get HouseAirWayBill List
    @ApiOperation(response = FindHouseAirwayBill[].class, value = "Get HouseAirWayBill List")
    @PostMapping("/consignment/find/consignmentHAWBList")
    public ResponseEntity<?> findHouseAirwayBillList(@RequestBody FindHAWB findHAWB,@RequestParam String authToken){
        FindHouseAirwayBill[] findData = midMileService.findHouseAirwayBill(findHAWB,authToken);
        return new ResponseEntity<>(findData,HttpStatus.OK);
    }




    // Grouping Consignment by City
//    @ApiOperation(response = ConsignmentEntity.class, value = "Grouping Consignment by City")
//    @GetMapping("/grouped-consignments-by-city")
//    public ResponseEntity<?> getGroupedConsignmentsByCity(@RequestParam String authToken ,@RequestParam String statusId) {
//        Map<String, List<ConsignmentEntity>> groupedByCity = midMileService.groupConsignmentByCityWithStatus(authToken, statusId);
//        return new ResponseEntity<>(groupedByCity, HttpStatus.OK);
//    }

    // Find IConsignmentEntity - null validation column preAlertValidationIndicator
    @ApiOperation(response = IConsignment[].class, value = "Find Consignment preAlertValidationIndicator")
    //label for swagger
    @PostMapping("/consignment/find/v2")
    public IConsignment[] findIConsignment(@Valid @RequestBody FindIConsignment findConsignment, @RequestParam String authToken) throws Exception {
        return midMileService.findIConsignmentEntity(findConsignment, authToken);
    }

    // Find ConsignmentEntity - cassandra
    @ApiOperation(response = ConsignmentEntity[].class, value = "Find Consignment cassandra-Raw Data") //label for swagger
    @PostMapping("/consignment/find/v3")
    public ConsignmentEntity[] findConsignmentV3(@Valid @RequestBody FindCassandraConsignment findConsignment) throws Exception {
        return commonService.findConsignment(findConsignment);
    }

    // Find ConsignmentEntity - cassandra
    @ApiOperation(response = ConsignmentOutput[].class, value = "Find Consignment cassandra-Processed Data") //label for swagger
    @PostMapping("/consignment/find/v4")
    public ConsignmentOutput[] findConsignmentV4(@Valid @RequestBody FindCassandraConsignment findConsignment) throws Exception {
        return commonService.findConsignmentV4(findConsignment);
    }

    // Find PreAlert Manifest - based on consignment details
    @ApiOperation(response = ConsignmentEntity[].class, value = "Find PreAlert Manifest - consignment details based")
    //label for swagger
    @PostMapping("/consignment/findPreAlertManifest")
    public ConsignmentEntity[] findPreAlertManifest(@Valid @RequestBody FindPreAlertManifest findPreAlertManifest, @RequestParam String authToken) throws Exception {
        return midMileService.findPreAlertManifest(findPreAlertManifest, authToken);
    }

    // Find PreAlert Manifest - based on Item details
    @ApiOperation(response = PreAlertManifest[].class, value = "Find PreAlert Manifest - Item Details Based")
    //label for swagger
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
    public ResponseEntity<?> deleteConsignment(@Valid @RequestBody List<ConsignmentDelete> consignmentDeletes, @RequestParam String loginUserID,
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

//    // Create new BondedManifest Based on ConsignmentInput
//    @ApiOperation(response = BondedManifest.class, value = "Create new BondedManifest Based On ConsignmentInput")
//    // label for swagger
//    @PostMapping("/bondedManifest/create")
//    public ResponseEntity<?> postBondedManifestPost(@Valid @RequestBody List<ConsignmentEntity> addConsignments,
//                                                    @RequestParam String loginUserID, @RequestParam String authToken) {
//        BondedManifest[] bondedManifest = midMileService.createBondedManifestBasedOnConsignment(addConsignments, loginUserID, authToken);
//        return new ResponseEntity<>(bondedManifest, HttpStatus.OK);
//    }

    // Create new BondedManifests based on PreAlert Input
    @ApiOperation(response = BondedManifest.class, value = "Create new BondedManifests based On PreAlert Input")
    @PostMapping("/bondedManifest/create/preAlert")
    public ResponseEntity<?> postBondedManifestsFromPreAlert(@Valid @RequestBody List<PreAlert> preAlertList,
                                                             @RequestParam String loginUserID, @RequestParam String authToken) {
        BondedManifest[] bondedManifests = midMileService.createBondedManifestListsOnPreAlertInput(preAlertList, loginUserID, authToken);
        return new ResponseEntity<>(bondedManifests, HttpStatus.OK);
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
                                    @RequestParam String partnerMasterAirwayBill, @RequestParam String partnerHouseAirwayBill,
                                    @RequestParam String pieceId, @RequestParam String pieceItemId,
                                    @RequestParam String consoleId, @RequestParam String authToken) {
        Ccr ccr = midMileService.getCcr(languageId, companyId, partnerId,
                partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, ccrId, pieceId, pieceItemId, authToken);
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
        Ccr[] ccr = midMileService.findCcr(findCcr, authToken);
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
                                        @RequestParam String partnerMasterAirwayBill, @RequestParam String partnerHouseAirwayBill,
                                        @RequestParam String authToken) {
        Console console = midMileService.getConsole(languageId, companyId, partnerId,
                partnerMasterAirwayBill, partnerHouseAirwayBill, consoleId, authToken);
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
    @ApiOperation(response = Console.class, value = "Update Console For CCR Create") // label for swagger
    @PatchMapping("/console/update/ccr/create")
    public ResponseEntity<?> patchConsole(@Valid @RequestBody List<UpdateConsole> updateConsole,
                                          @RequestParam String loginUserID, @RequestParam String authToken) {
        Console[] console = midMileService.updateConsole(updateConsole, loginUserID, authToken);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }


    // Update Console
    @ApiOperation(response = Console.class, value = "Update Console For Mobile App") // label for swagger
    @PatchMapping("/console/update/mobileApp")
    public ResponseEntity<?> patchConsoleForMobileApp(@Valid @RequestBody List<UpdateConsole> updateConsole,
                                                      @RequestParam String loginUserID, @RequestParam String authToken) {
        Console[] console = midMileService.updateConsoleForMobileApp(updateConsole, loginUserID, authToken);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Update Console Normal
    @ApiOperation(response = Console.class, value = "Update Console Normal")
    @PatchMapping("/console/update")
    public ResponseEntity<?> patchConsoleNormal(@Valid @RequestBody List<UpdateConsole> updateConsoleList, @RequestParam String loginUserID,
                                                @RequestParam String authToken) {
        Console[] dbConsole = midMileService.updateConsoleNormal(updateConsoleList, loginUserID, authToken);
        return new ResponseEntity<>(dbConsole, HttpStatus.OK);
    }

    // Delete Console
    @ApiOperation(response = Console.class, value = "Delete Console") // label for Swagger
    @PostMapping("/console/delete/list")
    public ResponseEntity<?> deleteConsole(@RequestBody List<ConsoleDeleteInput> deleteInputList,
                                           @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteConsole(deleteInputList, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Consoles - normal
    @ApiOperation(response = Console[].class, value = "Find Console") // label for swagger
    @PostMapping("/console/findConsole")
    public ResponseEntity<?> findConsole(@Valid @RequestBody FindConsole findConsole,
                                         @RequestParam String authToken) throws Exception {
        Console[] console = midMileService.findConsole(findConsole, authToken);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Find Consoles - MobileApp
    @ApiOperation(response = Console[].class, value = "Find Consoles - Mobile App") // label for swagger
    @PostMapping("/console/findConsole/mobileApp")
    public ResponseEntity<?> findConsolesMobileApp(@Valid @RequestBody FindConsole findConsole,
                                                   @RequestParam String authToken) throws Exception {
        Console[] console = midMileService.findConsoleMobileApp(findConsole, authToken);
        return new ResponseEntity<>(console, HttpStatus.OK);
    }

    // Find Consoles by Pagination
    @ApiOperation(response = Console.class, value = "Find Consoles By Pagination") // label for swagger
    @PostMapping("/console/findConsole/pagination")
    public Page<Console> findConsolesByPagination(@RequestBody FindConsole findConsole,
                                                  @RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                  @RequestParam(defaultValue = "consoleId") String sortBy,
                                                  @RequestParam String authToken) throws Exception {
        return midMileService.findConsolesByPagination(findConsole, pageNo, pageSize, sortBy, authToken);
    }

    // Console Create preAlertResponse
    @ApiOperation(response = Console[].class, value = "Create Console based on PreAlert Input")
    @PostMapping("/console/prealert")
    public ResponseEntity<?> createConsoleBasedOnConInput(@Valid @RequestBody List<PreAlert> preAlerts, @RequestParam String loginUserID,
                                                          @RequestParam String authToken) {
        Console[] createConsole = midMileService.createConsoleBasedOnPreAlertResponse(preAlerts, loginUserID, authToken);
        return new ResponseEntity<>(createConsole, HttpStatus.OK);
    }

    //Console Transfer
    @ApiOperation(response = Console[].class, value = "Console Transfer")
    @PostMapping("/console/transfer")
    public ResponseEntity<?> consoleTransfer(@Valid @RequestBody List<TransferConsole> transferConsole, @RequestParam String loginUserID,
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
    @GetMapping("/consignmentStatus/get")
    public ResponseEntity<?> getConsignmentStatus(@RequestParam String languageId, @RequestParam String companyId,
                                                  @RequestParam String houseAirwayBill, @RequestParam String pieceId, @RequestParam String authToken) {
        ConsignmentStatus dbConsignmentStatus = midMileService.getConsignmentStatus(languageId, companyId, houseAirwayBill, pieceId, authToken);
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
    @PatchMapping("/consignmentStatus/update")
    public ResponseEntity<?> patchConsignmentStatus(@RequestParam String languageId, @RequestParam String companyId,
                                                    @RequestParam String houseAirwayBill, @RequestParam String pieceId,
                                                    @RequestBody UpdateConsignmentStatus updateConsignmentStatus, @RequestParam String loginUserID,
                                                    @RequestParam String authToken) throws IllegalAccessException, InvocationTargetException {
        ConsignmentStatus updatedConsignmentStatus = midMileService.updateConsignmentStatus(
                languageId, companyId, houseAirwayBill, pieceId, updateConsignmentStatus, loginUserID, authToken);
        return new ResponseEntity<>(updatedConsignmentStatus, HttpStatus.OK);
    }

    // Delete ConsignmentStatus
    @ApiOperation(response = ConsignmentStatus.class, value = "Delete ConsignmentStatus") // label for swagger
    @DeleteMapping("/consignmentStatus/delete")
    public ResponseEntity<?> deleteConsignmentStatus(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String houseAirwayBill,
                                                     @RequestParam String pieceId, @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteConsignmentStatus(languageId, companyId, houseAirwayBill, pieceId, loginUserID, authToken);
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
    public InvoiceForm[] findConsignmentInvoice(@Valid @RequestBody FindConsignmentInvoice findConsignmentInvoice,
                                                @RequestParam String authToken) throws Exception {
        return midMileService.findConsignmentInvoice(findConsignmentInvoice, authToken);
    }

    //Add ConsignmentStatus
    @ApiOperation(response = ConsignmentStatus[].class, value = "Add ConsignmentStatus")
    @PostMapping("/consignmentStatus/consignmentAdd")
    public ResponseEntity<?> consignmentAdd(@Valid @RequestBody List<FindConsignmentNew> findConsignmentNew,@RequestParam String loginUserID,@RequestParam String authToken) throws Exception {
        ConsignmentStatus[] consignmentStatusList = midMileService.addConsignmentStatus(findConsignmentNew,loginUserID,authToken);
        return new ResponseEntity<>(consignmentStatusList, HttpStatus.OK);
    }

    // Get All PreAlert Details
    @ApiOperation(response = PreAlert[].class, value = "Get all PreAlert Details")
    @GetMapping("/prealert")
    public ResponseEntity<?> getAllPreAlertDetails(@RequestParam String authToken) {
        PreAlert[] preAlert = midMileService.getAllPreAlert(authToken);
        return new ResponseEntity<>(preAlert, HttpStatus.OK);
    }

    // Get PreAlert
    @ApiOperation(response = PreAlert.class, value = "Get PreAlert") // label for swagger
    @GetMapping("/prealert/{partnerId}")
    public ResponseEntity<?> getPreAlert(@PathVariable String partnerId, @RequestParam String languageId,
                                         @RequestParam String companyId, @RequestParam String partnerMasterAirwayBill,
                                         @RequestParam String partnerHouseAirwayBill,
                                         @RequestParam String authToken) {
        PreAlert preAlert = midMileService.getPreAlert(languageId, companyId, partnerId,
                partnerHouseAirwayBill, partnerMasterAirwayBill, authToken);
        return new ResponseEntity<>(preAlert, HttpStatus.OK);
    }

    // Create new PreAlert
    @ApiOperation(response = PreAlert.class, value = "Create new PreAlert") // label for swagger
    @PostMapping("/prealert/post/list")
    public ResponseEntity<?> postPreAlert(@RequestBody List<PreAlert> addPreAlert,
                                          @RequestParam String loginUserID, @RequestParam String authToken) {
        PreAlert[] preAlert = midMileService.createPreAlerts(addPreAlert, loginUserID, authToken);
        return new ResponseEntity<>(preAlert, HttpStatus.OK);
    }

    // Update PreAlert
    @ApiOperation(response = PreAlert.class, value = "Update PreAlert") // label for swagger
    @PatchMapping("/prealert/update/list")
    public ResponseEntity<?> patchPreAlert(@RequestBody List<UpdatePreAlert> updatePreAlert,
                                           @RequestParam String loginUserID, @RequestParam String authToken) {
        PreAlert[] preAlerts = midMileService.updatePreAlert(updatePreAlert, loginUserID, authToken);
        return new ResponseEntity<>(preAlerts, HttpStatus.OK);
    }

    // Delete PreAlert
    @ApiOperation(response = PreAlert.class, value = "Delete PreAlert") // label for Swagger
    @PostMapping("/prealert/delete/list")
    public ResponseEntity<?> deletePreAlert(@RequestBody List<PreAlertDeleteInput> deleteInputList,
                                            @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deletePreAlert(deleteInputList, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find PreAlert
    @ApiOperation(response = PreAlert[].class, value = "Find PreAlert") // label for swagger
    @PostMapping("/prealert/findPrealert")
    public ResponseEntity<?> findPreAlerts(@RequestBody FindPreAlert findPreAlerts,
                                           @RequestParam String authToken) throws Exception {
        PreAlert[] preAlert = midMileService.findPreAlerts(findPreAlerts, authToken);
        return new ResponseEntity<>(preAlert, HttpStatus.OK);
    }

    // Find PreAlert New
    @ApiOperation(response = FindPreAlertRes[].class, value = "Find New PreAlert")
    @PostMapping("/prealert/findPrealert/new")
    public ResponseEntity<?> findPreAlert(@Valid @RequestBody FindPreAlert findPreAlert,@RequestParam String authToken) throws ExecutionException, InterruptedException {
        FindPreAlertRes[] dbPreAlert = midMileService.findPreAlertNew(findPreAlert,authToken);
        return new ResponseEntity<>(dbPreAlert, HttpStatus.OK);
    }

    // Find MobileApp
    @ApiOperation(response = MobileApp[].class, value = "Get Console for Mobile App")
    @GetMapping("/console/find/mobileapp")
    public ResponseEntity<?> getAllMobileApp(@RequestParam String authToken) {
        MobileApp[] consoles = midMileService.getAllMobileApp(authToken);
        return new ResponseEntity<>(consoles, HttpStatus.OK);
    }

    @ApiOperation(response = ConsoleStatus[].class, value = "Console Status Update ")
    @PostMapping("/console/status-event/update")
    public ResponseEntity<?> updateHawbType(@Valid @RequestBody ConsoleStatus[] consoleStatuses,
                                            @RequestParam String loginUserID,
                                            @RequestParam String authToken) throws Exception {
        Console[] consoles = midMileService.updateConsoleStatus(consoleStatuses, loginUserID, authToken);
        return new ResponseEntity<>(consoles, HttpStatus.OK);
    }

    //===============================================Unconsolidation===================================================
    // Get All Unconsolidation Details
    @ApiOperation(response = Unconsolidation[].class, value = "Get all Unconsolidation Details")
    @GetMapping("/unconsolidation")
    public ResponseEntity<?> getAllUnconsolidationDetails(@RequestParam String authToken) {
        Unconsolidation[] unconsolidations = midMileService.getAllUnconsolidations(authToken);
        return new ResponseEntity<>(unconsolidations, HttpStatus.OK);
    }

    // Get Unconsolidation
    @ApiOperation(response = Unconsolidation.class, value = "Get Unconsolidation") // label for swagger
    @GetMapping("/unconsolidation/get")
    public ResponseEntity<?> getUnconsolidation(@RequestParam String partnerId, @RequestParam String languageId,
                                                @RequestParam String companyId, @RequestParam String partnerMasterAirwayBill,
                                                @RequestParam String partnerHouseAirwayBill, @RequestParam String pieceId,
                                                @RequestParam String authToken) {
        Unconsolidation unconsolidation = midMileService.getUnconsolidation(languageId, companyId, partnerId,
                partnerHouseAirwayBill, partnerMasterAirwayBill, pieceId, authToken);
        return new ResponseEntity<>(unconsolidation, HttpStatus.OK);
    }

    // Create new Unconsolidation
    @ApiOperation(response = Unconsolidation.class, value = "Create new Unconsolidation") // label for swagger
    @PostMapping("/unconsolidation/create")
    public ResponseEntity<?> postUnconsolidation(@RequestBody AddUnconsolidation addUnconsolidation,
                                                 @RequestParam String loginUserID, @RequestParam String authToken) {
        Unconsolidation unconsolidation = midMileService.createUnconsolidation(addUnconsolidation, loginUserID, authToken);
        return new ResponseEntity<>(unconsolidation, HttpStatus.OK);
    }

    // Update Unconsolidation
    @ApiOperation(response = Unconsolidation.class, value = "Update Unconsolidation") // label for swagger
    @PatchMapping("/unconsolidation/update")
    public ResponseEntity<?> patchUnconsolidation(@Valid @RequestBody UpdateUnconsolidation updateUnconsolidation,
                                                  @RequestParam String loginUserID, @RequestParam String authToken) {
        Unconsolidation unconsolidation = midMileService.updateUnconsolidation(updateUnconsolidation, loginUserID, authToken);
        return new ResponseEntity<>(unconsolidation, HttpStatus.OK);
    }

    // Delete Unconsolidation
    @ApiOperation(response = Unconsolidation.class, value = "Delete Unconsolidations - list") // label for Swagger
    @PostMapping("/unconsolidation/delete/list")
    public ResponseEntity<?> deleteUnconsolidation(@RequestBody List<UnconsolidationDeleteInput> deleteInputList,
                                                   @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteUnconsolidations(deleteInputList, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Unconsolidated Shipments
    @ApiOperation(response = Unconsolidation[].class, value = "Find Unconsolidated Shipments") // label for swagger
    @PostMapping("/unconsolidation/find")
    public ResponseEntity<?> findUnconsolidations(@RequestBody FindUnconsolidation findUnconsolidation,
                                                  @RequestParam String authToken) throws Exception {
        Unconsolidation[] unconsolidations = midMileService.findUnconsolidations(findUnconsolidation, authToken);
        return new ResponseEntity<>(unconsolidations, HttpStatus.OK);
    }

    //=====================================================Reports=====================================================
    // GET MobileDashboard - Console count
    @ApiOperation(response = MobileDashboard.class, value = "Get MobileDashboard") // label for swagger
    @PostMapping("/reports/mobileDashboard/get")
    public ResponseEntity<?> postMobileDashboard(@Valid @RequestBody MobileDashboardRequest mobileDashboardRequest,
                                                 @RequestParam String authToken) {
        MobileDashboard dashboard = midMileService.getMobileDashboard(mobileDashboardRequest, authToken);
        return new ResponseEntity<>(dashboard, HttpStatus.OK);
    }

    //GET MobileDashboard - Hub Count
    @PostMapping("/reports/mobileDashboard/hub/count")
    public ResponseEntity<?> hubMobileDashboardCount(@Valid @RequestBody MobileDashboardRequest mobileDashboardRequest,
                                                     @RequestParam String authToken) {
        HubCountResponse hubCountResponse1 = midMileService.getHubDashboardCount(mobileDashboardRequest,authToken);
        return new ResponseEntity<>(hubCountResponse1,HttpStatus.OK);
    }

    // Generate Location Sheet
    @ApiOperation(response = LocationSheetOutput.class, value = "Generate Location Sheet") // label for swagger
    @PostMapping("/reports/locationSheet")
    public ResponseEntity<?> postLocationSheet(@Valid @RequestBody List<LocationSheetInput> sheetInputs,
                                               @RequestParam String loginUserID, @RequestParam String authToken) {
        LocationSheetOutput[] sheetOutputs = midMileService.generateLocationSheet(sheetInputs, loginUserID, authToken);
        return new ResponseEntity<>(sheetOutputs, HttpStatus.OK);
    }

    // Generate Console Tracking Report
    @ApiOperation(response = ConsoleTrackingReportOutput[].class, value = "Generate Console Tracking Report")
    @PostMapping("/reports/consoleTrackingReport")
    public ResponseEntity<?> postConsoleTracking(@Valid @RequestBody ConsoleTrackingReportInput reportInputList,
                                                 @RequestParam String loginUserID, @RequestParam String authToken) {
        ConsoleTrackingReportOutput[] reportOutputs = midMileService.generateConsoleTrackingReport(reportInputList, loginUserID, authToken);
        return new ResponseEntity<>(reportOutputs, HttpStatus.OK);
    }

    // Manual Console Create
    @ApiOperation(response = Console[].class, value = "Manual Console Create")
    @PostMapping("/manual/console/create")
    public ResponseEntity<?> manualConsoleCreate(@Valid @RequestBody List<Console> consoles,
                                                 @RequestParam String loginUserID, @RequestParam String authToken) {
        Console[] consoleCreate = midMileService.manualConsoleCreate(consoles, loginUserID, authToken);
        return new ResponseEntity<>(consoleCreate, HttpStatus.OK);
    }

//    // Generate Console Tracking Report - list Screen
//    @ApiOperation(response = ConsoleTrackingReportOutput[].class, value = "Generate Console Tracking Report - list Screen")
//    @PostMapping("/reports/consoleTrackingReport/listScreen")
//    public ResponseEntity<?> postConsoleTrackingReportListScreen(@Valid @RequestBody ConsoleTrackingReportInput reportInputList,
//                                                                 @RequestParam String loginUserID, @RequestParam String authToken) {
//        ConsoleTrackingReportOutput[] reportOutputs = midMileService.generateConsoleTrackingReportListPage(reportInputList, loginUserID, authToken);
//        return new ResponseEntity<>(reportOutputs, HttpStatus.OK);
//    }


    //==================================================BagTracking====================================================

    // Get All BagTracking Details
    @ApiOperation(response = BagTracking[].class, value = "Get all BagTracking details")
    // label for swagger
    @GetMapping("/bagTracking")
    public ResponseEntity<?> getAllBagTracking(@RequestParam String authToken) {
        BagTracking[] bagTracking = midMileService.getAllBagTracking(authToken);
        return new ResponseEntity<>(bagTracking, HttpStatus.OK);
    }

    // Get BagTracking
    @ApiOperation(response = BagTracking.class, value = "Get BagTracking") // label for swagger
    @GetMapping("/bagTracking/{bagId}")
    public ResponseEntity<?> getBagTracking(@PathVariable Long bagId, @RequestParam String languageId, @RequestParam String companyId, @RequestParam String partnerId,
                                            @RequestParam String houseAirwayBill, @RequestParam String authToken) {
        BagTracking dbBagTracking = midMileService.getBagTracking(languageId, companyId, partnerId, bagId, houseAirwayBill, authToken);
        return new ResponseEntity<>(dbBagTracking, HttpStatus.OK);
    }


    // Create BagTracking
    @ApiOperation(response = BagTracking[].class, value = "Create new BagTracking") // label for swagger
    @PostMapping("/bagTracking")
    public ResponseEntity<?> postBagTracking(@RequestBody List<AddBagTracking> addBagTracking, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        BagTracking[] createdBagTracking = midMileService.createBagTracking(addBagTracking, loginUserID, authToken);
        return new ResponseEntity<>(createdBagTracking, HttpStatus.OK);
    }

    // Create BagTracking from Pickup
    @ApiOperation(response = BagTracking[].class, value = "Create BagTracking from Pickup") //label for swagger
    @PostMapping("/bagTracking/create/pickup")
    public ResponseEntity<?> postBagTrackingFromPickup(@RequestBody List<PickupEntity> pickupEntities, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException {
        BagTracking[] createdBagTracking = midMileService.createBagTrackingFromPickup(pickupEntities, loginUserID, authToken);
        return new ResponseEntity<>(createdBagTracking, HttpStatus.OK);
    }

    // Update BagTracking
    @ApiOperation(response = BagTracking[].class, value = "Update BagTracking") // label for swagger
    @PatchMapping("/bagTracking/update")
    public ResponseEntity<?> patchBagTracking(@RequestBody List<UpdateBagTracking> updateBagTracking, @RequestParam String loginUserID,
                                              @RequestParam String authToken) throws IllegalAccessException, InvocationTargetException {
        BagTracking[] updatedBagTracking = midMileService.updateBagTracking(updateBagTracking, loginUserID, authToken);
        return new ResponseEntity<>(updatedBagTracking, HttpStatus.OK);
    }

    // Delete BagTracking
    @ApiOperation(response = BagTracking.class, value = "Delete BagTracking") // label for swagger
    @PostMapping("/bagTracking/delete")
    public ResponseEntity<?> deleteBagTracking(@RequestBody List<BagTracking> deleteInput,@RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteBagTracking(deleteInput, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find BagTracking
    @ApiOperation(response = BagTracking[].class, value = "Find BagTracking")//label for swagger
    @PostMapping("/bagTracking/find")
    public BagTracking[] findBagTracking(@RequestBody FindBagTracking findBagTracking,
                                         @RequestParam String authToken) throws Exception {
        return midMileService.findBagTracking(findBagTracking, authToken);
    }

    //==================================================FuelTracking===================================================
    // Get All FuelTracking Details
    @ApiOperation(response = FuelTracking[].class, value = "Get all FuelTracking details")
    @GetMapping("/fuelTracking")
    public ResponseEntity<?> getAllFuelTracking(@RequestParam String authToken) {
        FuelTracking[] fuelTracking = midMileService.getAllFuelTracking(authToken);
        return new ResponseEntity<>(fuelTracking, HttpStatus.OK);
    }

    // Get FuelTracking
    @ApiOperation(response = FuelTracking.class, value = "Get FuelTracking") // label for swagger
    @GetMapping("/fuelTracking/{vehicleRegNumber}")
    public ResponseEntity<?> getFuelTracking(@PathVariable String vehicleRegNumber, @RequestParam String companyId,
                                             @RequestParam String languageId, @RequestParam String authToken) {
        FuelTracking dbFuelTracking = midMileService.getFuelTracking(companyId, languageId, vehicleRegNumber, authToken);
        return new ResponseEntity<>(dbFuelTracking, HttpStatus.OK);
    }

    // Create FuelTracking
    @ApiOperation(response = FuelTracking.class, value = "Create new FuelTracking") // label for swagger
    @PostMapping("/fuelTracking")
    public ResponseEntity<?> postFuelTracking(@Valid @RequestBody AddFuelTracking addFuelTracking, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        FuelTracking createdFuelTracking = midMileService.createFuelTracking(addFuelTracking, loginUserID, authToken);
        return new ResponseEntity<>(createdFuelTracking, HttpStatus.OK);
    }

    // Update FuelTracking
    @ApiOperation(response = FuelTracking.class, value = "Update FuelTracking") // label for swagger
    @PatchMapping("/fuelTracking/{vehicleRegNumber}")
    public ResponseEntity<?> patchFuelTracking(@PathVariable String vehicleRegNumber, @RequestParam String companyId, @RequestParam String languageId,
                                               @RequestBody UpdateFuelTracking updateFuelTracking, @RequestParam String loginUserID,
                                               @RequestParam String authToken) {
        FuelTracking updatedFuelTracking = midMileService.updateFuelTracking(companyId, languageId, vehicleRegNumber, updateFuelTracking, loginUserID, authToken);
        return new ResponseEntity<>(updatedFuelTracking, HttpStatus.OK);
    }

    // Delete FuelTracking
    @ApiOperation(response = FuelTracking.class, value = "Delete FuelTracking") // label for swagger
    @DeleteMapping("/fuelTracking/{vehicleRegNumber}")
    public ResponseEntity<?> deleteFuelTracking(@PathVariable String vehicleRegNumber, @RequestParam String companyId,
                                                @RequestParam String languageId, @RequestParam String loginUserID,
                                                @RequestParam String authToken) {
        midMileService.deleteFuelTracking(companyId, languageId, vehicleRegNumber, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find FuelTracking
    @ApiOperation(response = FuelTracking[].class, value = "Find FuelTracking")
    @PostMapping("/fuelTracking/find")
    public ResponseEntity<?> findFuelTracking(@Valid @RequestBody FindFuelTracking findFuelTracking,
                                              @RequestParam String authToken) throws Exception {
        FuelTracking[] fuelTrackingList = midMileService.findFuelTracking(findFuelTracking, authToken);
        return new ResponseEntity<>(fuelTrackingList, HttpStatus.OK);
    }

    //===============================================RiderAssignment===================================================
    // Get All RiderAssignment Details
    @ApiOperation(response = RiderAssignmentEntity[].class, value = "Get all RiderAssignment Details")
    @GetMapping("/riderAssignment")
    public ResponseEntity<?> getAllRiderAssignmentDetails(@RequestParam String authToken) {
        RiderAssignmentEntity[] riderAssignments = midMileService.getAllRiderAssignments(authToken);
        return new ResponseEntity<>(riderAssignments, HttpStatus.OK);
    }

    // Create Rider Assignments
    @ApiOperation(response = RiderAssignmentEntity[].class, value = "Create RiderAssignments")
    @PostMapping("/riderAssignment")
    public ResponseEntity<?> postRiderAssignments(@Valid @RequestBody List<AddConsignment> consignmentList,
                                                  @RequestParam String loginUserID, @RequestParam String authToken) {
        RiderAssignmentEntity[] riderConsignments = midMileService.createRiderConsignments(consignmentList, loginUserID, authToken);
        return new ResponseEntity<>(riderConsignments, HttpStatus.OK);
    }

    // Update RiderAssignments
    @ApiOperation(response = RiderAssignmentEntity.class, value = "Update RiderAssignments") // label for swagger
    @PatchMapping("/riderAssignment/update/list")
    public ResponseEntity<?> patchRiderAssignments(@Valid @RequestBody List<RiderAssignmentEntity> updateRiderAssignment,
                                                   @RequestParam String loginUserID, @RequestParam String authToken) {
        RiderAssignmentEntity[] riderAssignments = midMileService.updateRiderAssignments(updateRiderAssignment, loginUserID, authToken);
        return new ResponseEntity<>(riderAssignments, HttpStatus.OK);
    }

    // Delete RiderAssignments
    @ApiOperation(response = RiderAssignmentEntity.class, value = "Delete RiderAssignments") // label for Swagger
    @PostMapping("/riderAssignment/delete/list")
    public ResponseEntity<?> deleteRiderAssignments(@Valid @RequestBody List<RiderAssignmentDeleteInput> deleteInputList,
                                                    @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteRiderAssignments(deleteInputList, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get RiderAssignment
    @ApiOperation(response = RiderAssignmentEntity.class, value = "Get RiderAssignment")
    @GetMapping("/get")
    public ResponseEntity<?> getRiderAssignment(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String partnerId,
                                                @RequestParam String houseAirwayBill, @RequestParam String pickupId, @RequestParam String authToken) {
        RiderAssignmentEntity riderAssignment = midMileService.getRiderAssignment(
                languageId, companyId, partnerId, houseAirwayBill, pickupId, authToken);
        return new ResponseEntity<>(riderAssignment, HttpStatus.OK);
    }

    // Find RiderAssignments
    @ApiOperation(response = RiderAssignmentEntity[].class, value = "Find RiderAssignments") // label for swagger
    @PostMapping("/riderAssignment/find")
    public RiderAssignmentEntity[] findRiderAssignments(@Valid @RequestBody FindRiderAssignment findUnconsolidation,
                                                        @RequestParam String authToken) throws Exception {
        return midMileService.findRiderAssignments(findUnconsolidation, authToken);
    }

    // Find RiderAssignments
    @ApiOperation(response = CustomsCalculationReport[].class, value = "Find CustomsCalculationReport")
    // label for swagger
    @GetMapping("/customsCalculation/report")
    public CustomsCalculationReport[] findCustomsCalculation(@RequestParam String authToken) throws Exception {
        return midMileService.findCustomsCalculationReport(authToken);
    }

    //==================================================Clearance_Charges===================================================
    // Get All ClearanceCharges Details
    @ApiOperation(response = ClearanceCharges[].class, value = "Get all ClearanceCharges details")
    @GetMapping("/clearanceCharges")
    public ResponseEntity<?> getAllClearanceCharges(@RequestParam String authToken) {
        ClearanceCharges[] clearanceCharges = midMileService.getAllClearanceCharges(authToken);
        return new ResponseEntity<>(clearanceCharges, HttpStatus.OK);
    }

    // Get ClearanceCharges
    @ApiOperation(response = ClearanceCharges.class, value = "Get ClearanceCharges") // label for swagger
    @GetMapping("/clearanceCharges/{clearanceChargesId}")
    public ResponseEntity<?> getClearanceCharges(@PathVariable Long clearanceChargesId, @RequestParam String authToken) {
        ClearanceCharges dbClearanceCharges = midMileService.getClearanceCharges(clearanceChargesId, authToken);
        return new ResponseEntity<>(dbClearanceCharges, HttpStatus.OK);
    }

    // Create ClearanceCharges
    @ApiOperation(response = ClearanceCharges.class, value = "Create new ClearanceCharges") // label for swagger
    @PostMapping("/clearanceCharges")
    public ResponseEntity<?> postClearanceCharges(@Valid @RequestBody List<AddClearanceCharges> addClearanceCharges, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ClearanceCharges[] createdClearanceCharges = midMileService.createClearanceCharges(addClearanceCharges, loginUserID, authToken);
        return new ResponseEntity<>(createdClearanceCharges, HttpStatus.OK);
    }

    // Update ClearanceCharges
    @ApiOperation(response = ClearanceCharges.class, value = "Update ClearanceCharges") // label for swagger
    @PatchMapping("/clearanceCharges/update/list")
    public ResponseEntity<?> patchClearanceCharges(@Valid @RequestBody List<ClearanceCharges> updateClearanceCharges, @RequestParam String loginUserID,
                                                   @RequestParam String authToken) {
        ClearanceCharges[] updatedClearanceCharges = midMileService.updateClearanceCharges(updateClearanceCharges, loginUserID, authToken);
        return new ResponseEntity<>(updatedClearanceCharges, HttpStatus.OK);
    }

    // Delete ClearanceCharges
    @ApiOperation(response = ClearanceCharges.class, value = "Delete ClearanceCharges") // label for swagger
    @PostMapping("/clearanceCharges/delete/list")
    public ResponseEntity<?> deleteClearanceCharges(@RequestBody List<ClearanceCharges> deleteInput, @RequestParam String loginUserID,
                                                    @RequestParam String authToken) {
        midMileService.deleteClearanceCharges(deleteInput, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find ClearanceCharges
    @ApiOperation(response = ClearanceCharges[].class, value = "Find ClearanceCharges")
    @PostMapping("/clearanceCharges/find")
    public ResponseEntity<?> findClearanceCharges(@Valid @RequestBody FindClearanceCharges findClearanceCharges,
                                                  @RequestParam String authToken) throws Exception {
        ClearanceCharges[] clearanceCharges = midMileService.findClearanceCharges(findClearanceCharges, authToken);
        return new ResponseEntity<>(clearanceCharges, HttpStatus.OK);
    }


//===============================================CustomsCosting===================================================

    // Get All CustomsCosting Details
    @ApiOperation(response = CustomsCosting[].class, value = "Get all CustomsCosting details")
    @GetMapping("/customsCosting")
    public ResponseEntity<?> getAllCustomsCosting(@RequestParam String authToken) {
        CustomsCosting[] customsCosting = midMileService.getAllCustomsCosting(authToken);
        return new ResponseEntity<>(customsCosting, HttpStatus.OK);
    }

    // Get CustomsCosting
    @ApiOperation(response = CustomsCosting.class, value = "Get CustomsCosting") // label for swagger
    @GetMapping("/customsCosting/{costCenter}")
    public ResponseEntity<?> getCustomsCosting(@PathVariable String costCenter, @RequestParam String companyId,
                                               @RequestParam String languageId, @RequestParam String customerId,
                                               @RequestParam Long lineNo, @RequestParam String authToken) {
        CustomsCosting dbCustomsCosting = midMileService.getCustomsCosting(companyId, languageId, customerId, costCenter, lineNo, authToken);
        return new ResponseEntity<>(dbCustomsCosting, HttpStatus.OK);
    }

    // Create CustomsCosting
    @ApiOperation(response = CustomsCosting.class, value = "Create new CustomsCosting") // label for swagger
    @PostMapping("/customsCosting")
    public ResponseEntity<?> postCustomsCosting(@Valid @RequestBody List<AddCustomsCosting> addCustomsCosting, @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        AddCustomsCosting[] createdCustomsCosting = midMileService.createCustomsCosting(addCustomsCosting, loginUserID, authToken);
        return new ResponseEntity<>(createdCustomsCosting, HttpStatus.OK);
    }

    // Create CustomsCosting
    @ApiOperation(response = CustomsCosting.class, value = "Create new CustomsCosting") // label for swagger
    @PostMapping("/customsCosting/cost/text")
    public ResponseEntity<?> postCustomsCostingForCostText(@Valid @RequestBody List<CustomsCosting> addCustomsCosting,
                                                           @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        CustomsCosting[] createdCustomsCosting = midMileService.createCustomsCostingForText(addCustomsCosting, loginUserID, authToken);
        return new ResponseEntity<>(createdCustomsCosting, HttpStatus.OK);
    }


    // Update CustomsCosting
    @ApiOperation(response = CustomsCosting.class, value = "Update CustomsCosting") // label for swagger
    @PatchMapping("/customsCosting/update/list")
    public ResponseEntity<?> patchCustomsCosting(@Valid @RequestBody List<CustomsCosting> updateCustomsCosting, @RequestParam String loginUserID,
                                                 @RequestParam String authToken) {
        CustomsCosting[] updatedCustomsCosting = midMileService.updateCustomsCosting(updateCustomsCosting, loginUserID, authToken);
        return new ResponseEntity<>(updatedCustomsCosting, HttpStatus.OK);
    }

    // Delete CustomsCosting
    @ApiOperation(response = CustomsCosting.class, value = "Delete CustomsCosting") // label for swagger
    @PostMapping("/customsCosting/delete/list")
    public ResponseEntity<?> deleteCustomsCosting(@RequestBody List<CustomsCosting> deleteInput, @RequestParam String loginUserID,
                                                  @RequestParam String authToken) {
        midMileService.deleteCustomsCosting(deleteInput, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete CustomCosting Multiple
    @ApiOperation(response= CustomsCosting.class, value = "Delete Multiple CustomsCosting ")
    @PostMapping("/customsCosting/multiple/list")
    public ResponseEntity<?> deleteCustomCosting(@Valid @RequestBody List<CustomsCosting> costCenter,
                                                 @RequestParam String authToken,
                                                 @RequestParam String loginUserID) {
        midMileService.deleteCustomsCostingMultiple(costCenter, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find CustomsCosting
    @ApiOperation(response = CustomsCosting[].class, value = "Find CustomsCosting")
    @PostMapping("/customsCosting/find")
    public ResponseEntity<?> findCustomsCosting(@Valid @RequestBody FindCustomsCosting findCustomsCosting,
                                                @RequestParam String authToken) throws Exception {
        CustomsCosting[] customsCostingList = midMileService.findCustomsCosting(findCustomsCosting, authToken);
        return new ResponseEntity<>(customsCostingList, HttpStatus.OK);
    }

    // Find CustomsCostingInvoice
    @ApiOperation(response = CustomsCostingInvoice[].class, value = "Find CustomsCosting Invoice")
    @PostMapping("/customsCostingInvoice/find")
    public ResponseEntity<?> findCustomsCostingInvoice(@Valid @RequestBody FindCustomInvoice findCustomsCosting,
                                                       @RequestParam String authToken) throws Exception {
        CustomsCostingInvoice[] customsCostingList = midMileService.findCustomsCostingGroupByCostCenter(findCustomsCosting, authToken);
        return new ResponseEntity<>(customsCostingList, HttpStatus.OK);
    }

    // Find Custom Costing Total PDF
    @ApiOperation(response = CustomCostingTotalResult[].class, value = "Find Custom Costing Total PDF")
    @PostMapping("/customsCosting/findCustomsCostingPdf")
    public ResponseEntity<?> findCustomCostingPdf(@Valid @RequestBody CustomCostingTotal customCostingTotal,@RequestParam String authToken) {
        CustomCostingTotalResult[] customCostingTotalResults = midMileService.findCustomCostingResPdf(customCostingTotal,authToken);
        return new ResponseEntity<>(customCostingTotalResults,HttpStatus.OK);
    }

    //===================================================CustomsClearanceInvoice==========================================================

    // Get all CustomsClearanceInvoice Details
    @ApiOperation(response = CustomsClearanceInvoice.class, value = "Get All CustomsClearanceInvoice Details")
    // label for swagger
    @GetMapping("/customsClearanceInvoice")
    public ResponseEntity<?> getAllCustomsClearanceInvoiceDetails(@RequestParam String authToken) {
        CustomsClearanceInvoice[] customsClearanceInvoices = midMileService.getAllCustomsClearanceInvoices(authToken);
        return new ResponseEntity<>(customsClearanceInvoices, HttpStatus.OK);
    }

    // Get CustomsClearanceInvoice
    @ApiOperation(response = CustomsClearanceInvoice.class, value = "Get CustomsClearanceInvoice") // label for swagger
    @GetMapping("/customsClearanceInvoice/get")
    public ResponseEntity<?> getCustomsClearanceInvoice(@RequestParam String languageId, @RequestParam String companyId,
                                                        @RequestParam String partnerHouseAirwayBill, @RequestParam String houseAirwayBill,
                                                        @RequestParam String invoiceNo, @RequestParam String authToken) {
        CustomsClearanceInvoice customsClearanceInvoice = midMileService.getCustomsClearanceInvoice(languageId, companyId,
                partnerHouseAirwayBill, houseAirwayBill, invoiceNo, authToken);
        return new ResponseEntity<>(customsClearanceInvoice, HttpStatus.OK);
    }

    // Create CustomsClearanceInvoice
    @ApiOperation(response = CustomsClearanceInvoice.class, value = "Create new CustomsClearanceInvoice")
    // label for swagger
    @PostMapping("/customsClearanceInvoice")
    public ResponseEntity<?> createCustomsClearanceInvoice(@RequestBody AddCustomsClearanceInvoice addCustomsClearanceInvoice,
                                                           @RequestParam String loginUserID, @RequestParam String authToken) {
        CustomsClearanceInvoice customsClearanceInvoice = midMileService.createCustomsClearanceInvoice(addCustomsClearanceInvoice, loginUserID, authToken);
        return new ResponseEntity<>(customsClearanceInvoice, HttpStatus.OK);
    }

    // Update CustomsClearanceInvoice
    @ApiOperation(response = CustomsClearanceInvoice.class, value = "Update CustomsClearanceInvoice")
    // label for swagger
    @PatchMapping("/customsClearanceInvoice/update")
    public ResponseEntity<?> updateCustomsClearanceInvoice(@RequestParam String languageId, @RequestParam String companyId,
                                                           @RequestParam String partnerHouseAirwayBill, @RequestParam String houseAirwayBill,
                                                           @RequestParam String invoiceNo, @RequestParam String loginUserID,
                                                           @RequestBody UpdateCustomsClearanceInvoice updateCustomsClearanceInvoice, @RequestParam String authToken) {
        CustomsClearanceInvoice customsClearanceInvoice = midMileService.updateCustomsClearanceInvoice(languageId, companyId, partnerHouseAirwayBill, houseAirwayBill, invoiceNo, updateCustomsClearanceInvoice, loginUserID, authToken);
        return new ResponseEntity<>(customsClearanceInvoice, HttpStatus.OK);
    }

    // Delete CustomsClearanceInvoice
    @ApiOperation(response = CustomsClearanceInvoice.class, value = "Delete CustomsClearanceInvoice")
    // label for swagger
    @DeleteMapping("/customsClearanceInvoice/delete")
    public ResponseEntity<?> deleteCustomsClearanceInvoice(@RequestParam String languageId, @RequestParam String companyId,
                                                           @RequestParam String partnerHouseAirwayBill, @RequestParam String houseAirwayBill,
                                                           @RequestParam String invoiceNo,
                                                           @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteCustomsClearanceInvoice(languageId, companyId, partnerHouseAirwayBill, houseAirwayBill, invoiceNo, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find CustomsClearanceInvoice
    @ApiOperation(response = CustomsClearanceInvoice[].class, value = "Find CustomsClearanceInvoice")
    //label for swagger
    @PostMapping("/customsClearanceInvoice/find")
    public CustomsClearanceInvoice[] findCustomsClearanceInvoice(@Valid @RequestBody FindCustomsClearanceInvoice findCustomsClearanceInvoice,
                                                                 @RequestParam String authToken) throws Exception {
        return midMileService.findCustomsClearanceInvoice(findCustomsClearanceInvoice, authToken);
    }

    // Find CustomsClearanceInvoiceReport
    @ApiOperation(response = CustomClearanceInvoiceReport[].class, value = "Find CustomClearanceInvoiceReport")
    //label for swagger
    @PostMapping("/customsClearanceInvoiceReport/find")
    public CustomClearanceInvoiceReport[] findCustomsClearanceInvoiceReport(@Valid @RequestBody FindInvoice findInvoice,
                                                                            @RequestParam String authToken) throws Exception {
        return midMileService.findCustomsClearanceInvoiceReport(findInvoice, authToken);
    }

    //===================================================Invoice==========================================================

    // Get all InvoiceHeader Details
    @ApiOperation(response = InvoiceHeader.class, value = "Get All InvoiceHeader Details") // label for swagger
    @GetMapping("/invoiceHeader")
    public ResponseEntity<?> getAllInvoiceHeaderDetails(@RequestParam String authToken) {
        InvoiceHeader[] invoiceHeaders = midMileService.getAllInvoiceHeader(authToken);
        return new ResponseEntity<>(invoiceHeaders, HttpStatus.OK);
    }

    // Get InvoiceHeader
    @ApiOperation(response = InvoiceHeader.class, value = "Get InvoiceHeader") // label for swagger
    @GetMapping("/invoiceHeader/{invoiceNo}")
    public ResponseEntity<?> getInvoiceHeader(@PathVariable String invoiceNo, @RequestParam String languageId, @RequestParam String companyId,
                                              @RequestParam String authToken) {
        InvoiceHeader invoiceHeader = midMileService.getInvoiceHeader(languageId, companyId, invoiceNo, authToken);
        return new ResponseEntity<>(invoiceHeader, HttpStatus.OK);
    }

    // Create InvoiceHeader
    @ApiOperation(response = InvoiceHeader.class, value = "Create new InvoiceHeader") // label for swagger
    @PostMapping("/invoiceHeader")
    public ResponseEntity<?> createInvoiceHeader(@RequestBody List<InvoiceHeader> invoiceHeaderList, @RequestParam String loginUserID, @RequestParam String authToken) {
        InvoiceHeader[] invoiceHeader = midMileService.createInvoiceHeader(invoiceHeaderList, loginUserID, authToken);
        return new ResponseEntity<>(invoiceHeader, HttpStatus.OK);
    }

    // Update InvoiceHeader
    @ApiOperation(response = InvoiceHeader.class, value = "Update InvoiceHeader") // label for swagger
    @PatchMapping("/invoiceHeader/update")
    public ResponseEntity<?> updateInvoiceHeader(@RequestBody List<InvoiceHeader> invoiceHeaderList, @RequestParam String loginUserID, @RequestParam String authToken) {
        InvoiceHeader[] invoiceHeader = midMileService.updateInvoiceHeader(invoiceHeaderList, loginUserID, authToken);
        return new ResponseEntity<>(invoiceHeader, HttpStatus.OK);
    }

    // Delete InvoiceHeader
    @ApiOperation(response = InvoiceHeader.class, value = "Delete InvoiceHeader") // label for swagger
    @PostMapping("/invoiceHeader/delete")
    public ResponseEntity<?> deleteInvoiceHeader(@Valid @RequestBody List<InvoiceHeader> deleteInvoiceHeader, @RequestParam String loginUserID, @RequestParam String authToken) {
        midMileService.deleteInvoiceHeader(deleteInvoiceHeader, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find InvoiceHeader
    @ApiOperation(response = InvoiceHeader[].class, value = "Find InvoiceHeader") //label for swagger
    @PostMapping("/invoiceHeader/find")
    public ResponseEntity<?> findInvoiceHeader(@RequestBody FindInvoiceHeader findInvoiceHeader, @RequestParam String authToken) throws Exception {
        InvoiceHeader[] invoiceHeader = midMileService.findInvoiceHeader(findInvoiceHeader, authToken);
        return new ResponseEntity<>(invoiceHeader, HttpStatus.OK);
    }

    // Approve CustomsCosting
    @ApiOperation(response = Optional.class, value = "Approve CustomsCosting") // label for swagger
    @GetMapping("/customsCosting/approve/{partnerMasterAirWayBill}")
    public ResponseEntity<?> postCustomsCosting(@PathVariable String partnerMasterAirWayBill, @RequestParam String loginUserID,
                                                @RequestParam String companyId, @RequestParam String languageId, String authToken) {
        midMileService.approveCustomsCosting(companyId, languageId, partnerMasterAirWayBill, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Approve CustomsCosting - Batch
    @ApiOperation(response = Optional.class, value = "Approve CustomsCosting Batch") // label for swagger
    @PostMapping("/customsCosting/approve/batch")
    public ResponseEntity<?> batchApproveCustomsCosting(@RequestBody ApproveCustomCostingInput approveCustomCostingInput,
                                                        @RequestParam String loginUserID, String authToken) {
        midMileService.batchApproveCustomsCosting(approveCustomCostingInput, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Approve(FinanceApproval) CustomsCosting - Batch
    @ApiOperation(response = Optional.class, value = "Batch Approve CustomsCosting - FinanceApproval")      // label for swagger
    @PostMapping("/customsCosting/approve/batch/finance")
    public ResponseEntity<?> batchFinanceApproveCustomsCosting(@RequestBody ApproveCustomCostingInput approveCustomCostingInput,
                                                               @RequestParam String loginUserID, String authToken) {
        midMileService.batchFinanceApproveCustomsCosting(approveCustomCostingInput, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Update BayanValue
    @ApiOperation(response = BayanUpdate.class, value = "Update Bayan Value")
    @PatchMapping("/update/bayan")
    public ResponseEntity<?> updateBayanValue(@Valid @RequestBody List<BayanUpdate> bayanUpdates, @RequestParam String authToken) {
        String bayan = midMileService.updateBayanValue(bayanUpdates, authToken);
        Map<String, String> response = new HashMap<>();
        response.put("message", bayan);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Create new Consignment
    @ApiOperation(response = PickupEntity.class, value = "Create Pickup to Consignment") // label for swagger
    @PostMapping("/pickup/consignment")
    public ResponseEntity<?> postPickupToConsignment(@Valid @RequestBody PickupEntity[] addConsignment,
                                             @RequestParam String loginUserID, String authToken)
            throws IllegalAccessException, InvocationTargetException {
        ConsignmentEntity[] createConsignment = midMileService.createPickupToConsignment(addConsignment, loginUserID, authToken);
        return new ResponseEntity<>(createConsignment, HttpStatus.OK);
    }

    // Find SpecialApproval
    @ApiOperation(response = HsCode.class, value = "Find SpeicalApproval")
    @PostMapping("/find/specialApproval")
    public ResponseEntity<?> findSpecialApproval(@Valid @RequestBody ConsoleRequest consoleRequest,@RequestParam String authToken){
        HsCode[] response = midMileService.findSpecialApproval(consoleRequest, authToken);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    // Consignment to BagTracking Create
    @ApiOperation(response = BagTracking[].class, value = "Consignment to BagTracking Create")
    @PostMapping("/bagTracking/consignmentBagTrack")
    public ResponseEntity<?> createConsignmentBagTrack(@RequestParam String authToken,@Valid @RequestBody List<ConsignmentEntity> consignmentEntities,
                                                       @RequestParam String loginUserID) throws Exception {
        BagTracking[] dbBagTracking = midMileService.createConsignmentToBagTrack(consignmentEntities, loginUserID,authToken);
        return new ResponseEntity<>(dbBagTracking, HttpStatus.OK);
    }

    @ApiOperation(response = Console.class, value = "Send notification")
    @PostMapping("/send/notification")
    public ResponseEntity<?> sendNotification(@RequestParam String companyId, @RequestParam String languageId,
                                              @RequestParam String consoleId, @RequestParam String houseAirwayBill,
                                              @RequestParam String consoleGroupName, @RequestParam String consoleName, @RequestParam String authToken){
        String msg = midMileService.sendNotificationForConsoleCreate(companyId, languageId, consoleId, houseAirwayBill, consoleGroupName, consoleName, authToken);
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @ApiOperation(response = ReadMessages.class, value = "Update Notification Message")
    @PatchMapping("/message/update")
    public ResponseEntity<?> updateNotification(@Valid @RequestBody NotificationMessage[] notificationMessageList, @RequestParam String loginUserID,
                                                @RequestParam String authToken) {
        ReadMessages[] response = midMileService.updateNotificationMessage(notificationMessageList, loginUserID, authToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = UpdateFinance.class, value = "Update Finance")
    @PatchMapping("/update/finance")
    public ResponseEntity<?> updateNotification(@Valid @RequestBody UpdateFinance updateFinance, @RequestParam String loginUserID,
                                                @RequestParam String authToken) {
        UpdateFinance response = midMileService.updateFinance(updateFinance, loginUserID, authToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = Finance.class, value = "Find Finance")
    @PostMapping("finance/find")
    public ResponseEntity<?> findFinance(@Valid @RequestBody FindConsignment findConsignment, @RequestParam String authToken) {
        Finance[] finances = midMileService.findFinance(findConsignment, authToken);
        return new ResponseEntity<>(finances, HttpStatus.OK);
    }

    @ApiOperation(response = PickupPriceList.class, value = "Find PickupPriceList")
    @PostMapping("pickup/priceList")
    public ResponseEntity<?> findPickupPriceList(@Valid @RequestBody PickupFinance[] pickupFinance, @RequestParam String authToken) {
        PickupPriceList[] finances = midMileService.findPickupPriceList(pickupFinance, authToken);
        return new ResponseEntity<>(finances, HttpStatus.OK);
    }

    @ApiOperation(response = ConsignmentDetailsImpl.class, value = "Find Consignment Details")
    @PostMapping("/find/consignment/details")
    public ResponseEntity<?> findConsDetails(@Valid @RequestBody FindConsignment findConsignment, @RequestParam String authToken) throws Exception {
        ConsignmentDetailsImpl[] response = midMileService.findConsignmentDetails(findConsignment, authToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = Delivery[].class, value = "Update Delivery Mobile App")
    @PatchMapping("delivery/mobile/update")
    public ResponseEntity<?> updateDeliveryMob(@Valid @RequestBody List<DeliveryMobUpdateInput> inputs, @RequestParam String loginUserID, @RequestParam String authToken) {
        Delivery[] response = midMileService.updateDeliveryMob(inputs, loginUserID, authToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = CustomerConsignmentStatusCount.class, value = "Find Delivery Status Count")
    @PostMapping("/customerConsignment/status/count")
    public ResponseEntity<?> findCustomerConsignmentCount(@Valid @RequestBody StatusCountInput statusCountInput, @RequestParam String authToken) {
        CustomerConsignmentStatusCount[] response = midMileService.findCustomerConsignmentStatusCount(statusCountInput, authToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = Consignment[].class, value = "Create Consignment From B2B")
    @PostMapping("consignment/create/b2b")
    public ResponseEntity<?> createConsignmentFromB2b(@Valid @RequestBody Consignment[] consignments, @RequestParam String loginUserID,
                                                      @RequestParam String authToken) {
        Consignment[] response = midMileService.createConsignmentFromB2b(consignments, loginUserID, authToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}