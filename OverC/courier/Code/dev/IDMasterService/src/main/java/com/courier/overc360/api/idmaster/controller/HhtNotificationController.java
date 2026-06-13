package com.courier.overc360.api.idmaster.controller;

import com.courier.overc360.api.idmaster.primary.model.hhtnotification.FindHhtNotification;
import com.courier.overc360.api.idmaster.primary.model.hhtnotification.HhtNotification;
import com.courier.overc360.api.idmaster.service.HhtNotificationService;
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
@Api(tags = {"HhtNotification"}, value = "HhtNotification  Operations related to HhtNotificationController")
// label for swagger
@SwaggerDefinition(tags = {@Tag(name = "HhtNotification", description = "Operations related to HhtNotification")})
@RequestMapping("/hhtnotification")
@RestController
public class HhtNotificationController {

    @Autowired
    HhtNotificationService hhtNotificationService;

    // Create HhtNotification
    @ApiOperation(response = HhtNotification.class, value = "Create HhtNotification") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> postHhtNotification(@Valid @RequestBody HhtNotification newHhtNotification,
                                                 @RequestParam String loginUserID) {
        HhtNotification createdHhtNotification = hhtNotificationService.createHhtNotification(newHhtNotification, loginUserID);
        return new ResponseEntity<>(createdHhtNotification, HttpStatus.OK);
    }

    // Get Notification
    @ApiOperation(response = HhtNotification.class, value = "Get a HhtNotification") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getHhtNotification(@RequestParam String companyId, @RequestParam String languageId,
                                                @RequestParam String deviceId, @RequestParam String userId, @RequestParam String tokenId) {
        HhtNotification hhtNotification =
                hhtNotificationService.getHhtNotification(companyId, languageId, deviceId, userId, tokenId);
        log.info("HhtNotification : " + hhtNotification);
        return new ResponseEntity<>(hhtNotification, HttpStatus.OK);
    }

    // Update HhtNotification
    @ApiOperation(response = HhtNotification.class, value = "Update HhtNotification") // label for swagger
    @PatchMapping("/update")
    public ResponseEntity<?> patchHhtNotification(@Valid @RequestBody HhtNotification newHhtNotification,
                                                  @RequestParam String loginUserID) {
        HhtNotification updatedHhtNotification = hhtNotificationService.updateHhtNotification(newHhtNotification, loginUserID);
        return new ResponseEntity<>(updatedHhtNotification, HttpStatus.OK);
    }

    // Find HhtNotification
    @ApiOperation(response = HhtNotification.class, value = "Find HhtNotification") // label for swagger
    @PostMapping("/find")
    public List<HhtNotification> findHhtNotifications(@Valid @RequestBody FindHhtNotification findHhtNotification) throws Exception {
        return hhtNotificationService.findHhtNotifications(findHhtNotification);
    }

    // Get All HhtNotifications
    @ApiOperation(response = HhtNotification.class, value = "Get a HhtNotification") // label for swagger
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllHhtNotifications() {
        List<HhtNotification> notificationList = hhtNotificationService.getAllHhtNotifications();
        return new ResponseEntity<>(notificationList, HttpStatus.OK);
    }


}
