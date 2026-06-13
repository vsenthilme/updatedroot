package com.courier.overc360.api.controller;


import com.courier.overc360.api.model.lastmile.*;
import com.courier.overc360.api.model.lastmile.maps.DistanceMatrix;
import com.courier.overc360.api.model.lastmile.maps.DistanceMatrixRequest;
import com.courier.overc360.api.model.lastmile.maps.GeoInfo;
import com.courier.overc360.api.model.lastmile.maps.MobileTracking;
import com.courier.overc360.api.model.transaction.ConsignmentEntity;
import com.courier.overc360.api.model.transaction.ConsignmentStatus;
import com.courier.overc360.api.model.transaction.FindInvoiceHeader;
import com.courier.overc360.api.service.LastMileService;
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
import java.util.concurrent.ExecutionException;

@Slf4j
@Validated
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/overc-lastmile-service")
@Api(tags = {"LastMile Service"}, value = "LastMile Service Operations") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "User", description = "Operations related to LastMile Service")})
public class LastMileController {

    @Autowired
    LastMileService lastMileService;

    //GET MobileDashboard Count
    @ApiOperation(response = MobileDashboardResponse.class, value = "Get Mobile Dashboard Count")
    @PostMapping("/reports/mobileDashboard/count")
    public ResponseEntity<?> pickupMobileDashboardCount(@Valid @RequestBody MobileDashboardCount mobileDashboardCount,
                                                        @RequestParam String authToken) {
        MobileDashboardResponse mobileDashboardCountResponse = lastMileService.getPickupDashboardCount(mobileDashboardCount,authToken);
        return new ResponseEntity<>(mobileDashboardCountResponse,HttpStatus.OK);
    }

//    //GET MobileDashboard - Delivery Count
//    @ApiOperation(response = DeliveryCountResponse.class, value = "Get Delivery Mobile Dashboard Count")
//    @PostMapping("/reports/deliveryDashboard/count")
//    public ResponseEntity<?> deliveryMobileDashboardCount(@Valid @RequestBody DeliveryMobileApp deliveryMobileApp,
//                                                          @RequestParam String authToken) {
//        DeliveryCountResponse mobileDashboardCountResponse = lastMileService.getDeliveryDashboardCount(deliveryMobileApp,authToken);
//        return new ResponseEntity<>(mobileDashboardCountResponse,HttpStatus.OK);
//    }


    //===============================================Pickup===================================================
    // Get All Pickup Details
    @ApiOperation(response = PickupEntity[].class, value = "Get all Pickup Details")
    @GetMapping("/pickup")
    public ResponseEntity<?> getAllPickupDetails(@RequestParam String authToken) {
        PickupEntity[] pickup = lastMileService.getAllPickup(authToken);
        return new ResponseEntity<>(pickup, HttpStatus.OK);
    }

    // Create Pickup
    @ApiOperation(response = PickupEntity[].class, value = "Create Pickup")
    @PostMapping("/pickup")
    public ResponseEntity<?> postPickup(@Valid @RequestBody List<PickupEntity> pickupEntities,
                                        @RequestParam String loginUserID, @RequestParam String authToken) {
        PickupEntity[] pickup = lastMileService.createPickupConsignments(pickupEntities, loginUserID, authToken);
        return new ResponseEntity<>(pickup, HttpStatus.OK);
    }

    // Update Pickup
    @ApiOperation(response = PickupEntity.class, value = "Update Pickup") // label for swagger
    @PatchMapping("/pickup/update/list")
    public ResponseEntity<?> patchPickup(@Valid @RequestBody List<PickupEntity> updatePickup,
                                         @RequestParam String loginUserID, @RequestParam String authToken) {
        PickupEntity[] pickup = lastMileService.updatePickup(updatePickup, loginUserID, authToken);
        return new ResponseEntity<>(pickup, HttpStatus.OK);
    }

