package com.mnrclara.api.management.controller;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.mnrclara.api.management.model.hhtnotification.Notification;
import com.mnrclara.api.management.service.PushNotificationService;
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
@Api(tags = {"PushNotification"}, value = "PushNotification Operations related to PushNotificationController")
@SwaggerDefinition(tags = {@Tag(name = "PushNotification", description = "Operations related to PushNotification")})
@RequestMapping("/pushNotification")
@RestController
public class PushNotificationController {

    @Autowired
    private PushNotificationService pushNotificationService;

    @ApiOperation(response = String.class, value = "PushNotification") // label for swagger
    @PostMapping("/send")
    public ResponseEntity<?> sendNotificationForTimeTicket(@Valid @RequestBody Notification notification) throws FirebaseMessagingException {

        String response = pushNotificationService.sendPushNotification(notification.getClassId(),
                notification.getToken(), notification.getTitle(), notification.getMessage(), notification.getClientUserId());
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }

    // For TimeTicket
    @ApiOperation(response = String.class, value = "PushNotification") // label for swagger
    @PostMapping("/sendQuotationHeaderNo")
    public ResponseEntity<?> sendQuotationHeaderNo(@Valid @RequestBody Notification notification) throws FirebaseMessagingException {

        String response = pushNotificationService.sendQutationHeader(notification);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(response = String.class, value = "PushNotification") // label for swagger
    @PostMapping("")
    public ResponseEntity<?> sendNotification(@Valid @RequestBody Notification notification)
            throws FirebaseMessagingException {
        String response = pushNotificationService.sendPushNotification(
                notification.getClassId(), notification.getClientId(), notification.getToken(),
                notification.getTitle(), notification.getMessage(), notification.getOrderType());
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }

}
