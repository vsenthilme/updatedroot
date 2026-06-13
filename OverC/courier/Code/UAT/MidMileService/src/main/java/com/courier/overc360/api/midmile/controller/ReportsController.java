package com.courier.overc360.api.midmile.controller;

import com.courier.overc360.api.midmile.primary.model.reports.*;
import com.courier.overc360.api.midmile.replica.model.dto.CustomClearanceInvoiceReport;
import com.courier.overc360.api.midmile.replica.model.dto.FindInvoice;
import com.courier.overc360.api.midmile.replica.model.prealert.CustomsCalculationReport;
import com.courier.overc360.api.midmile.service.ReportsService;
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
@Api(tags = {"Reports"}, value = "Reports  Operations related to ReportsController") // label for swagger
@SwaggerDefinition(tags = {@Tag(name = "Reports", description = "Operations related to Reports")})
@RequestMapping("/reports")
@RestController
public class ReportsController {

    @Autowired
    ReportsService reportsService;

    // Get MobileDashboard
    @ApiOperation(response = MobileDashboard.class, value = "Get Mobile Dashboard Count") // label for swagger
    @PostMapping("/mobileDashboard")
    public ResponseEntity<?> postMobileDashboard(@Valid @RequestBody MobileDashboardRequest mobileDashboardRequest) {
        MobileDashboard mobileDashboard = reportsService.getMobileDashboard(mobileDashboardRequest);
        return new ResponseEntity<>(mobileDashboard, HttpStatus.OK);
    }

    //Get Mobile dashboard Hub Count
    @ApiOperation(response = HubCountResponse.class, value = "Mobile Dashboard Hub Count")
    @PostMapping("/mobileDashboard/hub/count")
    public ResponseEntity<?> dashboardHubCount(@Valid @RequestBody MobileDashboardRequest mobileDashboardRequest){
        HubCountResponse hubCountResponse = reportsService.getHubScanAndReceiveCount(mobileDashboardRequest);
        return new ResponseEntity<>(hubCountResponse, HttpStatus.OK);
    }

    // Generate Location Sheet
    @ApiOperation(response = LocationSheetOutput.class, value = "Generate Location Sheet")
    @PostMapping("/locationSheet")
    public ResponseEntity<?> postLocationSheet(@Valid @RequestBody List<LocationSheetInput> locationSheetInputs,
                                               @RequestParam String loginUserID) {
        List<LocationSheetOutput> sheetOutputs = reportsService.generateLocationSheet(locationSheetInputs, loginUserID);
        return new ResponseEntity<>(sheetOutputs, HttpStatus.OK);
    }

    // Generate Console Tracking Report
    @ApiOperation(response = ConsoleTrackingReportOutput.class, value = "Generate Console Tracking Report")
    @PostMapping("/consoleTrackingReport")
    public ResponseEntity<?> postConsoleTrackingReport(@Valid @RequestBody ConsoleTrackingReportInput reportInputs,
                                                       @RequestParam String loginUserID) throws ParseException {
        List<ConsoleTrackingReportOutput> reportOutputs = reportsService.generateConsoleTracking(reportInputs, loginUserID);
        return new ResponseEntity<>(reportOutputs, HttpStatus.OK);
    }

//    // Generate Console Tracking Report - list Screen
//    @ApiOperation(response = ConsoleTrackingReportOutput.class, value = "Generate Console Tracking Report - list page")
//    @PostMapping("/consoleTrackingReport/listScreen")
//    public ResponseEntity<?> postConsoleTrackingListPage(@Valid @RequestBody ConsoleTrackingReportInput reportInputs,
//                                                               @RequestParam String loginUserID) throws ParseException {
//        List<ConsoleTrackingReportOutput> reportOutputs = reportsService.postConsoleTrackingListPage(reportInputs, loginUserID);
//        return new ResponseEntity<>(reportOutputs, HttpStatus.OK);
//    }

    @ApiOperation(response = CustomsCalculationReport.class, value = "Customs Calculation Report")
    @GetMapping("/customsCalculation")
    public ResponseEntity<?> customsCalculation() {
        List<CustomsCalculationReport> reports = reportsService.customsCalculationReportList();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @ApiOperation(response = CustomClearanceInvoiceReport.class, value = "CustomClearanceInvoice")
    @PostMapping("/customInvoice")
    public ResponseEntity<?> customClearance(@Valid @RequestBody FindInvoice findInvoice) {
        List<CustomClearanceInvoiceReport> report = reportsService.customClearanceInvoices(findInvoice);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