    // Delete Pickup
    @ApiOperation(response = PickupEntity.class, value = "Delete Pickup") // label for Swagger
    @PostMapping("/pickup/delete/list")
    public ResponseEntity<?> deletePickup(@Valid @RequestBody List<PickupDeleteInput> deleteInputList,
                                          @RequestParam String loginUserID, @RequestParam String authToken) {
        lastMileService.deletePickup(deleteInputList, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get Pickup
    @ApiOperation(response = PickupEntity.class, value = "Get Pickup")
    @GetMapping("/pickup/get")
    public ResponseEntity<?> getPickup(@RequestParam String languageId, @RequestParam String companyId, @RequestParam String partnerId,
                                       @RequestParam String houseAirwayBill, @RequestParam String pickupId, @RequestParam String authToken) {
        PickupEntity pickup = lastMileService.getPickup(
                languageId, companyId, partnerId, houseAirwayBill, pickupId, authToken);
        return new ResponseEntity<>(pickup, HttpStatus.OK);
    }

    // Find Pickup
    @ApiOperation(response = PickupEntity[].class, value = "Find Pickup") // label for swagger
    @PostMapping("/pickup/find")
    public PickupEntity[] findPickup(@Valid @RequestBody FindPickup findPickup,
                                     @RequestParam String authToken) throws Exception {
        return lastMileService.findPickup(findPickup, authToken);
    }

    // Find Pickup Assigned List
    @ApiOperation(response = FindPickupAssigned[].class, value = "Find Pickup Assigned List")
    @PostMapping("/pickup/find/pickupAssigned")
    public ResponseEntity<?> findPickupAssigned(@RequestBody PickupMobileAppReq pickupMobileAppReq,@RequestParam String authToken){
        FindPickupAssigned[] findPickupAssign = lastMileService.findPickupAssignedList(pickupMobileAppReq,authToken);
        return new ResponseEntity<>(findPickupAssign,HttpStatus.OK);
    }

    // Find Pickup Assigned Task List
    @ApiOperation(response = FindPickupAssignedTask[].class, value = "Find Pickup Assigned Task List")
    @PostMapping("/pickup/find/pickupAssigned/taskMobileApp")
    public ResponseEntity<?> findPickupAcceptedTask(@RequestBody PickupMobileAppReq pickupMobileAppReq,@RequestParam String authToken){
        FindPickupAssignedTask[] findPickupAssign = lastMileService.findPickupAcceptedList(pickupMobileAppReq,authToken);
        return new ResponseEntity<>(findPickupAssign,HttpStatus.OK);
    }



    // Update Pickup Status
    @ApiOperation(response = PickupEntity[].class, value = "Update Pickup Status") // label for swagger
    @PatchMapping("/pickup/update/pickupStatus/mobileApp")
    public ResponseEntity<?> updatePickupStatus(@Valid @RequestBody List<UpdatePickupStatus> updatePickup,
                                                @RequestParam String loginUserId, @RequestParam String authToken) {
        PickupEntity[] pickup = lastMileService.updatePickupStatus(updatePickup, loginUserId, authToken);
        return new ResponseEntity<>(pickup, HttpStatus.OK);
    }

    // Pickup Update Mobile By Piece
    @ApiOperation(response = PickupEntity[].class, value = "Pickup Update Mobile By Piece")
    @PatchMapping("/pickup/update/pickupByPiece")
    public ResponseEntity<?> updatePickupByPiece(@Valid @RequestBody List<PickupUpdateByPiece> pickupUpdateByPieces, @RequestParam String loginUserID, @RequestParam String authToken){
        PickupEntity[] updateStatus = lastMileService.updatePickupByPieceId(pickupUpdateByPieces,loginUserID,authToken);
        return new ResponseEntity<>(updateStatus,HttpStatus.OK);
    }

    // GetPicker Assignment
    @ApiOperation(response = PickerAssignment.class, value = "Get PickerAssignment Details")
    @GetMapping("/getPicker")
    public ResponseEntity<?>getPickerDetails(@RequestParam String authToken) {
        PickerAssignment[] pickerAssignments = lastMileService.getPickerAssignment(authToken);
        return new ResponseEntity<>(pickerAssignments, HttpStatus.OK);
    }

    // GetPicker Assignment
    @ApiOperation(response = PickerAssignment.class, value = "Get DeliveryAssignment Details")
    @GetMapping("/getDelivery")
    public ResponseEntity<?>getDeliveryAssignmentCount(@RequestParam String authToken) {
        PickerAssignment[] deliveryAssignment = lastMileService.getDeliveryAssignment(authToken);
        return new ResponseEntity<>(deliveryAssignment, HttpStatus.OK);
    }

    //Update Delivery Status
    @ApiOperation(response = Delivery[].class, value = "Update Delivery Status")
    @PatchMapping("/delivery/update/deliveryStatus/mobileApp")
    public ResponseEntity<?> updateDeliveryStatus(@Valid @RequestBody List<UpdateDeliveryStatus> updateDeliveryStatuses,@RequestParam String loginUserId,@RequestParam String authToken){
        Delivery[] deliveries = lastMileService.updateDeliveryStatus(updateDeliveryStatuses,loginUserId,authToken);
        return new ResponseEntity<>(deliveries,HttpStatus.OK);
    }


    /*-------------------------------------------Location------------------------------------*/

    // Get Location with ETA
//    @ApiOperation(response = LastMileService.TspRouteSolution.class, value = "Get Location Details")
//    @PostMapping("/location/sequence")
//    public ResponseEntity<?> getLocationTsp(@RequestBody DistanceMatrixRequest distanceMatrixRequest, @RequestParam String authToken) {
//        LastMileService.TspRouteSolution[] location = lastMileService.getLocationTsp(distanceMatrixRequest, authToken);
//        return new ResponseEntity<>(location, HttpStatus.OK);
//    }
//
//    // Get Location
//    @ApiOperation(response = LastMileService.TspRouteSolution.class, value = "Get Location Details")
//    @PostMapping("/location/sequence/withoutEta")
//    public ResponseEntity<?> getLocationTspWithoutEta(@RequestBody DistanceMatrixRequest distanceMatrixRequest, @RequestParam String authToken) {
//        Location[] location = lastMileService.getLocationTspWithoutEta(distanceMatrixRequest, authToken);
//        return new ResponseEntity<>(location, HttpStatus.OK);
//    }

    // Get Location
    @ApiOperation(response = LastMileService.TspRouteSolution.class, value = "Get Location Details")
    @PostMapping("/address/sequence/withduration")
    public ResponseEntity<?> getLocationTspWithDuration(@RequestBody List<DistanceMatrixRequest> distanceMatrixRequest, @RequestParam String authToken) {
        DistanceMatrix[] location = lastMileService.getLocationTspWithDuration(distanceMatrixRequest, authToken);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    // Get Location
    @ApiOperation(response = LastMileService.TspRouteSolution.class, value = "Get Location Details")
    @PostMapping("/address/sequence/withduration-v2")
    public ResponseEntity<?> getLocationTspWithDurationV2(@RequestBody List<DistanceMatrixRequest> distanceMatrixRequest, @RequestParam String authToken) {
        DistanceMatrix[] location = lastMileService.getLocationTspWithDurationV2(distanceMatrixRequest, authToken);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    // Get GeoInfo
    @ApiOperation(response = LastMileService.TspRouteSolution.class, value = "Get Location Details")
    @PostMapping("/get/geoinfo")
    public ResponseEntity<?> getGeoInfo(@RequestBody GeoInfo geoInfoRequest, @RequestParam String authToken) {
        GeoInfo geoInfo = lastMileService.getGeoInfo(geoInfoRequest, authToken);
        return new ResponseEntity<>(geoInfo, HttpStatus.OK);
    }

    //===============================================Delivery===================================================

    //Create Delivery
    @ApiOperation(response = Delivery.class, value = "Create Delivery")
    @PostMapping("/delivery/create/list")
    public ResponseEntity<?> createDelivery(@Valid @RequestBody List<Delivery> deliveryList,@RequestParam String loginUserID,@RequestParam String authToken){
        Delivery[] deliveries = lastMileService.createDelivery(deliveryList,loginUserID,authToken);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    //Update Delivery
    @ApiOperation(response = Delivery.class, value = "Update Delivery")
    @PatchMapping("/delivery/update/list")
    public ResponseEntity<?> updateDelivery(@Valid @RequestBody List<Delivery> updateDelivery,@RequestParam String loginUserID,@RequestParam String authToken){
        Delivery[] deliveries = lastMileService.updateDelivery(updateDelivery,loginUserID,authToken);
        return new ResponseEntity<>(deliveries,HttpStatus.OK);
    }

    //Delete Delivery
    @ApiOperation(response = Delivery.class, value = "Delete Delivery")
    @PostMapping("/delivery/delete/list")
    public ResponseEntity<?> deleteDelivery(@Valid @RequestBody List<Delivery> deleteDelivery,@RequestParam String loginUserID,@RequestParam String authToken){
        lastMileService.deleteDelivery(deleteDelivery,loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all Delivery Details
    @ApiOperation(response = Delivery.class, value = "Get All Delivery Details") // label for swagger
    @GetMapping("/delivery")
    public ResponseEntity<?> getAllDeliveryDetails(@RequestParam String authToken) {
        Delivery[] deliveries = lastMileService.getAllDelivery(authToken);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    // Find Delivery
    @ApiOperation(response = Delivery[].class, value = "Find Delivery") //label for swagger
    @PostMapping("/delivery/find")
    public ResponseEntity<?> findDelivery(@RequestBody FindDelivery findDelivery, @RequestParam String authToken) throws Exception {
        Delivery[] deliveries = lastMileService.findDeliveryData(findDelivery, authToken);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    // Find Delivery Assigned List
    @ApiOperation(response = FindDeliveryAssigned[].class, value = "Find Delivery Assigned List")
    @PostMapping("/delivery/find/deliveryAssigned")
    public ResponseEntity<?> findDeliveryAssigned(@Valid @RequestBody DeliveryMobileAppReq deliveryMobileAppReq,@RequestParam String authToken){
        FindDeliveryAssigned[] findDeliveryAssigneds = lastMileService.findDeliveryAssignedList(deliveryMobileAppReq,authToken);
        return new ResponseEntity<>(findDeliveryAssigneds,HttpStatus.OK);
    }

    // Find Delivery Assigned Task List
    @ApiOperation(response = FindDeliveryAssignedTask[].class, value = "Find Delivery Assigned Task List")
    @PostMapping("/delivery/find/deliveryAssigned/taskMobileApp")
    public ResponseEntity<?> findDeliveryAcceptedTask(@Valid @RequestBody DeliveryMobileAppReq deliveryMobileAppReq,@RequestParam String authToken){
        FindDeliveryAssignedTask[] findDeliveryAssignedTasks = lastMileService.findDeliveryAcceptedList(deliveryMobileAppReq,authToken);
        return new ResponseEntity<>(findDeliveryAssignedTasks,HttpStatus.OK);
    }


    // Find Delivery Manifest Mobile App List
    @ApiOperation(response = DeliveryManifestMobileAppRes[].class, value = "Find OutScan Delivery Manifest Mobile App")
    @PostMapping("/find/outscan/delivery/manifest/mobileApp")
    public ResponseEntity<?> findDeliveryManifestMobileApp(@Valid @RequestBody DeliveryManifestMobAppInput deliveryManifestMobAppInput,@RequestParam String authToken){
        DeliveryManifestMobileAppRes[] findDeliveryManifest = lastMileService.findDeliveryManifestMobileApp(deliveryManifestMobAppInput,authToken);
        return new ResponseEntity<>(findDeliveryManifest,HttpStatus.OK);
    }

    // Find Delivery HAWB MObile App List
    @ApiOperation(response = FindOutScanMobileApp[].class, value = "Find OutScan Delivery HAWB Mobile App")
    @PostMapping("/find/outscan/delivery/hawb/mobileApp")
    public ResponseEntity<?> findDeliveryHawbMobileApp(@Valid @RequestBody FindOutScanInput findOutScanInput, @RequestParam String authToken) {
        FindOutScanMobileApp[] findOutScanMobileApps = lastMileService.findDeliveryHawbMobileApp(findOutScanInput, authToken);
        return new ResponseEntity<>(findOutScanMobileApps, HttpStatus.OK);
    }

    // Update Status Delivery, Consignment and ConsignmentStatus
    @ApiOperation(response = FindConsignmentOutScanInput[].class, value = "Update Status Delivery, Consignment & ConsignmentStatus")
    @PatchMapping("/update/status/delivery/consignment")
    public ResponseEntity<?> updateStatusDeliveryConsignment(@Valid @RequestBody List<FindConsignmentOutScanInput> findConsignmentOutScanInputs,
                                                             @RequestParam String loginUserID,@RequestParam String authToken) {
        FindConsignmentOutScanInput[] findConsignmentOutScanInputs1 = lastMileService.updateStatusDeliveryConsignment(findConsignmentOutScanInputs, loginUserID, authToken);
        return new ResponseEntity<>(findConsignmentOutScanInputs1, HttpStatus.OK);
    }



    /*----------------------------------------------Mobile Tracking---------------------------------------------------------*/


    // Find MobileTracking
    @ApiOperation(response = MobileTracking.class, value = "Find Mobile Tracking")
    @PostMapping("/mobileTracking")
    public ResponseEntity<?>findMobileTracking(@Valid @RequestBody MobileTracking[] mobileTrackings) {
        MobileTracking[] mobileTrack = lastMileService.findMobileTracking(mobileTrackings);
        return new ResponseEntity<>(mobileTrack, HttpStatus.OK);
    }

    /*-----------------------------------------------DRS---------------------------------------------------------------*/

    // Create Drs
    @ApiOperation(response = Drs.class, value = "Create Drs") // label for swagger
    @PostMapping("/drs/create")
    public ResponseEntity<?> postDrs(@Valid @RequestBody Drs newDrs, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Drs createdDrs = lastMileService.createDrs(newDrs, loginUserID, authToken);
        return new ResponseEntity<>(createdDrs, HttpStatus.OK);
    }

    // Create Drs List
    @ApiOperation(response = Drs.class, value = "Create Drs List") // label for swagger
    @PostMapping("/drs/create/list")
    public ResponseEntity<?> postDrsList(@Valid @RequestBody List<Drs> drsList, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Drs[] createdDrs = lastMileService.createDrsList(drsList, loginUserID, authToken);
        return new ResponseEntity<>(createdDrs, HttpStatus.OK);
    }

    // Update Drs
    @ApiOperation(response = Drs.class, value = "Update Drs") // label for swagger
    @PatchMapping("/drs/update")
    public ResponseEntity<?> patchDrs(@RequestParam String loginUserID, @RequestBody Drs updateDrs, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Drs updatedDrs = lastMileService.updateDrs(updateDrs, loginUserID, authToken);
        return new ResponseEntity<>(updatedDrs, HttpStatus.OK);
    }

    // Update Drs List
    @ApiOperation(response = Drs.class, value = "Update Drs List") // label for swagger
    @PatchMapping("/drs/update/list")
    public ResponseEntity<?> patchDrsList(@RequestParam String loginUserID, @RequestBody List<Drs> updateDrsList, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Drs[] updatedDrs = lastMileService.updateDrsList(updateDrsList, loginUserID, authToken);
        return new ResponseEntity<>(updatedDrs, HttpStatus.OK);
    }

    // Delete Drs
    @ApiOperation(response = Drs.class, value = "Delete Drs") // label for swagger
    @DeleteMapping("/drs/delete")
    public ResponseEntity<?> deleteDrs(@RequestParam String languageId, @RequestParam String companyId
            , @RequestParam String customerId,@RequestParam String houseAirwayBill, @RequestParam String pieceId,@RequestParam String loginUserID, @RequestParam String authToken) {
        lastMileService.deleteDrs(languageId, companyId, customerId, houseAirwayBill, pieceId, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete Drs List
    @ApiOperation(response = Drs.class, value = "Delete Drs") // label for swagger
    @PostMapping("/drs/delete/list")
    public ResponseEntity<?> deleteDrsList(@RequestBody List<Drs> drsList , @RequestParam String loginUserID, @RequestParam String authToken) {
        lastMileService.deleteDrsList(drsList ,loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get All Drs Details
    @ApiOperation(response = Drs.class, value = "Get all Drs details") // label for swagger
    @GetMapping("/drs/getall")
    public ResponseEntity<?> getAllDrs(@RequestParam String authToken) {
        Drs[] replicaDrsList = lastMileService.getAllDrs(authToken);
        return new ResponseEntity<>(replicaDrsList, HttpStatus.OK);
    }

    // Get Drs
    @ApiOperation(response = Drs.class, value = "Get a Drs") // label for swagger
    @GetMapping("/drs/get")
    public ResponseEntity<?> getDrs(@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String customerId, @RequestParam String houseAirwayBill,
                                    @RequestParam String pieceId, @RequestParam String authToken) {

        Drs replicaDrs = lastMileService.getDrs(languageId, companyId, customerId, houseAirwayBill, pieceId, authToken);
        return new ResponseEntity<>(replicaDrs, HttpStatus.OK);
    }

    // Find Drs
    @ApiOperation(response = Drs.class, value = "Find Drs") // label for swagger
    @PostMapping("/drs/find")
    public ResponseEntity<?> findDrs(@RequestBody FindDrs findDrs, @RequestParam String authToken) throws Exception {
        Drs[] createdDrs = lastMileService.findDrs(findDrs, authToken);
        return new ResponseEntity<>(createdDrs, HttpStatus.OK);
    }

    /*-----------------------------------------------InventoryTable---------------------------------------------------------------*/

    // Create InventoryTable
    @ApiOperation(response = InventoryTable.class, value = "Create InventoryTable") // label for swagger
    @PostMapping("/inventoryTable/create")
    public ResponseEntity<?> postInventoryTable(@Valid @RequestBody InventoryTable newInventoryTable, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        InventoryTable createdInventoryTable = lastMileService.createInventoryTable(newInventoryTable, loginUserID, authToken);
        return new ResponseEntity<>(createdInventoryTable, HttpStatus.OK);
    }

    // Create InventoryTable List
    @ApiOperation(response = InventoryTable.class, value = "Create InventoryTable List") // label for swagger
    @PostMapping("/inventoryTable/create/list")
    public ResponseEntity<?> postInventoryTableList(@Valid @RequestBody List<InventoryTable> drsList, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        InventoryTable[] createdInventoryTable = lastMileService.createInventoryTableList(drsList, loginUserID, authToken);
        return new ResponseEntity<>(createdInventoryTable, HttpStatus.OK);
    }

    // Update InventoryTable
    @ApiOperation(response = InventoryTable.class, value = "Update InventoryTable") // label for swagger
    @PatchMapping("/inventoryTable/update")
    public ResponseEntity<?> patchInventoryTable(@RequestParam String loginUserID, @RequestBody InventoryTable updateInventoryTable, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        InventoryTable updatedInventoryTable = lastMileService.updateInventoryTable(updateInventoryTable, loginUserID, authToken);
        return new ResponseEntity<>(updatedInventoryTable, HttpStatus.OK);
    }

    // Update InventoryTable List
    @ApiOperation(response = InventoryTable.class, value = "Update InventoryTable List") // label for swagger
    @PatchMapping("/inventoryTable/update/list")
    public ResponseEntity<?> patchInventoryTableList(@RequestParam String loginUserID, @RequestBody List<InventoryTable> updateInventoryTableList, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        InventoryTable[] updatedInventoryTable = lastMileService.updateInventoryTableList(updateInventoryTableList, loginUserID, authToken);
        return new ResponseEntity<>(updatedInventoryTable, HttpStatus.OK);
    }

    // Delete InventoryTable
    @ApiOperation(response = InventoryTable.class, value = "Delete InventoryTable") // label for swagger
    @DeleteMapping("/inventoryTable/delete")
    public ResponseEntity<?> deleteInventoryTable(@RequestParam String languageId, @RequestParam String companyId
            , @RequestParam String customerId, @RequestParam String houseAirwayBill ,@RequestParam String loginUserID, @RequestParam String authToken) {
        lastMileService.deleteInventoryTable(languageId, companyId, customerId, houseAirwayBill,loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Delete InventoryTable List
    @ApiOperation(response = InventoryTable.class, value = "Delete InventoryTable") // label for swagger
    @PostMapping("/inventoryTable/delete/list")
    public ResponseEntity<?> deleteInventoryTableList(@RequestBody List<InventoryTable> inventoryTableList, @RequestParam String loginUserID, @RequestParam String authToken) {
        lastMileService.deleteInventoryTableList(inventoryTableList, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get All InventoryTable Details
    @ApiOperation(response = InventoryTable.class, value = "Get all InventoryTable details") // label for swagger
    @GetMapping("/inventoryTable/getall")
    public ResponseEntity<?> getAllInventoryTable(@RequestParam String authToken) {
        InventoryTable[] replicaInventoryTableList = lastMileService.getAllInventoryTable(authToken);
        return new ResponseEntity<>(replicaInventoryTableList, HttpStatus.OK);
    }

    // Get InventoryTable
    @ApiOperation(response = InventoryTable.class, value = "Get a InventoryTable") // label for swagger
    @GetMapping("/inventoryTable/get")
    public ResponseEntity<?> getInventoryTable(@RequestParam String languageId, @RequestParam String companyId,
                                               @RequestParam String customerId, @RequestParam String houseAirwayBill,
                                               @RequestParam String authToken) {

        InventoryTable replicaInventoryTable = lastMileService.getInventoryTable(languageId, companyId, customerId, houseAirwayBill, authToken);
        return new ResponseEntity<>(replicaInventoryTable, HttpStatus.OK);
    }

    // Find InventoryTable
    @ApiOperation(response = InventoryTable.class, value = "Find InventoryTable") // label for swagger
    @PostMapping("/inventoryTable/find")
    public ResponseEntity<?> findInventoryTable(@RequestBody FindInventoryTable findInventoryTable, @RequestParam String authToken) throws Exception {
        InventoryTable[] createdInventoryTable = lastMileService.findInventoryTable(findInventoryTable, authToken);
        return new ResponseEntity<>(createdInventoryTable, HttpStatus.OK);
    }

    /*-----------------------------------------------Debrief---------------------------------------------------------------*/

    // Create Debrief
    @ApiOperation(response = Debrief.class, value = "Create Debrief") // label for swagger
    @PostMapping("/debrief/create")
    public ResponseEntity<?> postDebrief(@Valid @RequestBody Debrief newDebrief, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Debrief createdDebrief = lastMileService.createDebrief(newDebrief, loginUserID, authToken);
        return new ResponseEntity<>(createdDebrief, HttpStatus.OK);
    }

    // Update Debrief
    @ApiOperation(response = Debrief.class, value = "Update Debrief") // label for swagger
    @PatchMapping("/debrief/update")
    public ResponseEntity<?> patchDebrief(@RequestParam String loginUserID, @RequestBody Debrief updateDebrief, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Debrief updatedDebrief = lastMileService.updateDebrief(updateDebrief, loginUserID, authToken);
        return new ResponseEntity<>(updatedDebrief, HttpStatus.OK);
    }

    // Delete Debrief
    @ApiOperation(response = Debrief.class, value = "Delete Debrief") // label for swagger
    @DeleteMapping("/debrief/delete")
    public ResponseEntity<?> deleteDebrief(@RequestParam String languageId, @RequestParam String companyId
            , @RequestParam String courierId ,@RequestParam String loginUserID, @RequestParam String authToken) {
        lastMileService.deleteDebrief(languageId, companyId, courierId,loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Debrief
    @ApiOperation(response = Debrief.class, value = "Find Debrief") // label for swagger
    @PostMapping("/debrief/find")
    public ResponseEntity<?> findDebrief(@RequestBody FindDebrief findDebrief, @RequestParam String authToken) throws Exception {
        Debrief[] createdDebrief = lastMileService.findDebrief(findDebrief, authToken);
        return new ResponseEntity<>(createdDebrief, HttpStatus.OK);
    }

    /*-----------------------------------------------NPR---------------------------------------------------------------*/

    // Create Npr List
    @ApiOperation(response = Npr[].class, value = "Create Npr List") // label for swagger
    @PostMapping("/npr/create/list")
    public ResponseEntity<?> postNprList(@Valid @RequestBody List<Npr> nprList, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Npr[] createdDrs = lastMileService.createNprList(nprList, loginUserID, authToken);
        return new ResponseEntity<>(createdDrs, HttpStatus.OK);
    }

    // Update Npr List
    @ApiOperation(response = Npr[].class, value = "Update Npr List") // label for swagger
    @PatchMapping("/npr/update/list")
    public ResponseEntity<?> patchNprList(@RequestParam String loginUserID, @RequestBody List<Npr> updateNprList, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Npr[] updatedNpr = lastMileService.updateNprList(updateNprList, loginUserID, authToken);
        return new ResponseEntity<>(updatedNpr, HttpStatus.OK);
    }

    // Delete Npr List
    @ApiOperation(response = Npr.class, value = "Delete Npr") // label for swagger
    @PostMapping("/npr/delete/list")
    public ResponseEntity<?> deleteNprList(@RequestBody List<Npr> nprList , @RequestParam String loginUserID, @RequestParam String authToken) {
        lastMileService.deleteNprList(nprList ,loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // Get All Npr Details
    @ApiOperation(response = Npr[].class, value = "Get all Npr details") // label for swagger
    @GetMapping("/npr/getAll")
    public ResponseEntity<?> getAllNpr(@RequestParam String authToken) {
        Npr[] replicaNprList = lastMileService.getAllNpr(authToken);
        return new ResponseEntity<>(replicaNprList, HttpStatus.OK);
    }

    // Get Npr
    @ApiOperation(response = Npr.class, value = "Get a Npr") // label for swagger
    @GetMapping("/npr/get")
    public ResponseEntity<?> getNpr(@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String pickupId, @RequestParam String authToken) {

        Npr replicaNpr = lastMileService.getNpr(languageId, companyId, pickupId, authToken);
        return new ResponseEntity<>(replicaNpr, HttpStatus.OK);
    }

    // Find Npr
    @ApiOperation(response = Npr.class, value = "Find Npr") // label for swagger
    @PostMapping("/npr/find")
    public ResponseEntity<?> findNpr(@RequestBody FindNpr findNpr, @RequestParam String authToken) throws Exception {
        Npr[] createdNpr = lastMileService.findNpr(findNpr, authToken);
        return new ResponseEntity<>(createdNpr, HttpStatus.OK);
    }

    /*-----------------------------------------------Reschedule Pickup---------------------------------------------------------------*/

    // Create Reschedule Pickup List
    @ApiOperation(response = ReSchedulePickUp[].class, value = "Create Reschedule Pickup List") // label for swagger
    @PostMapping("/reschedulePickup/create/list")
    public ResponseEntity<?> postReschedulePickupList(@Valid @RequestBody List<ReSchedulePickUp> reSchedulePickUps, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ReSchedulePickUp[] createdReschedulePickup = lastMileService.createReschedulePickup(reSchedulePickUps, loginUserID,authToken);
        return new ResponseEntity<>(createdReschedulePickup, HttpStatus.OK);
    }

    // Update Reschedule List
    @ApiOperation(response = ReSchedulePickUp[].class, value = "Update Reschedule List") // label for swagger
    @PatchMapping("/reschedulePickup/update/list")
    public ResponseEntity<?> patchRescheduleList(@RequestParam String loginUserID, @RequestBody List<ReSchedulePickUp> updateRescheduleList,@RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        ReSchedulePickUp[] updatedReschedulePickup = lastMileService.updateRescheduleList(updateRescheduleList, loginUserID,authToken);
        return new ResponseEntity<>(updatedReschedulePickup, HttpStatus.OK);
    }

    // Delete Reschedule List
    @ApiOperation(response = ReSchedulePickUp.class, value = "Delete Reschedule Pickup") // label for swagger
    @PostMapping("/reschedulePickup/delete/list")
    public ResponseEntity<?> deleteRescheduleList(@RequestBody List<ReSchedulePickUp> deleteRescheduleList, @RequestParam String loginUserID,@RequestParam String authToken) {
        lastMileService.deleteReschedulePickupList(deleteRescheduleList, loginUserID,authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get All Reschedule Pickup Details
    @ApiOperation(response = ReSchedulePickUp[].class, value = "Get all Reschedule Pickup details") // label for swagger
    @GetMapping("/reschedulePickup/getAll")
    public ResponseEntity<?> getAllReschedulePickup(@RequestParam String authToken) {
        ReSchedulePickUp[] reSchedulePickUps = lastMileService.getAllReschedulePickup(authToken);
        return new ResponseEntity<>(reSchedulePickUps, HttpStatus.OK);
    }

    // Get Reschedule Pickup
    @ApiOperation(response = ReSchedulePickUp.class, value = "Get a Reschedule Pickup") // label for swagger
    @GetMapping("/reschedulePickup/get")
    public ResponseEntity<?> getReschedulePickup(@RequestParam String languageId, @RequestParam String companyId,
                                                 @RequestParam String pickupId,@RequestParam String authToken) {

        ReSchedulePickUp replicaReSchedulePickUp = lastMileService.getReplicaReschedule(languageId, companyId, pickupId,authToken);
        return new ResponseEntity<>(replicaReSchedulePickUp, HttpStatus.OK);
    }


    // Find Reschedule Pickup
    @ApiOperation(response = ReSchedulePickUp[].class, value = "Find Reschedule Pickup") // label for swagger
    @PostMapping("/reschedulePickup/find")
    public ResponseEntity<?> findReschedulePickup(@RequestBody FindReschedulePickup findReschedulePickup,@RequestParam String authToken) throws Exception {
        ReSchedulePickUp[] findReschedule = lastMileService.findReschedulePickup(findReschedulePickup,authToken);
        return new ResponseEntity<>(findReschedule, HttpStatus.OK);
    }





    /*-----------------------------------------------RescheduleDelivery---------------------------------------------------------------*/

    // Create RescheduleDelivery
    @ApiOperation(response = RescheduleDelivery.class, value = "Create RescheduleDelivery") // label for swagger
    @PostMapping("/rescheduledelivery/create")
    public ResponseEntity<?> postRescheduleDelivery(@Valid @RequestBody RescheduleDelivery newRescheduleDelivery, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        RescheduleDelivery createdRescheduleDelivery = lastMileService.createRescheduleDelivery(newRescheduleDelivery, loginUserID, authToken);
        return new ResponseEntity<>(createdRescheduleDelivery, HttpStatus.OK);
    }

    // Update RescheduleDelivery
    @ApiOperation(response = RescheduleDelivery.class, value = "Update RescheduleDelivery") // label for swagger
    @PatchMapping("/rescheduledelivery/update")
    public ResponseEntity<?> patchRescheduleDelivery(@RequestParam String loginUserID, @RequestBody UpdateRescheduleDelivery updateRescheduleDelivery, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        RescheduleDelivery updatedRescheduleDelivery = lastMileService.updateRescheduleDelivery(updateRescheduleDelivery, loginUserID, authToken);
        return new ResponseEntity<>(updatedRescheduleDelivery, HttpStatus.OK);
    }

    // Delete RescheduleDelivery
    @ApiOperation(response = RescheduleDelivery.class, value = "Delete RescheduleDelivery") // label for swagger
    @DeleteMapping("/rescheduledelivery/delete")
    public ResponseEntity<?> deleteRescheduleDelivery(@RequestParam String languageId, @RequestParam String companyId
            , @RequestParam String deliveryId ,@RequestParam String loginUserID, @RequestParam String authToken) {
        lastMileService.deleteRescheduleDelivery(languageId, companyId, deliveryId,loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get All RescheduleDelivery Details
    @ApiOperation(response = RescheduleDelivery.class, value = "Get all RescheduleDelivery details") // label for swagger
    @GetMapping("/rescheduledelivery/getall")
    public ResponseEntity<?> getAllRescheduleDelivery(@RequestParam String authToken) {
        RescheduleDelivery[] replicaRescheduleDeliveryList = lastMileService.getAllRescheduleDelivery(authToken);
        return new ResponseEntity<>(replicaRescheduleDeliveryList, HttpStatus.OK);
    }

    // Get RescheduleDelivery
    @ApiOperation(response = RescheduleDelivery.class, value = "Get a RescheduleDelivery") // label for swagger
    @GetMapping("/rescheduledelivery/get")
    public ResponseEntity<?> getRescheduleDelivery(@RequestParam String languageId, @RequestParam String companyId,
                                                   @RequestParam String deliveryId,
                                                   @RequestParam String authToken) {

        RescheduleDelivery replicaRescheduleDelivery = lastMileService.getRescheduleDelivery(languageId, companyId, deliveryId, authToken);
        return new ResponseEntity<>(replicaRescheduleDelivery, HttpStatus.OK);
    }

    // Find RescheduleDelivery
    @ApiOperation(response = RescheduleDelivery.class, value = "Find RescheduleDelivery") // label for swagger
    @PostMapping("/rescheduledelivery/find")
    public ResponseEntity<?> findRescheduleDelivery(@RequestBody FindRescheduleDelivery findRescheduleDelivery, @RequestParam String authToken) throws Exception {
        RescheduleDelivery[] createdRescheduleDelivery = lastMileService.findRescheduleDelivery(findRescheduleDelivery, authToken);
        return new ResponseEntity<>(createdRescheduleDelivery, HttpStatus.OK);
    }

    /*-----------------------------------------------NDR---------------------------------------------------------------*/

    // Create Ndr List
    @ApiOperation(response = Ndr[].class, value = "Create Ndr List") // label for swagger
    @PostMapping("/ndr/create/list")
    public ResponseEntity<?> postNdrList(@Valid @RequestBody List<Ndr> ndrList, @RequestParam String loginUserID, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Ndr[] createdNdr = lastMileService.createNdrList(ndrList, loginUserID, authToken);
        return new ResponseEntity<>(createdNdr, HttpStatus.OK);
    }

    // Update Ndr List
    @ApiOperation(response = Ndr[].class, value = "Update Ndr List") // label for swagger
    @PatchMapping("/ndr/update/list")
    public ResponseEntity<?> patchNdrList(@RequestParam String loginUserID, @RequestBody List<Ndr> updateNdrList, @RequestParam String authToken)
            throws IllegalAccessException, InvocationTargetException, IOException, CsvException {
        Ndr[] updatedNdr = lastMileService.updateNdrList(updateNdrList, loginUserID, authToken);
        return new ResponseEntity<>(updatedNdr, HttpStatus.OK);
    }

    // Delete Ndr List
    @ApiOperation(response = Ndr.class, value = "Delete Ndr") // label for swagger
    @PostMapping("/ndr/delete/list")
    public ResponseEntity<?> deleteNdrList(@RequestBody List<Ndr> ndrList , @RequestParam String loginUserID, @RequestParam String authToken) {
        lastMileService.deleteNdrList(ndrList ,loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // Get All Ndr Details
    @ApiOperation(response = Ndr[].class, value = "Get all Ndr details") // label for swagger
    @GetMapping("/ndr/getAll")
    public ResponseEntity<?> getAllNdr(@RequestParam String authToken) {
        Ndr[] replicaNdrList = lastMileService.getAllNdr(authToken);
        return new ResponseEntity<>(replicaNdrList, HttpStatus.OK);
    }

    // Get Ndr
    @ApiOperation(response = Ndr.class, value = "Get a Ndr") // label for swagger
    @GetMapping("/ndr/get")
    public ResponseEntity<?> getNdr(@RequestParam String languageId, @RequestParam String companyId,
                                    @RequestParam String deliveryId, @RequestParam String authToken) {

        Ndr replicaNdr = lastMileService.getNdr(languageId, companyId, deliveryId, authToken);
        return new ResponseEntity<>(replicaNdr, HttpStatus.OK);
    }

    // Find Ndr
    @ApiOperation(response = Ndr.class, value = "Find Ndr") // label for swagger
    @PostMapping("/ndr/find")
    public ResponseEntity<?> findNdr(@RequestBody FindNdr findNdr, @RequestParam String authToken) throws Exception {
        Ndr[] createdNdr = lastMileService.findNdr(findNdr, authToken);
        return new ResponseEntity<>(createdNdr, HttpStatus.OK);
    }

    // Consignment To Pickup Create
    @ApiOperation(response = ConsignmentEntity[].class, value = "Consignment To Pickup Create")
    @PostMapping("/consignment/pickup")
    public ResponseEntity<?> createConsignmentToPickup(@Valid @RequestBody List<ConsignmentEntity> consignmentEntityList, @RequestParam String loginUserID,
                                                          @RequestParam String authToken) {
        PickupEntity[] pickupToConsignment = lastMileService.createPickupToConsignment(consignmentEntityList, loginUserID, authToken);
        return new ResponseEntity<>(pickupToConsignment, HttpStatus.OK);
    }

    // Consignment To Pickup Create
    @ApiOperation(response = ConsignmentEntity[].class, value = "Consignment To Pickup Create")
    @PostMapping("/consignment/delivery")
    public ResponseEntity<?> createConsignmentToDelivery(@Valid @RequestBody List<ConsignmentEntity> consignmentEntityList, @RequestParam String loginUserID,
                                                       @RequestParam String authToken) {
        Delivery[] deliveries = lastMileService.createDeliveryFromConsignment(consignmentEntityList, loginUserID, authToken);
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    // Consolidation Create Pickup
    @ApiOperation(response = PickupEntity[].class, value = "Consolidation Create Pickup")
    @PostMapping("/pickup/consolidation")
    public ResponseEntity<?> createConsolidationApi(@RequestParam String authToken,@Valid @RequestBody FindConsolidation findConsolidation,
                                                    @RequestParam String loginUserID) throws IOException,
            InvocationTargetException, IllegalAccessException, CsvException, ExecutionException, InterruptedException {
        PickupEntity[] dbPickup = lastMileService.createConsolidationPickup(findConsolidation, loginUserID,authToken);
        return new ResponseEntity<>(dbPickup, HttpStatus.OK);
    }

    //Pickup & Assign consignment update
    @ApiOperation(response = ConsignmentStatus[].class, value = "Pickup & Assign consignment update")
    @PostMapping("/pickup/assign/consignmentUpdate")
    public ResponseEntity<?> consignmentUpdate(@RequestParam String authToken,@Valid @RequestBody List<FindConsignmentData> addConsignmentStatuses, @RequestParam String loginUserID) throws Exception {
        ConsignmentStatus[] consignmentStatusList = lastMileService.addConsignmentStatus(addConsignmentStatuses,loginUserID,authToken);
        return new ResponseEntity<>(consignmentStatusList, HttpStatus.OK);
    }

    @ApiOperation(response = LMDInvoiceHeader.class, value = "Create lmdInvoice")
    @PostMapping("/invoice/create")
    public ResponseEntity<?> createDeliveryList(@Valid @RequestBody List<LMDInvoiceHeader> lmdInvoiceHeaders,@RequestParam String loginUserID,@RequestParam String authToken){
        LMDInvoiceHeader[] invoiceHeaders = lastMileService.createLmdInvoice(lmdInvoiceHeaders,loginUserID,authToken);
        return new ResponseEntity<>(invoiceHeaders, HttpStatus.OK);
    }

    //Update Delivery
    @ApiOperation(response = LMDInvoiceHeader.class, value = "Update LMDInvoiceHeader")
    @PatchMapping("/invoice/update")
    public ResponseEntity<?> updateLMDInvoice(@Valid @RequestBody LMDInvoiceHeader[] updateInvoice,@RequestParam String loginUserID,@RequestParam String authToken){
        LMDInvoiceHeader[] invoiceHeaders = lastMileService.updateInvoiceHeader(updateInvoice,loginUserID,authToken);
        return new ResponseEntity<>(invoiceHeaders,HttpStatus.OK);
    }

    //Delete Delivery
    @ApiOperation(response = LMDInvoiceHeader.class, value = "Delete LMD InvoiceHeader")
    @PostMapping("/invoice/delete")
    public ResponseEntity<?> deleteLMDInvoice(@Valid @RequestBody DeleteInvoice[] deleteInvoices,@RequestParam String loginUserID,@RequestParam String authToken) {
        lastMileService.deleteInvoice(deleteInvoices, loginUserID, authToken);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Find Ndr
    @ApiOperation(response = LMDInvoiceHeader.class, value = "Find LMDInvoiceHeader") // label for swagger
    @PostMapping("/invoice/find")
    public  ResponseEntity<?> findInvoice(@Valid @RequestBody FindInvoiceHeader deleteInvoice, @RequestParam String authToken) throws Exception {
        LMDInvoiceHeader[] createdNdr = lastMileService.findInvoiceHeader(deleteInvoice, authToken);
        return new ResponseEntity<>(createdNdr, HttpStatus.OK);
    }

    @ApiOperation(response = LMDInvoice.class, value = "Manual Create Invoice ")
    @PostMapping("/invoice/manual")
    public ResponseEntity<?> manualInvoice(@Valid @RequestBody LMDInvoice lmdInvoice, @RequestParam String loginUserID, @RequestParam String authToken) {
        LMDInvoiceHeader response = lastMileService.manualCreateInvoice(lmdInvoice, loginUserID, authToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = DeliveryStatusCount.class, value = "Find Delivery Status Count")
    @PostMapping("/delivery/status/count")
    public ResponseEntity<?> findCount(@Valid @RequestBody StatusCountInput statusCountInput, @RequestParam String authToken) {
        DeliveryStatusCount[] response = lastMileService.findDeliveryStatusCount(statusCountInput, authToken);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
