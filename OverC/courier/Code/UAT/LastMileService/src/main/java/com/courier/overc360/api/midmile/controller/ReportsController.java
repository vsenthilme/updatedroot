package com.courier.overc360.api.midmile.controller;


import com.courier.overc360.api.midmile.primary.model.maps.MobileTracking;
import com.courier.overc360.api.midmile.primary.model.reports.DeliveryCountResponse;
import com.courier.overc360.api.midmile.primary.model.reports.DeliveryMobileApp;
import com.courier.overc360.api.midmile.primary.model.reports.MobileDashboardCount;
import com.courier.overc360.api.midmile.primary.model.reports.MobileDashboardResponse;
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

    // Get Mobile Dashboard Count
    @ApiOperation(response = MobileDashboardResponse.class, value = "Get Mobile Dashboard Count") // label for swagger
    @PostMapping("/mobileDashboard/count")
    public ResponseEntity<?> dashboardCount(@Valid @RequestBody MobileDashboardCount mobileDashboardCount){
        MobileDashboardResponse mobileDashboardCountResponse  = reportsService.getMobileDashboardCount(mobileDashboardCount);
        return new ResponseEntity<>(mobileDashboardCountResponse, HttpStatus.OK);
    }

//    // Get Delivery Mobile Dashboard Count
//    @ApiOperation(response = DeliveryCountResponse.class, value = "Get Delivery Mobile Dashboard Count") // label for swagger
//    @PostMapping("/deliveryDashboard/count")
//    public ResponseEntity<?> deliveryMobileDashboard(@Valid @RequestBody DeliveryMobileApp deliveryMobileApp){
//        DeliveryCountResponse mobileDashboardCountResponse  = reportsService.getDeliveryAppCount(deliveryMobileApp);
//        return new ResponseEntity<>(mobileDashboardCountResponse, HttpStatus.OK);
//    }

    // Mobile Tracking API
    @ApiOperation(response = MobileTracking.class, value = "Mobile Tracking ")
    @PostMapping("/mobileTrack")
    public ResponseEntity<?> postMobileTracking(@Valid @RequestBody List<MobileTracking> mobileTrackingList) {
        List<MobileTracking> mobileTracking = reportsService.findMobileTracking(mobileTrackingList);
        return new ResponseEntity<>(mobileTracking, HttpStatus.OK);
    }





}
