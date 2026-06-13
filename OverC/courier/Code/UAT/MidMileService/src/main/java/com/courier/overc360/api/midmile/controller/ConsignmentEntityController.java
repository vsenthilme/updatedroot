package com.courier.overc360.api.midmile.controller;


import com.courier.overc360.api.midmile.primary.model.UploadResponse;
import com.courier.overc360.api.midmile.primary.model.consignment.*;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupEntity;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupFinance;
import com.courier.overc360.api.midmile.primary.model.pickup.PickupPriceList;
import com.courier.overc360.api.midmile.primary.model.prealert.PreAlert;
import com.courier.overc360.api.midmile.replica.model.consignment.*;
import com.courier.overc360.api.midmile.replica.model.dto.FindIConsignment;
import com.courier.overc360.api.midmile.replica.model.dto.FindPreAlertManifest;
import com.courier.overc360.api.midmile.replica.model.dto.IConsignment;
import com.courier.overc360.api.midmile.replica.model.dto.PreAlertManifestConsignment;
import com.courier.overc360.api.midmile.service.*;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Validated
@Api(tags = {"Consignment"}, value = "Consignment Operations related to ConsignmentController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Consignment", description = "Operations related to Consignment")})
@RequestMapping("/consignment")
@RestController
public class ConsignmentEntityController {

    @Autowired
    private ConsignmentService consignmentService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PreAlertService preAlertService;

    @Autowired
    private PreAlertServiceV2 preAlertServiceV2;

    @Autowired
    private PickupToConsignmentService pickupToConsignmentService;

//    @Autowired
//    private EmailService emailService;

    // Create ConsignmentEntity
    @ApiOperation(response = ConsignmentEntity.class, value = "Create Consignment Entity")
    @PostMapping("")
    public ResponseEntity<?> createConsignmentEntity(@Valid @RequestBody List<AddConsignment> consignmentEntityList,
                                                     @RequestParam String loginUserID) throws Exception {
        List<ConsignmentEntity> dbConsignment = consignmentService.createConsignmentEntity(consignmentEntityList, loginUserID);
        return new ResponseEntity<>(dbConsignment, HttpStatus.OK);
    }

    //Consignment Upload
    @ApiOperation(response = AddConsignment.class, value = "Upload Consignment")
    @PostMapping("/upload")
    public ResponseEntity<?> consignmentUpload(@Valid @RequestBody List<AddConsignment> addConsignments, @RequestParam String loginUserID)
            throws Exception {
        List<UploadResponse> uploadResponseList = new ArrayList<>();
        List<ConsignmentEntity> addConsignment = consignmentService.createConsignmentEntity(addConsignments, loginUserID);
        if (!addConsignment.isEmpty()) {
            UploadResponse uploadResponse = new UploadResponse();
            uploadResponse.setStatusCode("200");
            uploadResponse.setStatusCode("Consignment Upload Successfully");
            uploadResponseList.add(uploadResponse);
        }
        return new ResponseEntity<>(uploadResponseList, HttpStatus.OK);
    }

    // Update Consignment
    @ApiOperation(response = ConsignmentEntity.class, value = "Update ConsignmentEntity")
    @PatchMapping("/updateConsignment")
    public ResponseEntity<?> updateConsignmentEntity(@Valid @RequestBody List<UpdateConsignment> updateConsignment,
                                                     @RequestParam String loginUserID)
            throws IOException, InvocationTargetException, IllegalAccessException, CsvException {
        List<ConsignmentEntity> updateConsignments = consignmentService.updateConsignmentEntity(updateConsignment, loginUserID);
        return new ResponseEntity<>(updateConsignments, HttpStatus.OK);
    }

    // Find Consignment
    @ApiOperation(response = ReplicaConsignmentEntity.class, value = "Find ConsignmentEntity") // label for swagger
    @PostMapping("/find")
    public ResponseEntity<?> findImageReference(@Valid @RequestBody FindConsignment findConsignment) throws Exception {
        List<ReplicaConsignmentEntity> consignmentEntityList = consignmentService.findConsignmentEntityAsync(findConsignment);
        return new ResponseEntity<>(consignmentEntityList, HttpStatus.OK);
    }

