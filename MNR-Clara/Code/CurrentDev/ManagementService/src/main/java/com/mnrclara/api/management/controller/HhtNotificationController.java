package com.mnrclara.api.management.controller;

import com.mnrclara.api.management.model.hhtnotification.HhtNotification;
import com.mnrclara.api.management.service.HhtNotificationService;
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
@SwaggerDefinition(tags = {@Tag(name = "HhtNotification ", description = "Operations related to HhtNotification ")})
@RequestMapping("/hhtnotification")
@RestController
public class HhtNotificationController {

    @Autowired
    HhtNotificationService hhtNotificationService;

    // Create HHTNotification
    @ApiOperation(response = HhtNotification.class, value = "Create HhtNotification") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> createHhtNotification(@Valid @RequestBody HhtNotification newHhtNotification,
                                                   @RequestParam String loginUserID) {
        HhtNotification createdHhtNotification = hhtNotificationService.createHhtNotification(newHhtNotification, loginUserID);
        return new ResponseEntity<>(createdHhtNotification, HttpStatus.OK);
    }

    //Get Notification
    @ApiOperation(response = HhtNotification.class, value = "Get a HhtNotification") // label for swagger
    @GetMapping("")
    public ResponseEntity<?> getHhtNotification(@RequestParam String classId, @RequestParam String clientId,
                                                @RequestParam String deviceId, @RequestParam String tokenId) {
        HhtNotification hhtNotification =
                hhtNotificationService.getHhtNotification(classId, clientId, deviceId, tokenId);
        log.info("HhtNotification : " + hhtNotification);
        return new ResponseEntity<>(hhtNotification, HttpStatus.OK);
    }

    // Create Notification
    @ApiOperation(response = HhtNotification.class, value = "Create Notification for login")
    @PostMapping("/createhht")
    public ResponseEntity<?> createHhtForLogin(@Valid @RequestBody HhtNotification hhtNotification,
                                               @RequestParam String loginUserID) {
        HhtNotification dbHht = hhtNotificationService.createNotificationForLogin(hhtNotification, loginUserID);
        log.info("create hht for login" + dbHht);
        return new ResponseEntity<>(dbHht, HttpStatus.OK);
    }

    // Get Token
    @ApiOperation(response = HhtNotification.class, value = "Get Token")
    @GetMapping("/token")
    public ResponseEntity<?> getToken(@RequestParam String classId, @RequestParam String userId){
        List<HhtNotification> dbHht = hhtNotificationService.getToken(classId, userId);
        return new ResponseEntity<>(dbHht, HttpStatus.OK);
    }

}