    // Find Consignment - MobileApp
    @ApiOperation(response = ReplicaConsignmentEntity.class, value = "Find Consignment - MobileApp")
    @PostMapping("/find/mobileApp")
    public ResponseEntity<?> findConsignmentMobileApp(@Valid @RequestBody List<FindConsignmentMobileApp> findConsignments) throws Exception {
        List<ReplicaConsignmentEntity> consignmentEntityList = consignmentService.findConsignmentMobileApp(findConsignments);
        return new ResponseEntity<>(consignmentEntityList, HttpStatus.OK);
    }

    //DeleteConsignment
    @ApiOperation(response = ConsignmentEntity.class, value = "Delete ConsignmentEntity")
    @PostMapping("/delete/list")
    public ResponseEntity<?> deleteConsignmentEntity(@Valid @RequestBody List<ConsignmentDelete> consignmentDeletes, @RequestParam String loginUserID) {
        consignmentService.deleteConsignmentEntity(consignmentDeletes, loginUserID);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/v2/download")
    public ResponseEntity<?> downloadDocument(@RequestParam String sourceUrl, @RequestParam String destinationDir,
                                              @RequestParam String documentName) {
        String filePathWithName = commonService.downLoadDocument(sourceUrl, destinationDir, documentName);
        return new ResponseEntity<>(filePathWithName, HttpStatus.OK);
    }

    //========================================================null validation column==================================================//
    // Find
    @ApiOperation(response = IConsignment.class, value = "Find IConsignmentEntity") // label for swagger
    @PostMapping("/find/v2")
    public ResponseEntity<?> findIConsignment(@RequestBody FindIConsignment findConsignment) throws Exception {
        List<IConsignment> consignmentEntityList = consignmentService.findIConsignment(findConsignment);
        return new ResponseEntity<>(consignmentEntityList, HttpStatus.OK);
    }

    //Find PreAlert Manifest
    @ApiOperation(response = ReplicaConsignmentEntity.class, value = "Find Pre Alert Manifest") // label for swagger
    @PostMapping("/findPreAlertManifest")
    public ResponseEntity<?> findPreAlertManifest(@RequestBody FindPreAlertManifest findPreAlertManifest) throws Exception {
        List<PreAlertManifestConsignment> consignmentEntityList = consignmentService.findPreAlertManifest(findPreAlertManifest);
        return new ResponseEntity<>(consignmentEntityList, HttpStatus.OK);
    }

    // Consignment Invoice
    @ApiOperation(response = ConsignmentInvoice.class, value = "Find ConsignmentInvoice")
    @PostMapping("/findConsignmentInvoice")
    public ResponseEntity<?> findConsignmentInvoice(@Valid @RequestBody FindConsignmentInvoice findConsignmentInvoice) throws Exception {
        List<ConsignmentInvoice> consignmentInvoiceList = consignmentService.findConsignmentInvoice(findConsignmentInvoice);
        return new ResponseEntity<>(consignmentInvoiceList, HttpStatus.OK);
    }

    // Consignment Invoice
    @ApiOperation(response = InvoiceForm.class, value = "Find ConsignmentInvoice Header Line")
    @PostMapping("/consignmentInvoiceGenerate")
    public ResponseEntity<?> findConsignmentInvoiceGenerate(@Valid @RequestBody FindConsignmentInvoice findConsignmentInvoice) throws Exception {
        List<InvoiceForm> consignmentInvoiceList = consignmentService.ConsignmentInvoicePdfGenerate(findConsignmentInvoice);
        return new ResponseEntity<>(consignmentInvoiceList, HttpStatus.OK);
    }

    // PreAlert Create
    @ApiOperation(response = PreAlert.class, value = "PreAlert Create")
    @PostMapping("/post/prealert")
    public ResponseEntity<?> createPreAlert(@Valid @RequestBody List<PreAlert> preAlert, @RequestParam String loginUserID) {
        List<PreAlert> dbPreAlert = preAlertServiceV2.createPreAlertService(preAlert, loginUserID);
        return new ResponseEntity<>(dbPreAlert, HttpStatus.OK);
    }


    // Find Consignment Mobile InScan List
    @ApiOperation(response = FindPickupAssigned.class, value = "Find Consignment Mobile InScan List")
    @PostMapping("/find/consignmentInScanList")
    public ResponseEntity<?> findConsignmentInscan(@RequestParam String languageId, @RequestParam String companyId) {
        List<FindInscanConsignment> findInscanConsignments = consignmentService.findInscanConsignments(languageId, companyId);
        return new ResponseEntity<>(findInscanConsignments, HttpStatus.OK);
    }

    // Update MobileApp Inscan Status
    @ApiOperation(response = ConsignmentEntity.class, value = "Update MobileApp HubOperation")
    @PatchMapping("/update/hubOps/mobileApp")
    public ResponseEntity<?> updateInscanStatus(@RequestBody List<FindConsignmentScan> findConsignmentScans, @RequestParam String loginUserID) {
        List<ConsignmentEntity> updateStatus = consignmentService.updateInscanStatus(findConsignmentScans, loginUserID);
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }

    @ApiOperation(response = ConsignmentEntity.class, value = "Update InvoiceUrl")
    @PatchMapping("/update/invoiceurl")
    public ResponseEntity<?> updateInvoiceUrl(@RequestBody List<UpdateInvoice> updateInvoiceList, @RequestParam String loginUserID) {
        List<UploadResponse> uploadResponseList = new ArrayList<>();
        List<UpdateInvoice> updateInvoices = consignmentService.updateInvoiceUrl(updateInvoiceList, loginUserID);
        if (!updateInvoices.isEmpty()) {
            UploadResponse newUpload = new UploadResponse();
            newUpload.setStatusCode("200");
            newUpload.setMessage("UpdateUrl Successfully");
            uploadResponseList.add(newUpload);
        }
        return new ResponseEntity<>(uploadResponseList, HttpStatus.OK);
    }

    // Find Receiving Consignment - Hub Ops MobileApp
    @ApiOperation(response = FindConsignmentMobileResponse.class, value = "Find Receiving Consignment - Hub Ops MobileApp")
    @PostMapping("/find/receiving/mobileApp")
    public ResponseEntity<?> findConsignmentReceivingMobileApp(@Valid @RequestBody List<FindConsignmentMobileApp> findConsignments) throws Exception {
        List<FindConsignmentMobileResponse> consignmentEntityList = consignmentService.findConsignmentReceivingMobileApp(findConsignments);
        return new ResponseEntity<>(consignmentEntityList, HttpStatus.OK);
    }

    // Update MobileApp Receiving Status
    @ApiOperation(response = ConsignmentEntity.class, value = "Update MobileApp Receiving Status")
    @PatchMapping("/update/receivingStatus/mobileApp")
    public ResponseEntity<?> updateReceivingStatus(@RequestParam String scanId, @RequestParam String statusId, @RequestParam String storageTypeId, @RequestParam String loginUserID) {
        ConsignmentEntity updateStatus = consignmentService.updateReceivingStatus(scanId, statusId, storageTypeId, loginUserID);
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }

    // Find OutScan Consignment - Hub Ops MobileApp
    @ApiOperation(response = FindConsignmentMobileResponse.class, value = "Find OutScan Consignment - Hub Ops MobileApp")
    @PostMapping("/find/outscan/mobileApp")
    public ResponseEntity<?> findConsignmentOutscanMobileApp(@Valid @RequestBody List<FindConsignmentMobileApp> findConsignments) throws Exception {
        List<FindConsignmentMobileResponse> consignmentEntityList = consignmentService.findConsignmentReceivingMobileApp(findConsignments);
        return new ResponseEntity<>(consignmentEntityList, HttpStatus.OK);
    }

    // Update MobileApp OutScan Status
    @ApiOperation(response = ConsignmentEntity.class, value = "Update MobileApp OutScan Status")
    @PatchMapping("/update/outscanStatus/mobileApp")
    public ResponseEntity<?> updateOutscanStatus(@RequestParam String scanId, @RequestParam String statusId, @RequestParam String loginUserID) {
        ConsignmentEntity updateStatus = consignmentService.updateOutScanStatus(scanId, statusId, loginUserID);
        return new ResponseEntity<>(updateStatus, HttpStatus.OK);
    }

    // Find Consignment Piece List
    @ApiOperation(response = FindPieceRes.class, value = "Find Consignment Piece List")
    @PostMapping("/find/piece")
    public ResponseEntity<?> findPieceList(@Valid @RequestBody FindPieceReq findPieceReq) {
        List<FindPieceRes> pieceDetails = consignmentService.findPieceDetails(findPieceReq);
        return new ResponseEntity<>(pieceDetails, HttpStatus.OK);
    }


    // Grouping Consignment by City
//    @ApiOperation(response = ReplicaConsignmentEntity.class, value = "Grouping Consignment by City")
//    @GetMapping("/grouped-consignments-by-city")
//    public ResponseEntity<?> getGroupedConsignmentsByCity(@RequestParam String statusId) {
//        Map<String, List<ReplicaConsignmentEntity>> groupedByCity = consignmentService.groupConsignmentByCityWithStatus(statusId);
//        return new ResponseEntity<>(groupedByCity, HttpStatus.OK);
//    }

//    // Find PreAlert
//    @ApiOperation(response = PreAlert.class, value = "Find PreAlert")
//    @PostMapping("/find/prealert")
//    public ResponseEntity<?> postPreAlert(@Valid @RequestBody FindPreAlert findPreAlert) {
//        List<ReplicaPreAlert> dbPreAlert = preAlertService.findPreAlert(findPreAlert);
//        return new ResponseEntity<>(dbPreAlert, HttpStatus.OK);
//    }

//    @ApiOperation(value = "PushNotification")
//    @PostMapping("notification")
//    public ResponseEntity<?> pushNotification(@RequestParam List<String> tokens) {
//    String title = "PUSH_NOTIFICATION";
//    String message = "TEST CHECKING";
//    pushNotificationService.sendPushNotification(tokens, title, message);
//    return new ResponseEntity<>("Success", HttpStatus.OK);
//
//    }

    // Pickup to Consignment Create
    @ApiOperation(response = PickupEntity.class, value = "Create Consignment From PickupEntity")
    @PostMapping("/pickup")
    public ResponseEntity<?> createConsignmentEntityPickupInput(@Valid @RequestBody List<PickupEntity> pickupEntities,
                                                                @RequestParam String loginUserID) throws Exception {
        List<ConsignmentEntity> dbConsignment = pickupToConsignmentService.createConsignmentEntity(pickupEntities, loginUserID);
        return new ResponseEntity<>(dbConsignment, HttpStatus.OK);
    }

    // Get HouseAirWayBill List
    @ApiOperation(response = FindHouseAirwayBill.class, value = "Get HouseAirWayBill List")
    @PostMapping("/find/consignmentHAWBList")
    public ResponseEntity<?> findHouseAirwayBillList(@RequestBody FindHAWB findHAWB) throws ParseException {
        List<FindHouseAirwayBill> getHawbList = consignmentService.findHouseAirwayBill(findHAWB);
        return new ResponseEntity<>(getHawbList, HttpStatus.OK);
    }

    // Find Console
    @ApiOperation(response = ReplicaConsignmentEntity.class, value = "Find Consignment by Pagination")
    // label for swagger
    @PostMapping("/findConsignment/pagination")
    public ResponseEntity<?> findConsignmentByPagination(@Valid @RequestBody FindConsignment findConsignment) throws Exception {
        List<ReplicaConsignmentEntity> consoleList = consignmentService.findConsignmentEntity(findConsignment);
        return new ResponseEntity<>(consoleList, HttpStatus.OK);
    }

//    @ApiOperation(response = ReplicaConsignmentEntity.class, value = "Find Consignment by Pagination")
//    // label for swagger
//    @PostMapping("/findConsignment/new")
//    public ResponseEntity<?> findCon() throws Exception {
//        List<ConsignmentSummaryDTO> consoleList = consignmentService.findConsignment();
//        return new ResponseEntity<>(consoleList, HttpStatus.OK);
//    }

    @ApiOperation(response = ConsignmentDto.class, value = "Consignment Upload Update")
    @PostMapping("/update/upload")
    public ResponseEntity<?> updateConsignmentUpload(@Valid @RequestBody List<ConsignmentDto> consignmentDto, @RequestParam String loginUserID) {
        List<UploadResponse> uploadResponseList = new ArrayList<>();
        List<ConsignmentEntity> consignmentDtoList = pickupToConsignmentService.updateConsignment(consignmentDto, loginUserID);
        if (!consignmentDtoList.isEmpty()) {
            UploadResponse newUpload = new UploadResponse();
            newUpload.setStatusCode("200");
            newUpload.setMessage("UpdateUrl Successfully");
            uploadResponseList.add(newUpload);
        }
        return new ResponseEntity<>(uploadResponseList, HttpStatus.OK);
    }

    // Find Consignment - MobileApp OutScan
    @ApiOperation(response = ReplicaConsignmentEntity.class, value = "Find Consignment - MobileApp")
    @PostMapping("/find/outscan/storage/mobileApp")
    public ResponseEntity<?> findConsignmentMobileAppOutScan(@Valid @RequestBody FindConsignmentOutScanMobApp findConsignments) throws Exception {
        List<FindConsignmentOutScanResponse> findConsignmentOutScanResponses = consignmentService.findConsignmentOutScanResponses(findConsignments);
        return new ResponseEntity<>(findConsignmentOutScanResponses, HttpStatus.OK);
    }

    // Update Finance
    @ApiOperation(response = UpdateFinance.class, value = "Update Finance")
    @PatchMapping("/update/finance")
    public ResponseEntity<?> updateFinance(@Valid @RequestBody UpdateFinance updateFinance, String loginUserID) {
        UpdateFinance response = pickupToConsignmentService.updateFinances(updateFinance, loginUserID);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = Finance.class, value = "Find fiance")
    @PostMapping("/finance")
    public ResponseEntity<?> findFinance(@Valid @RequestBody FindConsignment findConsignment) throws ParseException {
        List<Finance> response = consignmentService.findFinanceFromConsignment(findConsignment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = PickupFinance.class, value = "Find PickupPriceList")
    @PostMapping("/pickup/priceList")
    public ResponseEntity<?> findPickupPriceList(@Valid @RequestBody List<PickupFinance> pickupFinances) {
        List<PickupPriceList> pickupPriceLists = pickupToConsignmentService.pickupPriceListList(pickupFinances);
        return new ResponseEntity<>(pickupPriceLists, HttpStatus.OK);
    }

    @ApiOperation(response = ConsignmentData.class, value = "Find Consignment Details")
    @PostMapping("/find/consignment/details")
    public ResponseEntity<?> findConsignmentDetails(@Valid @RequestBody FindConsignment findConsignment) throws ParseException {
        List<ConsignmentData> response = consignmentService.findConsignmentDetails(findConsignment);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = ConsignmentData.class, value = "Find Consignment Details")
    @PostMapping("/find/consignment/list")
    public ResponseEntity<?> findConsignmentDetails() throws ParseException {
        List<ConsignmentEntity> response = consignmentService.findConsignmentDetails();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @ApiOperation(value = "Pdf Send Mail")
//    @PostMapping("/pdf/email")
//    public UploadResponse sendEmailWithAttachment(@RequestParam("file") MultipartFile file) {
//        try {
//            File tempFile = File.createTempFile("attachment-", file.getOriginalFilename());
//            file.transferTo(tempFile);
//            // Send email with the PDF
//            emailService.sendEmailPdf(tempFile);
//            // Clean up temporary file
//            tempFile.delete();
//            UploadResponse uploadResponse = new UploadResponse();
//            uploadResponse.setStatusCode("200");
//            uploadResponse.setMessage("Email Send Successfully");
//            return uploadResponse;
//        } catch (IOException e) {
//            e.printStackTrace();
//            UploadResponse uploadResponse = new UploadResponse();
//            uploadResponse.setStatusCode("400");
//            uploadResponse.setMessage(e.getMessage());
//            return uploadResponse;
//        }
//    }
}
